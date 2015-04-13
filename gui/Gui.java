package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import symbolics.GameResult;
import symbolics.MazeBuild;
import symbolics.Size;

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
	private BottomOptions optionButtons;
	
	/**
	 * The top panel in the game. Holds the start and quit buttons.
	 */
	private TopOptions startQuitButtons;
	
	/**
	 * The game controls. They default to W/A/S/D
	 */
	private int[] controls;

	/**
	 * Get @see controls
	 * 
	 * @return {@link #controls}
	 */
	public int[] getControls() {
		return controls;
	}

	
	/**
	 * Set @see controls
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
	 * Get @see engine
	 * 
	 * @return {@link #engine}
	 */
	public GameEngine getEngine() {
		return engine;
	}

	/**
	 * Get @see panel
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
		frame.setBounds(0, 0, Size.hSize, Size.vSize);

		this.panel = new GraphicMaze(this);
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		startQuitButtons = new TopOptions(this);
		frame.getContentPane().add(startQuitButtons, BorderLayout.NORTH);

		setOptionButtons(new BottomOptions(this));
		frame.getContentPane().add(getOptionButtons(), BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);

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
			mb.setOpcao(MazeBuild.RANDOMMAZE);
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

	/**
	 * Makes the necessary arrangements for starting the create maze menu.
	 */
	public void createGame() {
		panel.setInCreationMode(true);
		panel.startMenuCreation();
		panel.setCreateMenuAsVisible();
		panel.requestFocus();
		panel.repaint();
	}
	
	/**
	 * Outputs to the user the game result, once the game is over
	 * @param estado Whether the user has won or lost
	 */
	public void estadoFinal(int estado) {

		switch (estado) {
		case GameResult.WIN:
			JOptionPane.showMessageDialog(null,
					"You win!", "Escape the Maze",
					JOptionPane.INFORMATION_MESSAGE,new ImageIcon("resources/Trophy.png"));
			break;
		case GameResult.LOSE:
			JOptionPane.showMessageDialog(null,
					"You lose!", "Escape the Maze",
					JOptionPane.ERROR_MESSAGE,new ImageIcon("resources/logo.png"));
			break;
		default:
			break;
		}
		return;
	}

	/**
	 * Set @see engine
	 * @param engine {@link engine}
	 */
	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	/**
	 * Get @see engine
	 * @return {@link optionButtons}
	 */
	public BottomOptions getOptionButtons() {
		return optionButtons;
	}

	/**
	 * Set @see optionButtons
	 * @param optionButtons {@link optionButtons}
	 */
	public void setOptionButtons(BottomOptions optionButtons) {
		this.optionButtons = optionButtons;
	}

}
