package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import logic.Dragon;
import logic.GameEngine;
import logic.MazeBuilder;

public class Gui {

	private JFrame frame;
	private GameEngine engine;
	private GraphicMaze panel;
	private OptionButtons optionButtons;
	private StartQuitButtons startQuitButtons;
	private int[] controls;

	public static final int hSize = 600;
	public static final int vSize = 600;
	
	public int[] getControls() {
		return controls;
	}
	
	public void setControl(int index, int keyCode){
		controls[index] = keyCode;
	}
	
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0,0,hSize,vSize);
		
		this.panel = new GraphicMaze(this);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setResizable(false);
		panel.setBounds(0, 50, hSize, vSize-100);
		
		startQuitButtons = new StartQuitButtons(this);
		frame.getContentPane().add(startQuitButtons, BorderLayout.NORTH);
		
		optionButtons = new OptionButtons(frame, this);
		frame.getContentPane().add(optionButtons, BorderLayout.SOUTH);
		
		int controls[] = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
		
		this.controls = controls;
	}
	
	public void startGame(){
		MazeBuilder mb = new MazeBuilder();
		mb.setMazeType(1);
		mb.setMazeDim(optionButtons.getOptDialog().getTamanhoLabirinto());
		engine.board = mb.getMaze();
		engine.board.gera();
		
		switch(optionButtons.getOptDialog().getModoDragoes()){
		case 1: 
			engine.dragonMode = Dragon.Mode.STATIC;
			break;
		case 2: 
			engine.dragonMode = Dragon.Mode.MOVABLE;
			break;
		case 3:
			engine.dragonMode = Dragon.Mode.SLEEPING;
			break;
		default:
			break;
		}
		
		engine.generateDragons(optionButtons.getOptDialog().getNumeroDragoes());
		
		engine.initializeGame(mb);
		engine.placeEntities();
				
		panel.inGame = true;
		panel.requestFocus();
		panel.repaint();
	}

}
