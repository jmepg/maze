package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.GameEngine;

public class Gui extends JFrame {
	private JPanel maze;
	private GameEngine engine;
	
	public Gui(){
		initialize();
	}
	
	public void initialize(){
		setSize(new Dimension(GraphicMaze.hSize,GraphicMaze.vSize));
		setTitle("Dungeon Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(GraphicMaze.hSize,GraphicMaze.vSize));
		
		engine = new GameEngine(2);
		engine.initializeGame();
		maze = new GraphicMaze(engine);
		getContentPane().add(maze);
		setVisible(true);
		
		maze.requestFocus();
	}
	
	public void start(){
		
	}
}
