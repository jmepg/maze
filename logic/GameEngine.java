package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cli.Cli;
import test.MyTest;

/**
 * The class that holds all the information "behind the scenes" in the maze.
 */
public class GameEngine implements Serializable {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 6468700820813573469L;
	
	/**
	 * List of the dragons in the maze
	 */
	private List<Dragon> dragons = new ArrayList<Dragon>();
	
	public List <Dragon> getDragons(){
		return dragons;
	}
	
	public void setDragons(List <Dragon> dragons){
		this.dragons=dragons;
	}
	
	/**
	 * List of the darts in the maze
	 */
	private List<Dart> darts = new ArrayList<Dart>();
	
	public List <Dart> getDarts(){
		return darts;
	}
	
	public void setDarts(List <Dart> darts){
		this.darts=darts;
	}
	/**
	 * The hero.
	 */
	private Hero h1 = new Hero();
	
	public Hero getHero(){
		return h1;
	}
	
	public void setHero(Hero hero){
		this.h1=hero;
	}
	/**
	 * The board layout.
	 */
	private Maze board;
	
	public Maze getBoard(){
		return board;
	}
	
	public void setBoard(Maze b1){
		this.board=b1;
	}
	
	/**
	 * The sword's position.
	 */
	private int posEspada = 81;
	
	public int getPosEspada(){
		return posEspada;
	}
	public void setPosEspada(int sword){
		this.posEspada=sword;
	}
	/**
	 * The shield's position.
	 */
	private int posEscudo = -1;
	
	public int getPosEscudo(){
		return posEscudo;
	}
	public void setPosEscudo(int shield){
		this.posEscudo=shield;
	}
	
	/* Nao vou documentar estes dois membros porque vao muito provavelmente ser apagados */
	public transient Cli cli = new Cli();
	public transient MyTest test = new MyTest();
	
	/** The dragons' behaviour
	 * 
	 */
	public Dragon.Mode dragonMode;
	
	/** The interface used to play */
	private int ambiente; // 0=cli 1=test
	
	public int getAmbiente(){
		return ambiente;
	}
	public void setAmbiente(int amb){
		this.ambiente=amb;
	}

	/** 
	 * The class constructor.
	 * @param ambiente the interface used to play.
	 */
	public GameEngine(int ambiente) {
		this.ambiente = ambiente;
	}

	/**
	 * The games' main loop.
	 */
	public void jogar() {
		/*
		 * Esta funcao vai ter de ser alterada de forma a ir para o cli, vai ser
		 * preciso fazer um jogar() novo para test e gui. Os ciclos dos dragoes
		 * vao ter de passar para outra funcao na game engine, e no main vamos
		 * ter de ter um if que decida qual dos jogar() chamar consoante
		 * estarmos num ambiente de texto, grafico ou de teste.
		 */

		while (true) {
			placeEntities();

			if (ambiente == 0) {
				cli.printMaze(board.getMaze());
			}

			if (combate())
				return;

			if (testWinCondition()) {
				if (ambiente == 0)
					cli.estadoFinal(0);
				if (ambiente == 1)
					test.estadoFinal(0);
				return;
			}
			if (ambiente == 0)
				moveHeroi(cli.askForDirection());
			else if (ambiente == 2) {

			}

			moveDragoes();
			deleteEntities();
		}
	}

	/**
	 * Initializes the variables needed for the good functioning of the game.
	 * @param mb {@link logic.MazeBuilder}
	 */
	public void initializeGame(MazeBuilder mb) {
	
		if (ambiente == 0) {
			if(!cli.mainMenu())
				System.exit(0);
			mb.setOpcao(cli.askForType());

			if (mb.getOpcao() == 1) {
				mb.setMazeDim(cli.askForSize());
				board = mb.getMaze();
				board.generate();
				dragonMode = cli.askForMode();
				generateDragons(cli.askForDragons());
			} else {
				board = mb.getMaze();
				board.generate();
				dragons.add(new Dragon());
			}
		} else if (ambiente == 1) {
			MazeBuilder mbt = new MazeBuilder();
			mbt.setOpcao(0);
			board = mbt.getMaze();
			board.generate();
			if (mbt.getOpcao() == 0) {
				dragonMode = Dragon.Mode.STATIC;
				dragons.add(new Dragon());
			}
		}
		
		Random r = new Random();
		Random r2 = new Random();
		int n;

		generateDarts();

		do {
			n = r.nextInt((board.getDimension() * board.getDimension()) - 1);
		} while (board.checkTile(n) != ' ');

		posEspada = n;

		do {
			n = r2.nextInt((board.getDimension() * board.getDimension()) - 1);
		} while (board.checkTile(n) != ' ');

		posEscudo = n;

		/*
		 * O heroi nao pode comecar numa posicao adjacente a dragoes, senao o
		 * jogo e perdido automaticamente. Isto nao deve causar erros de acesso
		 * ilegal a memoria, visto que as bordas nunca tem um ' '
		 */
		do {
			do {
				n = r.nextInt(board.getDimension() * board.getDimension());
			} while (board.checkTile(n) != ' ');
		} while (board.checkTile(n + 1) == 'D' || board.checkTile(n - 1) == 'D'
				|| board.checkTile(n + board.getDimension()) == 'D'
				|| board.checkTile(n - board.getDimension()) == 'D');
		h1.setPosicao(n);
		board.maze.set(n, 'H');

	}

