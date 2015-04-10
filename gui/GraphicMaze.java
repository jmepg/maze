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
import java.io.File;
import java.io.IOException;

public class GraphicMaze extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Gui gui;
	public Cli cli = new Cli();
	public boolean inGame = false;
	public boolean inCreationMode = false;
	
	private CreationMenu cm;

	Image image;

	/**
	 * Create the application.
	 */
	public GraphicMaze(Gui g) {
		addKeyListener(this);
		setPreferredSize(new Dimension(Gui.hSize, Gui.vSize));
		setMinimumSize(new Dimension(Gui.hSize, Gui.vSize));
		setLayout(new BorderLayout());
		
		gui = g;
		try {
			image = ImageIO.read(new File(
					"src/resources/dungeon-demon_wide.jpg"));
		} catch (IOException e) {
			System.out.println("Ficheiro nao existe!");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		cm = new CreationMenu();
		add(cm, BorderLayout.NORTH);
		cm.setVisible(false);
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			image = ImageIO.read(new File("resources/dungeon-demon_wide.jpg"))
					.getScaledInstance(Gui.hSize, Gui.vSize, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO: handle exception
		}

		if (inGame) {
			printMaze(g,Gui.hSize, Gui.hSize-50);
		}
		else if(inCreationMode){
			cm.setVisible(true);
			printMaze(g,Gui.hSize, Gui.hSize-75);
		}
		else {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	public void setCreateMenuAsVisible(){
		
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
				cli.estadoFinal(1);
				System.exit(0);
			}

			if (gui.getEngine().testWinCondition()) {
				cli.estadoFinal(0);
				System.exit(0);
			}

			gui.getEngine().placeEntities();
			for (int i = 0; i < gui.getEngine().board.getDimension(); i++)
				System.out.println("");
			cli.printMaze(gui.getEngine().board.getDados());
			repaint();
		}

	}
	
	public void printMaze(Graphics g, int hSize, int vSize){
		int size = gui.getEngine().board.getDimension();
		int tileHSize = hSize / size;
		int tileVSize = vSize / size;
		
		for (int vTile = 0; vTile < size; vTile++) {
			for (int hTile = 0; hTile < size; hTile++) {
				switch (gui.getEngine().board.getDados().get(
						vTile * size + hTile)) {
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
				g.fillRect(hTile * tileHSize, vTile * tileVSize, tileHSize,
						tileVSize);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
