package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cli.Cli;
import test.MyTest;

public class GameEngine {

	public List<Dragon> dragons = new ArrayList<Dragon>();
	public List<Dart> darts = new ArrayList<Dart>();
	public Hero h1 = new Hero();
	public Maze board;
	public int posEspada = 81;
	public int posEscudo = -1;
	public Cli cli = new Cli();
	public MyTest test = new MyTest();
	public Dragon.Mode dragonMode;
	public int ambiente; // 0=cli 1=test
	public int[] posicoes = { 0, 0, 0, 0, 0 };

	public GameEngine(int ambiente) {
		this.ambiente = ambiente;
	}

	public GameEngine() {
		ambiente = 0;
	}
 
	/*
	 * @brief Ciclo principal do jogo.
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
				cli.printMaze(board.getDados());
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
			// System.out.println(h1.posicao + " " + board.saida);
			if (ambiente == 0)
				moveHeroi(cli.askForDirection());
			else if(ambiente == 2){
				
			}
			
			moveDragoes();
			if (ambiente == 0)
				Cli.clearConsole();
			deleteEntities();
		}
	}

	/* 
	 * @brief Inicializa as variaveis necessarias ao bom funcionamento do jogo.
	 */
	public void initializeGame(MazeBuilder mb) {
		if (ambiente == 0) {
			mb.setMazeType(cli.askForType());

			if (mb.opcao == 1) {
				mb.setMazeDim(cli.askForSize());
				board = mb.getMaze();
				board.gera();
				dragonMode = cli.askForMode();
				generateDragons(cli.askForDragons());
			} else {
				board = mb.getMaze();
				board.gera();
				dragons.add(new Dragon());
			}

		} else if (ambiente == 1) {
			MazeBuilder mbt = new MazeBuilder();
			mbt.setMazeType(0);
			board = mb.getMaze();
			board.gera();
			if (mbt.opcao == 0) {
				dragonMode = Dragon.Mode.STATIC;
				dragons.add(new Dragon());
			}
		}
		
		if (mb.opcao == 1) {
			Random r = new Random();
			Random r2 = new Random();
			int n;

			generateDarts();

			do {
				n = r.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) != ' ');

			posEspada = n;

			do {
				n = r2.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) != ' ');

			posEscudo = n;

