package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import cli.Cli;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.MouseAdapter;

import logic.Maze;
import logic.MazeBuilder;

public class GraphicMaze extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int hSize = Gui.hSize;
	public static final int vSize = Gui.vSize - 50;

	private static final int createGameXi = 0;
	private static final int createGameYi = 25;

	public Gui gui;
	public Cli cli = new Cli();
	private boolean inGame = false;
	private boolean inCreationMode = false;
	private CreationMenu cm;

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public boolean isInCreationMode() {
		return inCreationMode;
	}

	public void setInCreationMode(boolean inCreationMode) {
		this.inCreationMode = inCreationMode;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public void setCli(Cli cli) {
		this.cli = cli;
	}

	public void setCm(CreationMenu cm) {
		this.cm = cm;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	Image image;

	/**
	 * Create the application.
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

	public CreationMenu getCm() {
		return cm;
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
			cli.printMaze(gui.getEngine().board.getDados());
			repaint();
		}
	}

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

	public void disposeGame(){
		inGame = false;
		repaint();
	}
	
	public void setCreateMenuAsVisible() {
		add(cm, BorderLayout.NORTH);
		inGame = false;
		cm.setVisible(true);
	}

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