	/**
	 * Places the static entities on the map.
	 */
	public void placeEntities() {
		/*
		 * Check necessario para quando o heroi vai ate a saida sem ter ganho o
		 * jogo ou um dragao passa por cima
		 */
		if (board.checkTile(board.getExit()) == ' ')
			board.changeBoard(board.getExit(), 'S');

		if (h1.getPosicao() == posEspada) {
			h1.setArmado(true);
			posEspada = -1;
		} else if (posEspada != -1) {
			board.changeBoard(posEspada, 'E');
		}

		if (h1.getPosicao() == posEscudo) {
			h1.setEscudo(true);
			posEscudo = -1;
		} else if (posEscudo != -1) {
			board.changeBoard(posEscudo, 'P');
		}

		for (int dardo = 0; dardo < darts.size(); dardo++) {
			if (h1.getPosicao() == darts.get(dardo).getPosicao()) {
				h1.setDardo(true);
				darts.get(dardo).setPosicao(-1);
			} else if (darts.get(dardo).getPosicao() != -1)
				board.changeBoard(darts.get(dardo).getPosicao(), 'T');
		}

		if (h1.isArmado())
			board.changeBoard(h1.getPosicao(), 'A');
		else
			board.changeBoard(h1.getPosicao(), 'H');

		for (int dragon = 0; dragon < dragons.size(); dragon++) {
			if (dragons.get(dragon).getPosicao() == posEscudo
					&& posEscudo != -1)
				board.changeBoard(posEscudo, 'F');
			else if (dragons.get(dragon).getPosicao() == posEspada
					&& posEspada != -1)
				board.changeBoard(posEspada, 'F');
			else
				for (int dart = 0; dart < darts.size(); dart++) {
					if ((dragons.get(dragon).getPosicao() == darts.get(dart)
							.getPosicao())
							&& (darts.get(dart).getPosicao() != -1))
						board.changeBoard(darts.get(dart).getPosicao(), 'F');
				}
		}
	}

	/**
	 * Deletes the static entities from the map.
	 */

	public void deleteEntities() {
		if (posEspada != -1)
			board.changeBoard(posEspada, ' ');
		if (posEscudo != -1)
			board.changeBoard(posEscudo, ' ');
		for (int i = 0; i < darts.size(); i++)
			if (darts.get(i).getPosicao() != -1)
				board.changeBoard(darts.get(i).getPosicao(), ' ');
	}

	/**
	 * Tests if the given tile can be crossed.
	 * @param tile Tile to test.
	 * @return True if the tile can be crossed, false otherwise.
	 */
	public boolean canMove(int tile) {

		return !(board.checkTile(tile) == 'X' || board.checkTile(tile) == 'D');
	}

	/**
	 * Moves the hero.
	 * 
	 * @param direcao Direction to move him to
	 */
	public void moveHeroi(char direcao) {
		/*
		 * W -> -10 posicoes no array S -> +10 posicoes no array A -> -1 posicao
		 * no array D -> +1 posicao no array
		 */
		boolean dragonsKilled = true; // Esta vari�vel indica se todos os
										// dragoes estao mortos e por isso �
										// poss�vel sair do labirinto
		for (int i = 0; i < dragons.size(); i++) {
			if (dragons.get(i).getPosicao() != -1)
				dragonsKilled = false;
		}

		switch (direcao) {
		case 'w':
			if (h1.getPosicao() - board.getDimension() == board.getExit()
					&& !dragonsKilled)
				return;
			if (canMove(h1.getPosicao() - board.getDimension())) {
				board.maze.set(h1.getPosicao(), ' ');
				h1.setPosicao(h1.getPosicao() - board.getDimension());
			}
			break;
		case 's':
			if (h1.getPosicao() + board.getDimension() == board.getExit()
					&& !dragonsKilled)
				return;
			if (canMove(h1.getPosicao() + board.getDimension())) {
				board.maze.set(h1.getPosicao(), ' ');
				h1.setPosicao(h1.getPosicao() + board.getDimension());
			}
			break;
		case 'a':
			if (h1.getPosicao() - 1 == board.getExit() && !dragonsKilled)
				return;
			if (canMove(h1.getPosicao() - 1)) {
				board.maze.set(h1.getPosicao(), ' ');
				h1.setPosicao(h1.getPosicao() - 1);
			}
			break;
		case 'd':
			if (h1.getPosicao() + 1 == board.getExit() && !dragonsKilled)
				return;
			if (canMove(h1.getPosicao() + 1)) {
				board.maze.set(h1.getPosicao(), ' ');
				h1.setPosicao(h1.getPosicao() + 1);
			}
			break;
		default:
			break;
		}
		throwDarts(direcao);
		return;
	}

