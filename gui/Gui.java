package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import logic.GameEngine;
import logic.MazeBuilder;

/**
 * The main class that manages the graphical-user interface.
 */

public class Gui {

	/**
	 * The main frame of the game.
	 */
	private JFrame frame;
	
	/**
	 * The game engine used to manage the board in gui mode.
	 */
	private GameEngine engine;
	
	/**
	 * The panel that contains the game itself in game mode, the maze in create maze mode,
	 * and an image otherwise.
	 */
	private GraphicMaze panel;
	
	/**
	 * The bottom panel in the game. Holds the options, start/load game and create maze buttons.
	 */
	private OptionButtons optionButtons;
	
	/**
	 * The top panel in the game. Holds the start and quit buttons.
	 */
	private StartQuitButtons startQuitButtons;
	
	/**
	 * The game controls. They default to W/A/S/D
	 */
	private int[] controls;

	/**
	 * The game's horizontal size.
	 */
	public static final int hSize = 600;
	
	/**
	 * The game's vertical size.
	 */
	public static final int vSize = 600;
	
	/**
	 * The standard button height in the program.
	 */
	public static final int buttonHeight = 25;

	/**
	 * Get @see #controls
	 * 
	 * @return {@link #controls}
	 */
	public int[] getControls() {
		return controls;
	}

	
	/**
	 * Set @see #controls
	 * @param index the index where to set the keyCode to.
	 * @param keyCode the keyCode
	 */
	public void setControl(int index, int keyCode) {
		controls[index] = keyCode;
	}

	
	/**
	 * Get @see #frame
	 * 
	 * @return {@link #frame}
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Get @see #engine
	 * 
	 * @return {@link #engine}
	 */
	public GameEngine getEngine() {
		return engine;
	}

	/**
	 * Get @see #panel
	 * 
	 * @return {@link #panel}
	 */
	public GraphicMaze getPanel() {
		return panel;
	}

	/**
	 * Class constructor. Creates the application.
	 */
	public Gui() {
		engine = new GameEngine(2);
		initialize();
	}

	/**
	 * Initialize the buttons/panels of the main frame.
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

		setOptionButtons(new OptionButtons(this));
		frame.getContentPane().add(getOptionButtons(), BorderLayout.SOUTH);

		int controls[] = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,
				KeyEvent.VK_RIGHT };

		this.controls = controls;
	}

	/**
	 * Makes the necessary arrangements to start the game.
	 * @param ge The game engine to use.
	 */
	public void startGame(GameEngine ge) {
		if (ge != null) {
			engine = ge;
		} else {
			/* Isto pode passado quase tudo para a initializeGame, depois trato disso */
			MazeBuilder mb = new MazeBuilder();
			mb.setOpcao(1);
			mb.setMazeDim(getOptionButtons().getOptDialog()
					.getTamanhoLabirinto());
			engine.setBoard(mb.getMaze());
			engine.getBoard().generate();

			engine.dragonMode = getOptionButtons().getOptDialog().getModoDragoes();
			engine.generateDragons(getOptionButtons().getOptDialog()
					.getNumeroDragoes());
			
			engine.getHero().resetEquipment();
			
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
	
	public void estadoFinal(int estado) {

		switch (estado) {
		case 0:
			JOptionPane.showMessageDialog(null,
					"You win!", "Escape the Maze",
					JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/resources/Trophy.png"));
			break;
		case 1:
			JOptionPane.showMessageDialog(null,
					"You lose!", "Escape the Maze",
					JOptionPane.ERROR_MESSAGE,new ImageIcon("src/resources/logo.png"));
			break;
		default:
			break;
		}
		return;
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
