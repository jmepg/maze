package logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Maze {
	public List<Character> maze = new ArrayList<Character>();
	public int exit = 59;
	public int dimension = 10;

	public int getExit() {
		return exit;
	}

	/*
	 * @brief Gera o labirinto. E chamado o construtor da classe consoante o membro opcao da classe MazeBuilder.
	 */
	abstract void gera();

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
