package logic;


/**
 * Subclass of Maze that uses the demo labyrinth.
 */
public class StaticMaze extends Maze {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 2251878716836205717L;

	/**
	 * Generates the static maze.
	 */
	public void generate() {
		
		char[][] staticMaze = {
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				maze.add(staticMaze[i][j]);
			}
		}

	}
}
