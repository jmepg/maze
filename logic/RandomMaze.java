package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RandomMaze extends Maze{

	/*
	 * @brief Construtor da classe.
	 */
	public RandomMaze(int size) {
		this.dimension = size;
		
	}

	/*
	 * @brief Muda uma entidade de posicao no labirinto.
	 */
	public void changeBoard(int tile, char entity) {
		maze.set(tile, entity);
	}

	/*
	 * @brief Retorna o item existente numa tile.
	 * 
	 * @param tile Tile a testar
	 */
	public char checkTile(int tile) {
		return maze.get(tile);
	}

	/*
	 * @brief Determina se todos os vizinhos do pivot atual ja foram visitados.
	 * 
	 * @param currentPivot Pivot a usar
	 * 
	 * @param visitedCells Array com a informacao referente aos pivots
	 */

	public boolean isStuck(int currentPivot, List<Integer> visitedCells) {
		for (int i = 0; i < 4; i++) {
			if (possiblePivot(i, currentPivot, visitedCells))
				return false;
		}
		return true;
	}

	/*
	 * @brief Determina se, a partir de um pivot, e depois de gerada uma
	 * direccao, e possivel seguir nessa direccao.
	 * 
	 * @param currentPivot Pivot a usar
	 * 
	 * @param visitedCells Array com a informacao referente aos pivots
	 * 
	 * @param direction Direccao a tomar
	 */
	public boolean possiblePivot(int direction, int currentPivot,
			List<Integer> visitedCells) {
		int size = (int) Math.sqrt(visitedCells.size());

		switch (direction) {
		case 0:
			if ((currentPivot < size)
					|| (visitedCells.get(currentPivot - size) == 0))
				return false;
			break;
		case 1:
			if ((currentPivot / size) >= (size - 1)
					|| (visitedCells.get(currentPivot + size) == 0))
				return false;
			break;
		case 2:
			if (currentPivot % size == 0
					|| (visitedCells.get(currentPivot - 1) == 0))
				return false;
			break;
		case 3:
			if ((currentPivot % size) == (size - 1)
					|| (visitedCells.get(currentPivot + 1) == 0))
				return false;
			break;
		}
		return true;
	}

	/*
	 * @brief Gera um maze aleatoriamente. Segue o algoritmo explicado nesta
	 * pagina: http://difusal.blogspot.pt/2014/02/maze-generation-algorithm.html
	 */

	public void gera() {
		/* Encher o maze com X */

		for (int i = 0; i < dimension * dimension; i++) {
			maze.add('X');
		}

		/*
		 * Lista com os pivots. Guarda a posicao respetiva no maze, para efeitos
		 * de eficiencia. Quando um pivot ja foi visitado, a informacao na
		 * posicao respetiva e colocada a 0.
		 */
		List<Integer> visitedCells = new ArrayList<Integer>();

		/*
		 * Apagar os tiles com ambas as coordenadas impares. Multiplicacao de 2
		 * numeros impares e a unica situacao em que pode dar resultado impar,
		 * dai usar multiplicacao para descobrir quais as coordenadas que se
		 * adequam. Este metodo pode possivelmente ser optimizado.
		 */

		for (long i = 0; i < dimension * dimension; i++) {
			if ((((i / dimension) * (i % dimension)) % 2) != 0) {
				maze.set((int) i, ' ');
				visitedCells.add((int) i);
			}
		}

		/*
		 * Stack com os pivots ja visitados, por ordem. Util para saber qual
		 * pivot usar quando se ficar preso.
		 */
		Stack<Integer> pathHistory = new Stack<Integer>();
		int vcSize = (dimension - 1) / 2; // Tamanho do array de pivots

		Random r = new Random();
		int currentPivot;

		/*
		 * Escolha do primeiro pivot. Este pivot tem de estar nas bordas do
		 * array, para a saida poder ser na borda do maze
		 */
		do {
			currentPivot = r.nextInt(vcSize * vcSize);
		} while (!((currentPivot / vcSize == 0)
				|| ((currentPivot / vcSize) >= (vcSize - 1))
				|| (currentPivot % vcSize == 0) || (currentPivot % vcSize == (vcSize - 1))));

		int nextDirection; // Determina a direccao a seguir
		int iterador = visitedCells.get(currentPivot);

		/*
		 * Colocacao da saida. Prioritiza saidas norte/sul a este/oeste, no caso
		 * de o pivot inicial estar num dos 4 cantos do array de pivots.
		 */
		if (currentPivot / vcSize == 0) {
			exit = iterador - dimension;
			maze.set(exit, 'S');
		} else if ((currentPivot / vcSize) >= (vcSize - 1)) {
			exit = iterador + dimension;
			maze.set(exit, 'S');
		} else if (currentPivot % vcSize == 0) {
			exit = iterador - 1;
			maze.set(exit, 'S');
		} else {
			exit = iterador + 1;
			maze.set(exit, 'S');
		}

		/* Escolha dos proximos pivots */

		visitedCells.set(currentPivot, 0);
		pathHistory.push(currentPivot);

		/*
		 * Este ciclo determina a proxima direccao a seguir e, se nao a houver,
		 * consulta a stack ate encontrar um pivot viavel, continuando a partir
		 * dai.
		 */
		while (true) {
			do {
				if (isStuck(currentPivot, visitedCells))
					while (true) {
						if (isStuck(pathHistory.peek(), visitedCells))
							pathHistory.pop();
						else {
							currentPivot = pathHistory.peek();
							iterador = (2 * (currentPivot / vcSize) + 1) * dimension
									+ (2 * (currentPivot % vcSize) + 1);
							break;
						}
						if (pathHistory.isEmpty())
							return;
					}

				nextDirection = r.nextInt(4);
			} while (!possiblePivot(nextDirection, currentPivot, visitedCells));

			pathHistory.push(currentPivot);

			/*
			 * Depois de determinado o novo pivot e a direccao, esta na hora de
			 * abrir o espaco no maze e escolher o pivot seguinte.
			 */
			switch (nextDirection) {
			case 0:
				maze.set(iterador - dimension, ' ');
				currentPivot -= vcSize;
				break;
			case 1:
				maze.set(iterador + dimension, ' ');
				currentPivot += vcSize;
				break;
			case 2:
				maze.set(iterador - 1, ' ');
				currentPivot -= 1;
				break;
			case 3:
				maze.set(iterador + 1, ' ');
				currentPivot += 1;
				break;
			}

			iterador = visitedCells.get(currentPivot);
			visitedCells.set(currentPivot, 0);
		}
	}
}
