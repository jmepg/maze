package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Maze implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9170202473439340886L;
	public List<Character> maze = new ArrayList<Character>();
	protected int exit = 59;
	protected int dimension = 10;
	
	/*
	 * @brief Retorna a posicao da sa�da do labirinto
	 */
	public int getExit() {
		return exit;
	}
	public void setExit(int exit){
		this.exit = exit;	
	}
	
	/*
	 * @brief Retorna a dimensao total do labirinto
	 */
	public int getDimension(){
		return dimension;
	}
	
	public void setDimension(int dim){
		this.dimension = dim; 
	}

	/*
	 * @brief Gera o labirinto. E chamado o construtor da classe consoante o membro opcao da classe MazeBuilder.
	 */
	public abstract void gera();

	/*
	 * @brief Retorna o labirinto.
	 */
	public List<Character> getDados() {
		return maze;
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



	

}
