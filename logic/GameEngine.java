package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cli.Cli;

public class GameEngine {

	List<Dragon> dragons = new ArrayList<Dragon>();
	Hero h1 = new Hero();
	Maze board;
	int posEspada = 81;
	Cli cli = new Cli();
	int dragonMode;

	public static void main(String[] args) {
		GameEngine g = new GameEngine();
		g.initializeGame();
		g.jogar();
	}

	/*
	 * @brief Ciclo principal do jogo.
	 */
	public void jogar() {

		while (true) {
			// System.out.println("h1.posicao: " + h1.posicao);
			for(int i=0; i<dragons.size(); i++){
				if (dragons.get(i).posicao != -1) {
					if (combate())
						return;
				}
			}
			placeEntities();
			cli.printMaze(board.getDados());
			deleteEntities();
			if (h1.posicao == board.exit) {
				cli.estadoFinal(0);
				return;
			}
			// System.out.println(h1.posicao + " " + board.saida);
			moveHeroi(cli.askForDirection());
			for(int i=0; i<dragons.size(); i++){
				if (dragons.get(i).posicao != -1)
					moveDragao();
				Cli.clearConsole();
			}
		}
	}

	/*
	 * @brief Inicializa as variaveis necessarias ao bom funcionamento do jogo.
	 */
	public void initializeGame() {

		MazeBuilder mb = new MazeBuilder();
		mb.setMazeType(cli.askForType());
		if (mb.opcao == 1)
			mb.setMazeDim(cli.askForSize());
		
		board = mb.getMaze();
		board.gera();
		dragonMode = cli.askForMode();
		generateDragons(cli.askForDragons());
		
		
		

		Random r = new Random();
		int n;

		if (mb.opcao == 1) {
			do {
				n = r.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) == 'X');

			Dragon d1 = new Dragon(n);
			dragons.add(d1);

			do {
				n = r.nextInt(board.dimension * board.dimension);
			} while ((n == d1.posicao) || board.checkTile(n) == 'X');
			posEspada = n;

			do {
				n = r.nextInt(board.dimension * board.dimension);
			} while ((n == d1.posicao) || board.checkTile(n) == 'X'
					|| n == posEspada);
			h1.posicao = n;
		} else {
			Dragon d1 = new Dragon();
			dragons.add(d1);
		}
	}

	/*
	 * @brief Coloca as entidades do jogo no mapa.
	 */
	public void placeEntities() {
		if (h1.posicao == posEspada) {
			h1.armado = true;
			posEspada = -1;
		}

		if (h1.armado)
			board.changeBoard(h1.posicao, 'A');
		else
			board.changeBoard(h1.posicao, 'H');

		/* Este conjunto de condicoes pode ser optimizado */
		for(int i=0;i<dragons.size();i++){
			
			if (posEspada != -1)
				if (dragons.get(i).posicao == posEspada)
					board.changeBoard(posEspada, 'F');
				else
					board.changeBoard(posEspada, 'E');
			if (dragons.get(i).posicao != -1 && dragons.get(i).posicao != posEspada && dragons.get(0).acordado)
				board.changeBoard(dragons.get(i).posicao, 'D');
			if (dragons.get(i).posicao != -1 && dragons.get(i).posicao != posEspada && !dragons.get(0).acordado)
				board.changeBoard(dragons.get(i).posicao, 'd');
		}
	}

	/*
	 * @brief Remove as entidades do jogo do mapa, para serem repostas depois
	 * nas novas posicoes. NOTA: E possivel fazer isto de forma mais eficaz.
	 */

	public void deleteEntities() {
		board.changeBoard(h1.posicao, ' ');
		for(int i=0;i<dragons.size();i++)
		if (dragons.get(i).posicao != -1)
			board.changeBoard(dragons.get(i).posicao, ' ');
		if (posEspada != -1)
			board.changeBoard(posEspada, ' ');
	}

	/*
	 * @brief Testa se a posicao dada e transponivel ou nao.
	 * 
	 * @param pos posicao a testar.
	 */
	public boolean canMove(int pos) {
		for(int i=0; i<dragons.size(); i++){
			if(!dragons.get(i).acordado){
				if(dragons.get(i).posicao==pos && !h1.armado)
					return false;
			}
		}
		return (board.checkTile(pos) != 'X');
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
			if (canMove(h1.posicao - board.dimension))
				h1.posicao -= board.dimension;
			break;
		case 's':
			if (canMove(h1.posicao + board.dimension))
				h1.posicao += board.dimension;
			break;
		case 'a':
			if (canMove(h1.posicao - 1))
				h1.posicao -= 1;
			break;
		case 'd':
			if (canMove(h1.posicao + 1))
				h1.posicao += 1;
			break;
		default:
			break;
		}
		return 0;
	}
	
	public void isAwake(){
		Random r = new Random();
		
		for(int i=0; i<dragons.size(); i++){
			int n  = r.nextInt(2)+1;
			if(n==1)
				dragons.get(i).acordado=true;
			if(n==2) 
				dragons.get(i).acordado=false;
		}
	}

	/*
	 * @brief Move o dragao de posicao.
	 * 
	 * @param direcao Direcao para onde ele se vai mover. (W/A/S/D)
	 */
	public void moveDragao() {
		if(dragonMode == 1)
			return;

		if(dragonMode == 3)
			isAwake();

		for(int i=0; i<dragons.size();i++){
			Random r = new Random();

			/* 1 - cima 2 - baixo 3-esquerda 4- direita */
			int n, t;
			int ret = 1;

			if(!dragons.get(i).acordado) return;
			do {
				n = r.nextInt(5) + 1;
				if (n == 5)
					return;
				if (n <= 2) {
					t = (board.dimension * (n * 2 - 3));// Calculo feito para
					// minimizar o
					// numero de ifs feitos pela
					// funcao
					if (canMove(dragons.get(i).posicao + t)) {
						dragons.get(i).posicao += t;
						ret = 0;
					}
				} else {

					t = (n * 2 - 7); // f(3) = -1,f(4) = 1
					if (canMove(dragons.get(i).posicao + t)) {
						dragons.get(i).posicao += t;
						ret = 0;
					}
				}
			} while (ret != 0);
		}
		//System.out.println("Direccao: " + n);
	}

	/*
	 * @brief Testa se houve combate entre o h1.posicao e o
	 * dragons.get(0).posicao, e determina o vencedor
	 */
	
	

	public boolean combate() {
		for(int i=0; i<dragons.size(); i++){
			if (h1.posicao == dragons.get(i).posicao + 1
					|| h1.posicao == dragons.get(i).posicao - 1
					|| h1.posicao == dragons.get(i).posicao + 10
					|| h1.posicao == dragons.get(i).posicao - 10
					|| h1.posicao == dragons.get(i).posicao) {
				if (!h1.armado && dragons.get(i).acordado) {
					placeEntities();
					cli.printMaze(board.getDados());
					cli.estadoFinal(1);
					return true;
				} else {
					if(!h1.armado && !dragons.get(i).acordado)
						return false;
					else
						dragons.get(i).posicao = -1;
				}

			}/*
			if (h1.posicao == dragons.get(i).posicao + 3
					|| h1.posicao == dragons.get(i).posicao - 3
					|| h1.posicao == dragons.get(i).posicao + 30
					|| h1.posicao == dragons.get(i).posicao - 30
					|| h1.posicao == dragons.get(i).posicao) {

				if(!h1.escudo && dragons.get(i).acordado && randomFireBall()){
					placeEntities();
					cli.printMaze(board.getDados());
					cli.estadoFinal(1);
					return true;
				} 
			}*/
			
			
		}
		
		 

		return false;
	}
	
	public boolean randomFireBall(){
		Random r = new Random();
		int n  = r.nextInt(10)+1;
		if(n==1)
			return true;
		else 
			return false;
	}
	
	public void generateDragons(int number){
		int n=0;
		Random r = new Random();

		for(int i=0;i<number;i++){
			do {
				n = r.nextInt((board.dimension * board.dimension) - 1);
			} while (board.checkTile(n) == 'X');
			Dragon d1= new Dragon(n);
			dragons.add(d1);
		}
	}

}
