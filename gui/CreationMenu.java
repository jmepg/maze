package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Dart;
import logic.Dragon;
import logic.GameEngine;

/**
 * Manages the logic and buttons behind the maze creation by the user.
 */
public class CreationMenu extends JPanel {

	/**
	 * Horizontal size of the maze window.
	 */
	public static final int hSize = Gui.hSize - GraphicMaze.createGameXi;
	/**
	 * Vertical size of the maze window.
	 */
	public static final int vSize = GraphicMaze.vSize - GraphicMaze.createGameYi;

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The cancel button.
	 */
	private JButton btnCancelar;
	/**
	 * The ok button.
	 */
	private JButton btnOk;
	
	/**
	 * The combo box with the various game entities, for the user to choose.
	 */
	private JComboBox<?> jcbEntidades;
	
	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;
	
	/**
	 * The engine that contains the custom maze.
	 */
	private GameEngine customBoard;

	/** 
	 * Set @see #customBoard
	 * @param customBoard {@link #customBoard}
	 */
	public void setCustomBoard(GameEngine customBoard) {
		this.customBoard = customBoard;
	}

	/** 
	 * Get @see #customBoard.
	 * @return {@link #customBoard}
	 */
	public GameEngine getCustomBoard() {
		return customBoard;
	}
	
	/**
	 * Creates a custom Board,
	 */
	public void createCustomBoard(){
		customBoard = new GameEngine(2);
	}

	/**
	 * The class constructor.
	 * @param gui {@link #gui}
	 */
	public CreationMenu(Gui gui) {
		setLayout(new BorderLayout(0, 0));
		setSize(Gui.hSize, 25);
		this.gui = gui;
		createButtons();
		createCustomBoard();
		customBoard.h1.setPosicao(-1);
		customBoard.posEscudo = -1;
		customBoard.posEspada = -1;
	}

	/**
	 * Get @see #jcbEntidades
	 * @return {@link #jcbEntidades}
	 */
	public JComboBox<?> getJcbEntidades() {
		return jcbEntidades;
	}