			/*
			 * O heroi nao pode comecar numa posicao adjacente a dragoes, senao
			 * o jogo e perdido automaticamente. Isto nao deve causar erros de
			 * acesso ilegal a memoria, visto que as bordas nunca tem um ' '
			 */
			do {
				do {
					n = r.nextInt(board.dimension * board.dimension);
				} while (board.checkTile(n) != ' ');
			} while (board.checkTile(n + 1) == 'D'
					|| board.checkTile(n - 1) == 'D'
					|| board.checkTile(n + board.dimension) == 'D'
					|| board.checkTile(n - board.dimension) == 'D');
			h1.posicao = n;
			board.maze.set(n, 'H');
		}

	}

	/*
	 * @brief Coloca as entidades estaticas do jogo no mapa.
	 */
	public void placeEntities() {
		/*
		 * Check necessario para quando o heroi vai ate a saida sem ter ganho o
		 * jogo ou um dragao passa por cima
		 */
		if (board.checkTile(board.getExit()) == ' ')
			board.changeBoard(board.getExit(), 'S');

		if (h1.posicao == posEspada) {
			h1.armado = true;
			posEspada = -1;
		} else if (posEspada != -1) {
			board.changeBoard(posEspada, 'E');
		}

		if (h1.posicao == posEscudo) {
			h1.escudo = true;
			posEscudo = -1;
		} else if (posEscudo != -1) {
			board.changeBoard(posEscudo, 'P');
		}

		for (int dardo = 0; dardo < darts.size(); dardo++) {
			if (h1.posicao == darts.get(dardo).posicao) {
				h1.dardo = true;
				darts.get(dardo).posicao = -1;
			} else if (darts.get(dardo).posicao != -1)
				board.changeBoard(darts.get(dardo).posicao, 'T');
		}

		if (h1.armado)
			board.changeBoard(h1.posicao, 'A');
		else
			board.changeBoard(h1.posicao, 'H');

		for (int dragon = 0; dragon < dragons.size(); dragon++) {
			if (dragons.get(dragon).posicao == posEscudo && posEscudo != -1)
				board.changeBoard(posEscudo, 'F');
			else if (dragons.get(dragon).posicao == posEspada
					&& posEspada != -1)
				board.changeBoard(posEspada, 'F');
			else
				for (int dart = 0; dart < darts.size(); dart++) {
					if ((dragons.get(dragon).posicao == darts.get(dart).posicao)
							&& (darts.get(dart).posicao != -1))
						board.changeBoard(darts.get(dart).posicao, 'F');
				}
		}
	}

	/*
	 * @brief Remove as entidades estaticas do jogo do mapa, para serem repostas
	 * depois nas novas posicoes.
	 */

	public void deleteEntities() {
		if (posEspada != -1)
			board.changeBoard(posEspada, ' ');
		if (posEscudo != -1)
			board.changeBoard(posEscudo, ' ');
		for (int i = 0; i < darts.size(); i++)
			if (darts.get(i).posicao != -1)
				board.changeBoard(darts.get(i).posicao, ' ');
	}

	/*
	 * @brief Testa se a posicao dada e transponivel ou nao.
	 * 
	 * @param pos posicao a testar.
	 */
	public boolean canMove(int pos) {
		/*
		 * for (int i = 0; i < dragons.size(); i++) { if
		 * (!dragons.get(i).acordado) { if (dragons.get(i).posicao == pos &&
		 * !h1.armado) return false; } }
		 */
		return !(board.checkTile(pos) == 'X' || board.checkTile(pos) == 'D');
	}

	/*
	 * @brief Move o heroi de posicao.
	 * 
	 * @param direcao Direcao para onde ele se vai mover. (W/A/S/D)
	 */
	public int moveHeroi(char direcao) {
		/*
		 * W -> -10 posicoes no array S -> +10 posicoes no array A -> -1 posicao
		 * no array D -> +1 posicao no array
		 */
		switch (direcao) {
		case 'w':
			if (canMove(h1.posicao - board.dimension)) {
				board.maze.set(h1.posicao, ' ');
				h1.posicao -= board.dimension;
				/*if (h1.armado)
					board.changeBoard(h1.posicao, 'A');
				else
					board.changeBoard(h1.posicao, 'H');*/
			}
			break;
		case 's':
			if (canMove(h1.posicao + board.dimension)) {
				board.maze.set(h1.posicao, ' ');
				h1.posicao += board.dimension;
				/*if (h1.armado)
					board.changeBoard(h1.posicao, 'A');
				else
					board.changeBoard(h1.posicao, 'H');*/
			}
			break;
		case 'a':
			if (canMove(h1.posicao - 1)) {
				board.maze.set(h1.posicao, ' ');
				h1.posicao -= 1;
				/*if (h1.armado)
					board.changeBoard(h1.posicao, 'A');
				else
					board.changeBoard(h1.posicao, 'H');*/
			}
			break;
		case 'd':
			if (canMove(h1.posicao + 1)) {
				board.maze.set(h1.posicao, ' ');
				h1.posicao += 1;
				/*if (h1.armado)
					board.changeBoard(h1.posicao, 'A');
				else
					board.changeBoard(h1.posicao, 'H');*/
			}
			break;
		default:
			break;
		}
		throwDarts(direcao);
		return 0;
	}

	/*
	 * @brief Sorteia se o dragao fica acordado ou adormecido.
	 */

	public void isAwake() {
		Random r = new Random();
		int n;
		for (int i = 0; i < dragons.size(); i++) {
			n = r.nextInt(2) + 1;
			if (n == 1)
				dragons.get(i).acordado = true;
			else
				dragons.get(i).acordado = false;
		}
	}

	/*
	 * @brief Move o dragao de posicao.
	 * 
	 * 
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
			if (!dragons.get(i).acordado) {
				board.maze.set(dragons.get(i).posicao, 'd');
				continue;
			}

			board.maze.set(dragons.get(i).posicao, ' ');

			do {
				n = r.nextInt(5) + 1;
				if (n == 5) {
					board.maze.set(dragons.get(i).posicao, 'D');
					continue Dragon;
				} else if (n <= 2) {
					t = (board.dimension * (n * 2 - 3));// Calculo feito para
					// minimizar o
					// numero de ifs feitos pela
					// funcao
					if (canMove(dragons.get(i).posicao + t)
							&& board.maze.get(dragons.get(i).posicao + t) != 'S') {
						dragons.get(i).posicao += t;
						ret = 0;
					}
				} else {
					t = (n * 2 - 7); // f(3) = -1,f(4) = 1
					if (canMove(dragons.get(i).posicao + t)
							&& board.maze.get(dragons.get(i).posicao + t) != 'S') {
						dragons.get(i).posicao += t;
						ret = 0;
					}
				}
			} while (ret != 0);

			board.maze.set(dragons.get(i).posicao, 'D');
		}
	}

	/*
	 * @brief Testa se houve combate entre o h1.posicao e o
	 * dragons.get(i).posicao, e determina o vencedor
	 */

	public boolean combate() {
		for (int i = 0; i < dragons.size(); i++) {
			if (h1.posicao == dragons.get(i).posicao + 1
					|| h1.posicao == dragons.get(i).posicao - 1
					|| h1.posicao == dragons.get(i).posicao + board.dimension
					|| h1.posicao == dragons.get(i).posicao - board.dimension
					|| h1.posicao == dragons.get(i).posicao) {
				if (!h1.armado && dragons.get(i).acordado) {
					// placeEntities();

					if (ambiente == 0) {
						cli.printMaze(board.getDados());
						cli.estadoFinal(1);
					}
					if (ambiente == 1) {
						test.printMaze(board.getDados());
						test.estadoFinal(1);
					}
					return true;
				} else {
					if (!h1.armado && !dragons.get(i).acordado)
						return false;
					else {
						board.changeBoard(dragons.get(i).posicao, ' ');
						dragons.get(i).posicao = -1;
						return false;
					}
				}

			}

		}
		if(fireballKill())
			return true;

		return false;
	}

	public boolean fireballKill(){

		for(int i=0;i<dragons.size();i++){
			int pos = dragons.get(i).posicao;
			int ydragao = pos%10;
			int xdragao = pos/10;
			int xheroi = h1.getPosicao()/10;
			int yheroi = h1.getPosicao()%10;
			int maxpos=0;

			if(randomFireBall(1) && dragons.get(i).isAcordado()){
				if(ydragao == yheroi){
					if(xdragao<xheroi)
						maxpos = pos+3;
					else 
						maxpos = pos-3;
					while(board.checkTile(pos)!='X' && pos<maxpos){
						if(h1.getPosicao()==pos){
							cli.printMaze(board.getDados());
							cli.estadoFinal(1);
							return true;
							}
						else if(h1.getPosicao() < pos)
							pos--;
						else
							pos++;
					}
				}
				if(xdragao==xheroi){
					if(ydragao<yheroi)
						maxpos = pos+30;
					else 
						maxpos = pos-30;
					while(board.checkTile(pos)!='X' && pos<maxpos){
						if(h1.getPosicao()==pos){
							cli.printMaze(board.getDados());
							cli.estadoFinal(1);
							return true;
							}
						else if(h1.getPosicao() < pos)
							pos-=10;
						else
							pos+=10;
					}
				}
			}

		}
		return false;

	}

	/*
	 * @brief Sorteia se as Fireballs estao ativas.
	 */
	public boolean randomFireBall(int test) {
		Random r = new Random();
		int n = r.nextInt(10) + 1;
		if (n == 2 || test == 1)
			return true;
		else
			return false;
	}

	/*
	 * @brief Cria o numero de dragoes pedidos pelo user e insere-os na lista de
	 * dragoes (dragons).
	 * 
	 * @param number Numero de dragoes pedido pelo user
	 */
	public int generateDragons(int number) {
		int n = 0;
		Random r = new Random();

		for (int i = 0; i < number; i++) {
			do {
				n = r.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) == 'X');
			Dragon d1 = new Dragon(n);
			dragons.add(d1);
			board.maze.set(n, 'D');
		}

		return dragons.size();
	}

	/*
	 * @brief Cria o numero de dardos entre 0 e 5 re-os na lista de dardos
	 * (darts).
	 */

	public void generateDarts() {
		int n = 0;
		Random r = new Random();
		int number = r.nextInt(5) + 1;

		for (int i = 0; i < number; i++) {
			do {
				n = r.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) != ' ');
			Dart d1 = new Dart(n);
			darts.add(d1);
		}
	}

	public void throwDarts(char direcao) {
		boolean usedDart = false;

		if (h1.dardo) {
			for (int i = 0; i < dragons.size(); i++) {
				int pos = h1.posicao;
				while (!(board.checkTile(pos) == 'X' || board.checkTile(pos) == 'S')) {
					if (usedDart)
						return;
					if (dragons.get(i).posicao == pos) {
						board.changeBoard(dragons.get(i).posicao, ' ');
						dragons.get(i).posicao = -1;
						h1.dardo = false;
						usedDart = true;
						break;
					}

					switch (direcao) {
					case 'w':
						pos -= board.dimension;
						break;
					case 's':
						pos += board.dimension;
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

	public boolean testWinCondition() {
		if ((h1.posicao == board.exit) && (h1.isArmado())) {
			for (int i = 0; i < dragons.size(); i++) {
				if (dragons.get(i).posicao != -1)
					return false;
			}
			return true;
		} else
			return false;
	}
}
