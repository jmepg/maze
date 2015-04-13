package cli;

import logic.GameEngine;

public class Launcher {

	public static void main(String[] args) {
		GameEngine g = new GameEngine(0);
		g.initializeGame(null);
		g.jogar();
	}

}