	/**
	 * Creates the buttons and the Combo box for user interface.
	 */
	public void createButtons() {
		String[] entidades = { "Wall", "Floor", "Exit", "Hero", "Dragon",
				"Dart", "Shield", "Sword" };
		jcbEntidades = new JComboBox<>(entidades);
		jcbEntidades.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(jcbEntidades, BorderLayout.WEST);

		btnCancelar = new JButton("Cancel");
		btnCancelar.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.getPanel().disposeCreateMenu(true);
			}
		});
		add(btnCancelar, BorderLayout.EAST);

		btnOk = new JButton("OK");
		btnOk.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (validateMaze()) {
				case 0:
					JOptionPane.showMessageDialog(null,
							"Maze sucessfully created!", "",
							JOptionPane.INFORMATION_MESSAGE);
					gui.getPanel().disposeCreateMenu(false);
					break;
				case 1:
					JOptionPane.showMessageDialog(null,
							"The maze has to have a hero!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 2:
					JOptionPane.showMessageDialog(null,
							"Your maze has to have an exit!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 3:
					JOptionPane.showMessageDialog(null,
							"The maze has to have at least a dragon!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 4:
					JOptionPane.showMessageDialog(null,
							"The maze has to have a sword!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 5:
					int opt = JOptionPane
							.showConfirmDialog(
									null,
									"The maze has no shield. You will be vulnerable to the dragon's fireballs. Proceed anyway?",
									"Warning", JOptionPane.YES_NO_OPTION);
					if (opt == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null,
								"Maze sucessfully created!", "",
								JOptionPane.INFORMATION_MESSAGE);
						gui.getPanel().disposeCreateMenu(false);

					}
					break;
				case 6:
					JOptionPane.showMessageDialog(null,
							"You can't put the hero next to a dragon!",
							"Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
		});
		add(btnOk, BorderLayout.CENTER);

	}

	/**
	 * When the user wants to save the maze, this function tests if it is a valid maze
	 * @return 0 upon a valid maze, 1-6 depending on what it's missing.
	 */
	public int validateMaze() {

		boolean habemusDragao = false;
		boolean habemusEspada = false;
		boolean habemusEscudo = false;

		/* Testa se ha heroi. Se nao houver, o labirinto nao pode ser jogado */
		if (customBoard.h1.getPosicao() == -1)
			return 1;

		/* Testa se ha saida. */
		if (customBoard.board.getExit() == -1)
			return 2;

		/* Testa se ha dragoes, espada e escudo */
		for (char tile = 0; tile < customBoard.board.getMaze().size(); tile++) {
			if (customBoard.board.getMaze().get(tile) == 'D')
				habemusDragao = true;
			else if (customBoard.board.getMaze().get(tile) == 'E')
				habemusEspada = true;
			else if (customBoard.board.getMaze().get(tile) == 'P')
				habemusEscudo = true;
		}
		if (!habemusDragao)
			return 3;
		if (!habemusEspada)
			return 4;

		/* Por ultimo, testa se ha dragoes ao lado do heroi */
		if (customBoard.board.checkTile(customBoard.h1.getPosicao() + 1) == 'D'
				|| customBoard.board.checkTile(customBoard.h1.getPosicao() - 1) == 'D'
				|| customBoard.board.checkTile(customBoard.h1.getPosicao() + customBoard.board.getDimension()) == 'D'
				|| customBoard.board.checkTile(customBoard.h1.getPosicao() - customBoard.board.getDimension()) == 'D')
			return 6;

		/*
		 * Este check tem de estar no fim porque nao e um erro critico, apenas
		 * uma decisao extremamente questionavel.
		 */
		if (!habemusEscudo)
			return 5;

		/* Tudo bem, informar que o labirinto esta bem feito! */
		return 0;
	}

	/**
	 * Changes the custom board, validating the input.
	 * @param x The x coordinate of the tile.
	 * @param y The y coordinate of the tile.
	 * @param entity The entity to place in that tile.
	 */
	public void changeBoard(int x, int y, char entity) {
		int hTile = x * customBoard.board.getDimension() / hSize;
		int vTile = y * customBoard.board.getDimension() / vSize;
		int tile = vTile * customBoard.board.getDimension() + hTile;

		/*
		 * Validar a entrada, nao podemos permitir ao utilizador por dois
		 * herois, por exemplo!
		 */
		switch (entity) {
		case ' ':
			if (hTile == 0 || hTile == customBoard.board.getDimension() - 1 || vTile == 0
					|| vTile == customBoard.board.getDimension() - 1) {
				JOptionPane.showMessageDialog(null,
						"You can't place floor on the borders of the map!",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			break;
		case 'S':
			if (!(hTile == 0 || hTile == customBoard.board.getDimension() - 1 || vTile == 0 || vTile == customBoard.board
					.getDimension() - 1)) {
				JOptionPane.showMessageDialog(null,
						"The exit has to be in the borders of the map!",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.board.getExit() != -1)
				customBoard.board.changeBoard(customBoard.board.getExit(), 'X');
			customBoard.board.setExit(tile);
			break;
		case 'H':
			if (customBoard.board.getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The hero has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.h1.getPosicao() != -1)
				customBoard.board.changeBoard(customBoard.h1.getPosicao(), ' ');
			customBoard.h1.setPosicao(tile);
			break;
		case 'D':
			if (customBoard.board.getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"A dragon has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			customBoard.dragons.add(new Dragon(tile));
			break;
		case 'T':
			if (customBoard.board.getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"A dart has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			customBoard.darts.add(new Dart(tile));
			break;
		case 'E':
			if (customBoard.board.getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The sword has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.posEspada != -1)
				customBoard.board.changeBoard(customBoard.posEspada, ' ');
			customBoard.posEspada = tile;
			break;
		case 'P':
			if (customBoard.board.getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The shield has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.posEscudo != -1)
				customBoard.board.changeBoard(customBoard.posEscudo, ' ');
			customBoard.posEscudo = tile;
			break;
		}
		
		if(customBoard.board.getMaze().get(tile) == 'D')
			for(int dragon = 0; dragon < customBoard.dragons.size(); dragon++){
				if(customBoard.dragons.get(dragon).getPosicao() == tile) {
					customBoard.dragons.remove(dragon);
					break;
				}
			}
		
		else if(customBoard.board.getMaze().get(tile) == 'T')
			for(int dart = 0; dart < customBoard.darts.size(); dart++){
				if(customBoard.dragons.get(dart).getPosicao() == tile) {
					customBoard.dragons.remove(dart);
					break;
				}
			}
		
		customBoard.board.changeBoard(tile, entity);
	}

	/**
	 * Converts the JComboBox index to the appropriate entity.
	 * @return The entity.
	 */
	
	public char getSeleccaoTile() {
		switch (jcbEntidades.getSelectedIndex()) {
		case 0:
			return 'X';
		case 1:
			return ' ';
		case 2:
			return 'S';
		case 3:
			return 'H';
		case 4:
			return 'D';
		case 5:
			return 'T';
		case 6:
			return 'P';
		case 7:
			return 'E';
		default:
			return '\0';
		}
	}

}
