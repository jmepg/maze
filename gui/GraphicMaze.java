package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import cli.Cli;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import logic.*;

public class GraphicMaze extends JPanel implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int hSize = 600;
	public static final int vSize = 550;

	public GameEngine ge;
	public Cli cli = new Cli();
	public boolean inGame = false;
	
	BufferedImage image;
	
	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {

	/*
	 * GameEngine g = new GameEngine(2); g.initializeGame();
	 * 
	 * JFrame f = new JFrame(); f.setSize(new Dimension(hSize, vSize));
	 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * f.getContentPane().setLayout(new BorderLayout(0, 0));
	 * 
	 * JPanel panel = new GraphicMaze(g.board.getDados());
	 * 
	 * f.getContentPane().add(panel); f.setVisible(true);
	 * 
	 * g.jogar();
	 */

	/*
	 * GameEngine g = new GameEngine(); g.initializeGame(); g.jogar();
	 * 
	 * EventQueue.invokeLater(new Runnable() { public void run() { try { gui
	 * window = new gui(); window.frame.setVisible(true); } catch (Exception e)
	 * { e.printStackTrace(); } } });
	 */
	// }

	/**
	 * Create the application.
	 */
	public GraphicMaze(GameEngine g){
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(hSize, vSize));
		this.setMinimumSize(new Dimension(hSize, vSize));
		this.ge = g;
		try {
			image = ImageIO.read(new File("src/resources/dungeon-demon_wide.jpg"));
		} catch (FileNotFoundException e) {
			System.out.println("FIcheiro nao existe!");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	// private void initialize() {

	/*
	 * frame = new JFrame("Projeto Guiado"); frame.setSize(new Dimension(800,
	 * 600)); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.getContentPane().setLayout(new BorderLayout(0, 0));
	 * 
	 * JPanel panel = new AreaJogo(frame.getWidth(), frame.getHeight());
	 * panel.setPreferredSize(new Dimension(frame.getWidth(), frame
	 * .getHeight())); panel.setMinimumSize(new Dimension(frame.getWidth(),
	 * frame.getHeight())); frame.getContentPane().add(panel);
	 */

	/*
	 * JPanel panel = new JPanel(); panel.setPreferredSize(new Dimension(100,
	 * 100)); panel.setMinimumSize(new Dimension(100, 100));
	 * frame.getContentPane().add(panel, BorderLayout.EAST); GridBagLayout
	 * gbl_panel = new GridBagLayout(); gbl_panel.columnWidths = new int[] {0,
	 * 5}; gbl_panel.rowHeights = new int[] {0, 0, 5}; gbl_panel.columnWeights =
	 * new double[]{0.0, Double.MIN_VALUE}; gbl_panel.rowWeights = new
	 * double[]{0.0, 0.0, Double.MIN_VALUE}; panel.setLayout(gbl_panel);
	 */

	/*
	 * JButton btnNewButton = new JButton("Inicio"); GridBagConstraints
	 * gbc_btnNewButton = new GridBagConstraints(); gbc_btnNewButton.insets =
	 * new Insets(0, 0, 5, 0); gbc_btnNewButton.gridx = 0;
	 * gbc_btnNewButton.gridy = 0; panel.add(btnNewButton, gbc_btnNewButton);
	 * 
	 * JButton btnNewButton_1 = new JButton("New button");
	 * btnNewButton_1.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent arg0) { } }); GridBagConstraints
	 * gbc_btnNewButton_1 = new GridBagConstraints(); gbc_btnNewButton_1.anchor
	 * = GridBagConstraints.NORTH; gbc_btnNewButton_1.gridx = 0;
	 * gbc_btnNewButton_1.gridy = 1; panel.add(btnNewButton_1,
	 * gbc_btnNewButton_1);
	 */

	/*
	 * Panel panel_1 = new AreaJogo(); frame.getContentPane().add(panel_1,
	 * BorderLayout.CENTER);
	 */
	// }

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		try {
			@SuppressWarnings("unused")
			final BufferedImage image = ImageIO.read(new File("resources/dungeon-demon_wide.jpg"));
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		if (inGame) {
			int size = (int) Math.sqrt(ge.board.getDados().size());
			int tileHSize = hSize / size;
			int tileVSize = vSize / size;

			for (int vTile = 0; vTile < size; vTile++) {
				for (int hTile = 0; hTile < size; hTile++) {
					switch (ge.board.getDados().get(vTile * size + hTile)) {
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
		else {
			g.drawImage(image, 0,0,null);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			ge.moveHeroi('a');
			break;
		case KeyEvent.VK_RIGHT:
			ge.moveHeroi('d');
			break;
		case KeyEvent.VK_DOWN:
			ge.moveHeroi('s');
			break;
		case KeyEvent.VK_UP:
			// System.out.println("Estou a receber tudo bem!");
			ge.moveHeroi('w');
			break;
		}
		ge.moveDragoes();
		if (ge.combate()) {
			cli.estadoFinal(1);
			System.exit(0);
		}

		if (ge.testWinCondition()) {
			cli.estadoFinal(0);
			System.exit(0);
		}

		ge.placeEntities();
		for (int i = 0; i < ge.board.dimension; i++)
			System.out.println("");
		cli.printMaze(ge.board.getDados());
		repaint();
		// ge.deleteEntities();
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