	/**
	 * Randomly sets a dragon as awake or not.
	 */

	public void isAwake() {
		Random r = new Random();
		int n;
		for (int i = 0; i < dragons.size(); i++) {
			n = r.nextInt(2) + 1;
			if (n == 1)
				dragons.get(i).setAcordado(true);
			else
				dragons.get(i).setAcordado(false);
		}
	}

	/**
	 * Randomly moves the dragons.
	 */

	public void moveDragoes() {
		if (dragonMode == Dragon.Mode.STATIC)
			return;

		if (dragonMode == Dragon.Mode.SLEEPING)
			isAwake();

		Dragon: for (int i = 0; i < dragons.size(); i++) {
			Random r = new Random();

			/* 1-cima 2-baixo 3-esquerda 4- direita */
			int n, t;
			int ret = 1;

			if (dragons.get(i).getPosicao() == -1)
				continue;
			if (!dragons.get(i).isAcordado()) {
				board.maze.set(dragons.get(i).getPosicao(), 'd');
				continue;
			}

			board.maze.set(dragons.get(i).getPosicao(), ' ');

			do {
				n = r.nextInt(5) + 1;
				if (n == 5) {
					board.maze.set(dragons.get(i).getPosicao(), 'D');
					continue Dragon;
				} else if (n <= 2) {
					t = (board.getDimension() * (n * 2 - 3));// Calculo feito
																// para
					// minimizar o
					// numero de ifs feitos pela
					// funcao
					if (canMove(dragons.get(i).getPosicao() + t)
							&& board.maze.get(dragons.get(i).getPosicao() + t) != 'S') {
						dragons.get(i).setPosicao(
								dragons.get(i).getPosicao() + t);
						ret = 0;
					}
				} else {
					t = (n * 2 - 7); // f(3) = -1,f(4) = 1
					if (canMove(dragons.get(i).getPosicao() + t)
							&& board.maze.get(dragons.get(i).getPosicao() + t) != 'S') {
						dragons.get(i).setPosicao(
								dragons.get(i).getPosicao() + t);
						ret = 0;
					}
				}
			} while (ret != 0);

			board.maze.set(dragons.get(i).getPosicao(), 'D');
		}
	}

	/**
	 * Tests if there was a fight between the hero and one of the dragons, and determines the winner.
	 * @return true if a dragon wins, false if the hero wins.
	 */

	public boolean combate() {
		for (int i = 0; i < dragons.size(); i++) {
			if (h1.getPosicao() == dragons.get(i).getPosicao() + 1
					|| h1.getPosicao() == dragons.get(i).getPosicao() - 1
					|| h1.getPosicao() == dragons.get(i).getPosicao()
							+ board.getDimension()
					|| h1.getPosicao() == dragons.get(i).getPosicao()
							- board.getDimension()
					|| h1.getPosicao() == dragons.get(i).getPosicao()) {
				if (!h1.isArmado() && dragons.get(i).isAcordado()) {

					if (ambiente == 0) {
						cli.printMaze(board.getMaze());
						cli.estadoFinal(1);
					}
					if (ambiente == 1) {
						test.printMaze(board.getMaze());
						test.estadoFinal(1);
					}
					return true;
				} else {
					if (!h1.isArmado() && !dragons.get(i).isAcordado())
						return false;
					else {
						board.changeBoard(dragons.get(i).getPosicao(), ' ');
						dragons.get(i).setPosicao(-1);
						return false;
					}
				}

			}

		}
		
		return false;
	}

