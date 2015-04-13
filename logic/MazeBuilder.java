package logic;

import symbolics.MazeBuild;
import logic.RandomMaze;
import logic.StaticMaze;
import logic.Maze;

/**
 * Class used to generate a maze according to the type of maze specified.
 */
public class MazeBuilder {
	
	/**
	 * Type of maze to generate.
	 */
	private int opcao = -1;
	
	/**
	 * Size of the maze.
	 */
	private int dimension = 10;

	/**
	 * Get @see dardo
	 * 
	 * @return opcao {@link opcao}
	 */
	public int  getOpcao(){
		return opcao;
	}
	
	/**
	 * Set @see opcao
	 * 
	 * @param op {@link opcao} (0 = static, 1 = random, 2= user-created)
	 */
	public void setOpcao(int op) {
		opcao = op;
	}

	/**
	 * Set @see dimension
	 * 
	 * @param dim {@link dimension}
	 */
	public void setMazeDim(int dim) {
		dimension = dim;
	}

	/**
	 * Creates the labyrinth.
	 * @return The labyrinth generated.
	 */
	public Maze getMaze() {
		if (opcao == MazeBuild.RANDOMMAZE)
			return new RandomMaze(dimension);
		else if(opcao == MazeBuild.STATICMAZE)
			return new StaticMaze();
		else
			return new CreatedMaze(dimension);

	}
}
