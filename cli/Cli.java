package cli;

import java.util.Scanner;
import java.util.List;

import logic.Dragon;


public class Cli {

	Scanner keyboard = new Scanner(System.in);

	/*
	 * @brief Imprime no ecra o labirinto.
	 */
	public void printMaze(List<Character> maze) {

		int dimension = (int) Math.sqrt(maze.size());

		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.print(maze.get(dimension * i + j) + " ");
			}
			System.out.println();
		}
	}
	
	/*
	 * @brief Pergunta no ecra a direcao para a qual o heroi se vai mover na
	 * jogada seguinte.
	 */

	public char askForDirection() {
		char direcao;
		do {
			System.out.print("Direcao? ");
			direcao = keyboard.next().charAt(0);
			System.out.println(direcao);
			System.out.println();
		} while (!(direcao == 'w' || direcao == 's' || direcao == 'a' || direcao == 'd'));

		return direcao;
	}

	/*
	 * @brief Pergunta pelo tipo de labirinto.
	 */
	public int askForType() {
		int opcao;

		do {
			System.out
					.print("Labirinto estatico ou gerar labirinto aleatorio (0/1)?");
			opcao = keyboard.nextInt();
			System.out.println();
		} while (!(opcao == 0 || opcao == 1));

		return opcao;
	}
	
	/*
	 * @brief Se for escolhida a opcao de labirinto aleatorio, pergunta pelo
	 * tamanho do labirinto.
	 */

	public int askForSize() {
		int tamanho;

		do {
			System.out.print("Tamanho do labirinto? (>7, tem de ser impar)");
			tamanho = keyboard.nextInt();
			System.out.println();
		} while (tamanho < 7 || (tamanho % 2) == 0);

		return tamanho;
	}
	
	public Dragon.Mode askForMode(){
		int choice;
		Dragon.Mode mode = null;
		do{
			System.out.print("Modo de Jogo? 1-Dragao estatico 2-Dragao com movimento 3-Dragao adormecido");
		choice = keyboard.nextInt();
		System.out.println();
		} while (choice != 1 && choice != 2 && choice != 3);
		
		switch(choice){
		case 1: 
			mode = Dragon.Mode.STATIC;
			break;
		case 2: 
			mode = Dragon.Mode.MOVABLE;
			break;
		case 3:
			mode = Dragon.Mode.SLEEPING;
			break;
		default:
			break;
		}
		
		return mode;
	}
	
	public int askForDragons(){
		int number;
		do{
			System.out.print("Numero de dragoes?");
		number = keyboard.nextInt();
		System.out.println();
		} while (number<0);
		
		return number;
	}

	/*
	 * @brief Pseudo-limpa a consola.
	 */
	public final static void clearConsole() {
		for (int i = 0; i < 25; i++) {
			System.out.println("");
		}
	}

	/*
	 * @brief Funcao que gere a condicao de fim de jogo
	 * 
	 * @param estado caso de fim de jogo. 0=vitoria 1=derrota.
	 */
	public void estadoFinal(int estado) {

		//clearConsole();
		System.out.println();

		switch (estado) {
		case 0:
			System.out.println("GANHOU!!!");
			break;
		case 1:
			System.out.println("PERDEU, FOI MORTO PELO DRAGÃO!!!");
			break;
		default:
			System.out.println("ERRO!!!");
			break;
		}
		return;
	}
}
