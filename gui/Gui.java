package gui;

import java.awt.EventQueue;

import javax.sound.midi.VoiceStatus;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import logic.GameEngine;

public class Gui {

	private JFrame frame;
	private GameEngine engine;
	private GraphicMaze panel;

	
	
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
		frame.setBounds(0,0,600,600);
		
		this.panel = new GraphicMaze(engine);
		frame.getContentPane().add(panel, BorderLayout.EAST);
		frame.setVisible(true);
		panel.setBounds(0, 50, 800, 750);
		
		JPanel panel_1 = new StartQuitButtons(this);
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
	}
	
	public void startGame(){
		panel.inGame = true;
		panel.requestFocus();
		panel.repaint();
	}

}
