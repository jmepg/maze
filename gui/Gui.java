package gui;

import javax.swing.JFrame;

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

	public void setControl(int index, int keyCode) {
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
		frame = new JFrame("Escape the Maze");
		frame.setBounds(0, 0, hSize, vSize);

		this.panel = new GraphicMaze(this);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setResizable(false);
		panel.setBounds(0, 50, hSize, vSize - 100);

		startQuitButtons = new StartQuitButtons(this);
		frame.getContentPane().add(startQuitButtons, BorderLayout.NORTH);

		setOptionButtons(new OptionButtons(frame, this));
		frame.getContentPane().add(getOptionButtons(), BorderLayout.SOUTH);

		int controls[] = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT };

		this.controls = controls;
	}

	public void startGame(boolean useCustomMaze) {
		if (useCustomMaze) {
			engine = panel.getCm().getCustomBoard();
		} else {
			MazeBuilder mb = new MazeBuilder();
			mb.setMazeType(1);
			mb.setMazeDim(getOptionButtons().getOptDialog()
					.getTamanhoLabirinto());
			engine.board = mb.getMaze();
			engine.board.gera();

			switch (getOptionButtons().getOptDialog().getModoDragoes()) {
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

			engine.generateDragons(getOptionButtons().getOptDialog()
					.getNumeroDragoes());

			engine.initializeGame(mb);
			engine.placeEntities();
		}
		panel.setInGame(true);
		panel.requestFocus();
		panel.repaint();
	}

	public void createGame() {
		panel.setInCreationMode(true);
		panel.startMenuCreation();
		panel.setCreateMenuAsVisible();
		panel.requestFocus();
		panel.repaint();
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	public OptionButtons getOptionButtons() {
		return optionButtons;
	}

	public void setOptionButtons(OptionButtons optionButtons) {
		this.optionButtons = optionButtons;
	}

}
