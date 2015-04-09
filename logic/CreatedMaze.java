package logic;

public class CreatedMaze extends Maze {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3721607151246690133L;

	public CreatedMaze(int size) {
		this.dimension = size;
	}
	
	@Override
	public void gera() {
		for (int i = 0; i < dimension * dimension; i++){
			maze.add('X');
		}
	}

}
