package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class containing the Maze's information.
 * <p>
 * All classes extending this one must implement the generate() method.
 */
public abstract class Maze implements Serializable {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 9170202473439340886L;

	/**
	 * The list containing the maze.
	 */
	public List<Character> maze = new ArrayList<Character>();

	/**
	 * The exit's location.
	 */
	protected int exit = 59;

	/**
	 * The maze's dimension
	 */
	protected int dimension = 10;

	/**
	 * Get @see exit
	 * 
	 * @return {@link exit}
	 */
	public int getExit() {
		return exit;
	}

	/**
	 * Set @see dardo
	 * 
	 * @param exit
	 *            {@link exit}
	 */
	public void setExit(int exit) {
		this.exit = exit;
	}

	/**
	 * Get @see dimension
	 * 
	 * @return {@link dimension}
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Set @see dimension
	 * 
	 * @param dim {@link dimension}
	 */
	public void setDimension(int dim) {
		this.dimension = dim;
	}

	/**
	 * x Generates the labyrinth.
	 */
	public abstract void generate();

	/**
	 * Get @see maze
	 * 
	 * @return {@link maze}
	 */
	public List<Character> getMaze() {
		return maze;
	}

	/**
	 * Changes the tile specified to the entity given.
	 * 
	 * @param tile
	 *            Tile to change.
	 * @param entity
	 *            Entity to put in the tile.
	 */
	public void changeBoard(int tile, char entity) {
		maze.set(tile, entity);
	}

	/**
	 * Gets the tile in the specified position
	 * 
	 * @param tile
	 *            tile to test.
	 * @return the entity in the tile.
	 */
	public char checkTile(int tile) {
		return maze.get(tile);
	}

}
