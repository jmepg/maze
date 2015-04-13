package gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import symbolics.Direction;
import symbolics.GameResult;
import symbolics.MazeBuild;
import symbolics.Size;
import symbolics.Tile;

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
 * Manages the main screen containing the maze in-game and in creation mode, and
 * an image otherwise.
 */
public class GraphicMaze extends JPanel implements KeyListener {

	private Image dragon;
	private Image hero;
	private Image floor;
	private Image wall;
	private Image dart;
	private Image sword;
	private Image shield;
	private Image armedHero;

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

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
	 * 
	 * @return {@link #inGame}
	 */
	public boolean isInGame() {
		return inGame;
	}

	/**
	 * Get @see #cm
	 * 
	 * @return {@link #cm}
	 */
	public CreationMenu getCm() {
		return cm;
	}

	/**
	 * Set @see #inGame
	 * 
	 * @param inGame
	 *            {@link #inGame}
	 */
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	/**
	 * Get @see #inCreationMode
	 * 
	 * @return {@link #inCreationMode}
	 */
	public boolean isInCreationMode() {
		return inCreationMode;
	}

	/**
	 * Set @see #inCreationMode
	 * 
	 * @param inCreationMode
	 *            {@link #inCreationMode}
	 */
	public void setInCreationMode(boolean inCreationMode) {
		this.inCreationMode = inCreationMode;
	}

	/**
	 * Set @see #gui
	 * 
	 * @param gui
	 *            {@link #gui}
	 */
	public void setGui(Gui gui) {
		this.gui = gui;
	}

	/**
	 * Set @see #cm
	 * 
	 * @param cm
	 *            {@link #cm}
	 */
	public void setCm(CreationMenu cm) {
		this.cm = cm;
	}

	/**
	 * Set @see #image
	 * 
	 * @param image
	 *            {@link #image}
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Create the application.
	 * 
	 * @param g
	 *            {@link gui.Gui}
	 */
	public GraphicMaze(Gui g) {
		gui = g;
		setSize(Size.gameHSize, Size.gameVSize);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (inCreationMode) {
					cm.changeBoard(arg0.getX() - Size.createGameXi, arg0.getY()
							- Size.createGameYi, cm.getSeleccaoTile());
					repaint();
				}
			}
		});
		addKeyListener(this);
		setPreferredSize(new Dimension(Size.hSize, Size.vSize));
		setMinimumSize(new Dimension(Size.hSize, Size.vSize));
		setLayout(new BorderLayout());

		loadImages();

		cm = new CreationMenu(gui);
		cm.setVisible(false);
	}

	public void loadImages() {
		ImageIcon i;
		try {
			image = ImageIO.read(new File("src/gui/resources/background.png"))
					.getScaledInstance(this.getWidth(), this.getHeight(),
							Image.SCALE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		i = new ImageIcon(this.getClass().getResource("resources/Dragons/down.png"));
		dragon = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Hero/Empty/down.png"));
		hero = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Tiles/floor.png"));
		floor = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Tiles/roof.png"));
		wall = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Weapons/dart.png"));
		dart = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Weapons/shield.png"));
		shield = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Weapons/spear.png"));
		sword = i.getImage();

		i = new ImageIcon(this.getClass().getResource("resources/Hero/Weapon/down.png"));
		armedHero = i.getImage();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (inGame) {
			printMaze(g, gui.getEngine().getBoard(), 0, 0, this.getWidth(),
					this.getHeight());
		} else if (inCreationMode) {
			printMaze(g, cm.getCustomBoard().getBoard(), Size.createGameXi,
					Size.createGameYi, this.getWidth() - Size.createGameXi,
					this.getHeight() - Size.createGameYi);
		} else {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					image.getWidth(null), image.getHeight(null), null);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (inGame) {
			if (arg0.getKeyCode() == gui.getControls()[0])
				gui.getEngine().moveHeroi(Direction.UP);
			else if (arg0.getKeyCode() == gui.getControls()[1])
				gui.getEngine().moveHeroi(Direction.DOWN);
			else if (arg0.getKeyCode() == gui.getControls()[2])
				gui.getEngine().moveHeroi(Direction.LEFT);
			else if (arg0.getKeyCode() == gui.getControls()[3])
				gui.getEngine().moveHeroi(Direction.RIGHT);
			else
				return;

			gui.getEngine().moveDragoes();
			if (gui.getEngine().combate()) {
				repaint();
				gui.estadoFinal(GameResult.LOSE);
				disposeGame();
			}

			if (gui.getEngine().testWinCondition()) {
				repaint();
				gui.estadoFinal(GameResult.WIN);
				disposeGame();
			}

			gui.getEngine().placeEntities();
			repaint();
		}
	}

	/**
	 * Prints the maze on the screen. Only called by paintComponent().
	 * 
	 * @see #paintComponent(Graphics)
	 * @param g
	 *            Graphics from paintCompontent().
	 * @param board
	 *            The board to print.
	 * @param xi
	 *            The first horizontal pixel coordinate to print on.
	 * @param yi
	 *            The first vertical pixel coordinate to print on.
	 * @param hSize
	 *            The horizontal size of the rectangle to print on.
	 * @param vSize
	 *            The vertical size of the rectangle to print on.
	 */
	public void printMaze(Graphics g, Maze board, int xi, int yi, int hSize,
			int vSize) {
		int size = board.getDimension();

		double tileHSize = (double) hSize / size;
		double tileVSize = (double) vSize / size;

		int tileHSizeInt = (int) tileHSize;
		int tileVSizeInt = (int) tileVSize;

		for (int vTile = 0; vTile < size; vTile++) {
			for (int hTile = 0; hTile < size; hTile++) {
				switch (board.getMaze().get(vTile * size + hTile)) {
				case Tile.WALL:
					g.drawImage(wall, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.FLOOR:
					g.drawImage(floor, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.HERO:
					g.drawImage(hero, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.DRAGON:
					g.drawImage(dragon, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.SLEEPINGDRAGON:
					g.drawImage(dragon, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null); // E preciso mudar isto
					break;
				case Tile.SWORD:
					g.drawImage(sword, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.ARMEDHERO:
					g.drawImage(armedHero, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.SHIELD:
					g.drawImage(shield, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.TWOENTITIES:
					g.drawImage(floor, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.DART:
					g.drawImage(dart, (hTile * tileHSizeInt + xi), (vTile
							* tileVSizeInt + yi), tileHSizeInt, tileVSizeInt,
							null);
					break;
				case Tile.EXIT:
					g.setColor(Color.BLUE);
				default:
					break;
				}
			}

		}
	}

	/**
	 * Sets the custom Maze in the create maze option with the settings from the
	 * Options class.
	 * 
	 * @see gui.Options
	 * @see gui.CreationMenu
	 */
	public void startMenuCreation() {
		MazeBuilder mb = new MazeBuilder();
		mb.setOpcao(MazeBuild.CREATEDMAZE);
		mb.setMazeDim(gui.getOptionButtons().getOptDialog()
				.getTamanhoLabirinto());
		if (cm.getCustomBoard() == null)
			cm.createCustomBoard();
		cm.getCustomBoard().dragonMode = gui.getOptionButtons().getOptDialog()
				.getModoDragoes();
		cm.getCustomBoard().setBoard(mb.getMaze());
		cm.getCustomBoard().getBoard().generate();
	}

	/**
	 * Disposes the game screen when the game ends.
	 */
	public void disposeGame() {
		inGame = false;
		repaint();
	}

	/**
	 * Starts the maze creation interface.
	 * 
	 * @see gui.CreationMenu
	 */
	public void setCreateMenuAsVisible() {
		add(cm, BorderLayout.NORTH);
		inGame = false;
		cm.setVisible(true);
	}

	/**
	 * Disposes the create menu screen.
	 * 
	 * @param discardChanges
	 *            If set to true, the function will destroy all the customMaze
	 *            data.
	 *            <p>
	 *            If set to false, the function will keep it.
	 */
	public void disposeCreateMenu(boolean discardChanges) {
		inCreationMode = false;
		remove(gui.getPanel().getCm());
		cm.setVisible(false);
		repaint();
		if (discardChanges)
			cm.getCustomBoard().setBoard(null);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