	/**
	 * Tests if a fireball kills the hero.
	 * @return true if the hero is killed, false otherwise.
	 */
	public boolean fireballKill() {

		if (h1.isEscudo())
			return false;

		for (int i = 0; i < dragons.size(); i++) {

			int pos = dragons.get(i).getPosicao();
			int xdragao = pos % board.getDimension();
			int ydragao = pos / board.getDimension();
			int xheroi = h1.getPosicao() % board.getDimension();
			int yheroi = h1.getPosicao() / board.getDimension();
			int maxpos = 0;

			if (randomFireBall(1) && dragons.get(i).isAcordado()) {
				if (ydragao == yheroi) {
					if (xdragao < xheroi)
						maxpos = pos + 3;
					else
						maxpos = pos - 3;
					while (board.checkTile(pos) != 'X'
							&& Math.abs(maxpos - pos) > 0) {
						if (h1.getPosicao() == pos) {
							cli.printMaze(board.getMaze());
							cli.estadoFinal(1);
							return true;
						} else if (h1.getPosicao() < pos)
							pos--;
						else
							pos++;
					}
				}
				if (xdragao == xheroi) {
					if (ydragao < yheroi)
						maxpos = pos + 3 * board.getDimension();
					else
						maxpos = pos - 3 * board.getDimension();
					while (board.checkTile(pos) != 'X'
							&& Math.abs(maxpos - pos) > 0) {
						if (h1.getPosicao() == pos) {
							cli.printMaze(board.getMaze());
							cli.estadoFinal(1);
							return true;
						} else if (h1.getPosicao() < pos)
							pos -= board.getDimension();
						else
							pos += board.getDimension();
					}
				}
			}

		}
		return false;

	}

	/**
	 * Randomly decides whether the dragon spits a fireball or not.
	 * @param test ISTO E PARA SAIR DAQUI ESTRADA
	 * @return true If a fireball is generated, false otherwise.
	 */
	public boolean randomFireBall(int test) {
		Random r = new Random();
		int n = r.nextInt(10) + 1;
		if (n == 2 || test == 1)
			return true;
		else
			return false;
	}

	/**
	 * Creates the number of dragons specified by the user and places them
	 * in the list of dragons and in the board.
	 * 
	 * @param number Number of dragons to generate.
	 */
	public void generateDragons(int number) {
		int n = 0;
		Random r = new Random();
		
		dragons.clear();

		for (int i = 0; i < number; i++) {
			do {
				n = r.nextInt((board.getDimension() * board.getDimension()) - 1);
			} while (board.checkTile(n) == 'X');
			Dragon d1 = new Dragon(n);
			dragons.add(d1);
			board.maze.set(n, 'D');
		}

		return;
	}

	/**
	 * Generates a random number of darts between 0 and the number of dragons, and places them
	 * on the list of darts and in the board.
	 */

	public void generateDarts() {
		int n = 0;
		Random r = new Random();
		int number = r.nextInt(dragons.size()+1);
		
		darts.clear();

		for (int i = 0; i < number; i++) {
			do {
				n = r.nextInt((board.getDimension() * board.getDimension()) - 1);
			} while (board.checkTile(n) != ' ');
			Dart d1 = new Dart(n);
			darts.add(d1);
		}
	}

	/**
	 * Checks whether the user has a dart, and whether the hero is in the range of a dragon,
	 * and throws it if so
	 * 
	 * @param direcao The direction to look to.
	 */
	public void throwDarts(char direcao) {
		boolean usedDart = false;

		if (h1.isDardo()) {
			for (int i = 0; i < dragons.size(); i++) {
				int pos = h1.getPosicao();
				while (!(board.checkTile(pos) == 'X' || board.checkTile(pos) == 'S')) {
					if (usedDart)
						return;
					if (dragons.get(i).getPosicao() == pos) {
						board.changeBoard(dragons.get(i).getPosicao(), ' ');
						dragons.get(i).setPosicao(-1);
						h1.setDardo(false);
						usedDart = true;
						break;
					}

					switch (direcao) {
					case 'w':
						pos -= board.getDimension();
						break;
					case 's':
						pos += board.getDimension();
						break;
					case 'a':
						pos -= 1;
						break;
					case 'd':
						pos += 1;
					default:
						break;
					}
				}

			}
		}
	}

	/**
	 * Tests if the game's winning condition has been met.
	 * 
	 * @return true if it has been met, false otherwise.
	 */
	public boolean testWinCondition() {
		if ((h1.getPosicao() == board.getExit()) && (h1.isArmado())) {
			for (int i = 0; i < dragons.size(); i++) {
				if (dragons.get(i).getPosicao() != -1)
					return false;
			}
			return true;
		} else
			return false;
	}
}
