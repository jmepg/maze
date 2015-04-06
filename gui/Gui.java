package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import logic.GameEngine;
import logic.MazeBuilder;

public class Gui {

	private JFrame frame;
	private GameEngine engine;
	private GraphicMaze panel;

	public static final int hSize = 600;
	public static final int vSize = 600;
	
	public JFrame getFrame() {
		return frame;
	}

	public GameEngine getEngine() {
		return engine;
	}

	public GraphicMaze getPanel() {
		return panel;
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		engine = new GameEngine(2);
		engine.initializeGame();
		engine.placeEntities();
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0,hSize,vSize);
		
		this.panel = new GraphicMaze(engine);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		panel.setBounds(0, 50, hSize, vSize-100);
		
		JPanel panel_1 = new StartQuitButtons(this);
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new OptionButtons(frame, this);
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		
	}
	
	public void startGame(){
		/*MazeBuilder mb = new MazeBuilder();
		mb.setMazeType(op);*/
		panel.inGame = true;
		panel.requestFocus();
		panel.repaint();
	}

}
