package logic;

import logic.RandomMaze;
import logic.StaticMaze;
import logic.Maze;

public class MazeBuilder {
	private int opcao = -1;
	private int dimension = 10;

	public int  getOpcao(){
		return opcao;
	}
	
	public void setOpcao(int op){
		this.opcao = op;
	}
	
	
	/*
	 * @brief Define o tipo de labirinto (aleatorio ou o ja definido).
	 * 
	 * @param op opcao a representar o tipo (0 = estatico, 1 = aleatorio, 2= criado pelo utilizador))
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
		else if(opcao == 0)
			return new StaticMaze();
		else
			return new CreatedMaze(dimension);

	}
}
