package logic;

import logic.RandomMaze;
import logic.StaticMaze;
import logic.Maze;

public class MazeBuilder {
	int opcao = -1;
	int dimension = 10;

	/*
	 * @brief Define o tipo de labirinto (aleatorio ou o ja definido).
	 * @param op opcao a representar o tipo (0 = ja definido, 1 = aleatorio)
	 */
	public void setMazeType(int op) {
		opcao = op;
	}
	
	/*
	 * @brief Define a dimensao do labirinto.
	 */
	public void setMazeDim(int dim) {
		dimension = dim;
	}

	/*
	 * @brief Cria o labirinto.
	 */
	public Maze getMaze() {
		if (opcao == 1)
			return new RandomMaze(dimension);
		else
			return new StaticMaze();
	}
}
