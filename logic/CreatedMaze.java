package logic;

/**
 * Subclass of maze that creates an empty labyrinth, used in the create maze option in the gui.
 */
public class CreatedMaze extends Maze {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 3721607151246690133L;

	/**
	 * Classe constructor.
	 * @param size Size of the maze to create.
	 */
	public CreatedMaze(int size) {
		this.dimension = size;
	}

	@Override
	public void generate() {
		for (int i = 0; i < dimension * dimension; i++){
			maze.add('X');
		}
		setExit(-1);
	}

}
