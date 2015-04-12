package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.MouseAdapter;

import logic.Maze;
import logic.MazeBuilder;

/**
 * Manages the main screen containing the maze in-game and in creation mode, and an image otherwise.
 */
public class GraphicMaze extends JPanel implements KeyListener {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The panel's horizontal size.
	 */
	public static final int hSize = Gui.hSize;
	
	/**
	 * The panel's vertical size. 
	 */
	public static final int vSize = Gui.vSize - 2*Gui.buttonHeight;

	/** 
	 * The first horizontal coordinate of the maze in creation mode. 
	 */
	public static final int createGameXi = 0;
	/**
	 * The first vertical coordinate of the maze in creation mode. 
	 */
	public static final int createGameYi = Gui.buttonHeight;

	/**
	 * {@link gui.Gui}
	 */
	public Gui gui;
	
	/**
	 * Determines if the user is currently playing.
	 */
	private boolean inGame = false;
	/**
	 * Determines if the user is currently creating a maze.
	 */
	private boolean inCreationMode = false;
	
	/**
	 * The image used in the main menu.
	 */
	private Image image;
	
	
	/**
	 * {@link gui.CreationMenu}
	 */
	private CreationMenu cm;

	/**
	 * Get @see #inGame
	 * @return {@link #inGame}
	 */
	public boolean isInGame() {
		return inGame;
	}
	
	/**
	 * Get @see #cm
	 * @return {@link #cm}
	 */
	public CreationMenu getCm() {
		return cm;
	}

	/**
	 * Set @see #inGame
	 * @param inGame {@link #inGame}
	 */
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	/**
	 * Get @see #inCreationMode
	 * @return {@link #inCreationMode}
	 */
	public boolean isInCreationMode() {
		return inCreationMode;
	}

	/**
	 * Set @see #inCreationMode
	 * @param inCreationMode {@link #inCreationMode}
	 */
	public void setInCreationMode(boolean inCreationMode) {
		this.inCreationMode = inCreationMode;
	}

	/**
	 * Set @see #gui
	 * @param gui {@link #gui}
	 */
	public void setGui(Gui gui) {
		this.gui = gui;
	}

	/**
	 * Set @see #cm
	 * @param cm {@link #cm}
	 */
	public void setCm(CreationMenu cm) {
		this.cm = cm;
	}

	/**
	 * Set @see #image
	 * @param image {@link #image}
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Create the application.
	 * @param g {@link gui.Gui}
	 */
	public GraphicMaze(Gui g) {
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(inCreationMode){
				cm.changeBoard(arg0.getX() - createGameXi, arg0.getY()
						- createGameYi, cm.getSeleccaoTile());
				repaint();
				}
			}
		});
		addKeyListener(this);
		setPreferredSize(new Dimension(Gui.hSize, Gui.vSize));
		setMinimumSize(new Dimension(Gui.hSize, Gui.vSize));
		setLayout(new BorderLayout());

		gui = g;
		try {
			image = ImageIO.read(new File(
					"src/resources/dungeon-demon_wide.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		cm = new CreationMenu(gui);
		cm.setVisible(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (inGame) {
			printMaze(g, gui.getEngine().board, 0, 0, GraphicMaze.hSize,
					GraphicMaze.vSize);
		} else if (inCreationMode) {
			printMaze(g, cm.getCustomBoard().board, createGameXi, createGameYi,
					CreationMenu.hSize - createGameXi, CreationMenu.vSize
							- createGameYi);
		} else {
			g.drawImage(image, 0, 0, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (inGame) {
			if (arg0.getKeyCode() == gui.getControls()[0])
				gui.getEngine().moveHeroi('w');
			else if (arg0.getKeyCode() == gui.getControls()[1])
				gui.getEngine().moveHeroi('s');
			else if (arg0.getKeyCode() == gui.getControls()[2])
				gui.getEngine().moveHeroi('a');
			else if (arg0.getKeyCode() == gui.getControls()[3])
				gui.getEngine().moveHeroi('d');
			else
				return;

			gui.getEngine().moveDragoes();
			if (gui.getEngine().combate()) {
				gui.estadoFinal(1);
				disposeGame();
			}

			if (gui.getEngine().testWinCondition()) {
				gui.estadoFinal(0);
				disposeGame();
			}

			gui.getEngine().placeEntities();
			for (int i = 0; i < gui.getEngine().board.getDimension(); i++)
				System.out.println("");
			repaint();
		}
	}

	/**
	 * Prints the maze on the screen. Only called by paintComponent().
	 * @see #paintComponent(Graphics)
	 * @param g Graphics from paintCompontent().
	 * @param board The board to print.
	 * @param xi The first horizontal pixel coordinate to print on.
	 * @param yi The first vertical pixel coordinate to print on.
	 * @param hSize The horizontal size of the rectangle to print on.
	 * @param vSize The vertical size of the rectangle to print on.
	 */
	public void printMaze(Graphics g, Maze board, int xi, int yi, int hSize,
			int vSize) {
		int size = board.getDimension();
		int tileHSize = hSize / size;
		int tileVSize = vSize / size;

		for (int vTile = 0; vTile < size; vTile++) {
			for (int hTile = 0; hTile < size; hTile++) {
				switch (board.getDados().get(vTile * size + hTile)) {
				case 'X':
					g.setColor(Color.red);
					break;
				case ' ':
					g.setColor(Color.white);
					break;
				case 'H':
					g.setColor(Color.cyan);
					break;
				case 'D':
					g.setColor(Color.green);
					break;
				case 'd':
					g.setColor(Color.green); // E preciso mudar isto
					break;
				case 'E':
					g.setColor(Color.gray);
					break;
				case 'A':
					g.setColor(Color.darkGray);
					break;
				case 'P':
					g.setColor(Color.pink);
					break;
				case 'F':
					g.setColor(Color.orange);
					break;
				case 'T':
					g.setColor(Color.yellow);
					break;
				case 'S':
					g.setColor(Color.BLUE);
				default:
					break;
				}
				g.fillRect(hTile * tileHSize + xi, vTile * tileVSize + yi,
						tileHSize, tileVSize);
			}
		}
	}

	/**
	 * Sets the custom Maze in the create maze option with the settings from the Options class.
	 * 
	 * @see gui.Options
	 * @see gui.CreationMenu
	 */
	public void startMenuCreation() {
		MazeBuilder mb = new MazeBuilder();
		mb.setMazeType(2);
		mb.setMazeDim(gui.getOptionButtons().getOptDialog()
				.getTamanhoLabirinto());
		if (cm.getCustomBoard() == null)
			cm.createCustomBoard();
		cm.getCustomBoard().dragonMode = gui.getOptionButtons().getOptDialog().getModoDragoes();
		cm.getCustomBoard().board = mb.getMaze();
		cm.getCustomBoard().board.gera();
	}

	/**
	 * Disposes the game screen when the game ends.
	 */
	public void disposeGame(){
		inGame = false;
		repaint();
	}
	
	/**
	 * Starts the maze creation interface.
	 * @see gui.CreationMenu
	 */
	public void setCreateMenuAsVisible() {
		add(cm, BorderLayout.NORTH);
		inGame = false;
		cm.setVisible(true);
	}

	/**
	 * Disposes the create menu screen.
	 * @param discardChanges If set to true, the function will destroy all the customMaze data.
	 * <p> If set to false, the fucntion will keep it.
	 */
	public void disposeCreateMenu(boolean discardChanges) {
		inCreationMode = false;
		remove(gui.getPanel().getCm());
		cm.setVisible(false);
		repaint();
		if (discardChanges)
			cm.getCustomBoard().board = null;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
