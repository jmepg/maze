package cli;

import java.util.Scanner;
import java.util.List;

import logic.Dragon;

/**
 * Manages the necessary information for playing with a command-line interface.
 */
public class Cli {

	/**
	 * The scanner used for accepting the player's inputs.
	 */
	Scanner keyboard = new Scanner(System.in);

	/**
	 *  Prints the maze in the command-line.
	 *  @param maze The list of characters containing the information regarding the maze.
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
	
	/**
	 * Asks in the command line the player's next move.
	 * @return The direction the user chose.
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

	/**
	 * Asks the user the desired labyrinth type (Static/Generated).
	 * @return The labyrinth type.
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
	
	/**
	 * In the case of a random labyrinth, asks the user for its size.
	 * @return The size.
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
	
	/**
	 * Asks the user for the dragon's behaviour (mode) (Static/Moving/Sleepy).
	 * @return The dragon mode.
	 */
	
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
	
	/**
	 * Asks the user about the number of dragons he wishes to have the game generate.
	 * @return The user's input.
	 */
	public int askForDragons(){
		int number;
		do{
			System.out.print("Numero de dragoes?");
		number = keyboard.nextInt();
		System.out.println();
		} while (number<0);
		
		return number;
	}

	/**
	 * Pseudo-clears the console.
	 */
	public final static void clearConsole() {
		for (int i = 0; i < 25; i++) {
			System.out.println("");
		}
	}

	/**
	 * When one of the game-finishing conditions has been met, outputs the game result.
	 * @param estado The game result.
	 */
	public void estadoFinal(int estado) {

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
