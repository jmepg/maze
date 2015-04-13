package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import symbolics.Size;
import symbolics.ValidateMaze;
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
	public static final int hSize = Size.hSize - Size.createGameXi;
	/**
	 * Vertical size of the maze window.
	 */
	public static final int vSize = Size.gameVSize - Size.createGameYi;

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
		setSize(Size.hSize, 25);
		this.gui = gui;
		createButtons();
		createCustomBoard();
		customBoard.getHero().setPosicao(-1);
		customBoard.setPosEscudo(-1); 
		customBoard.setPosEspada(-1);
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
		jcbEntidades.setPreferredSize(new Dimension(Size.hSize / 3, 25));
		add(jcbEntidades, BorderLayout.WEST);

		btnCancelar = new JButton("Cancel");
		btnCancelar.setPreferredSize(new Dimension(Size.hSize / 3, 25));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.getPanel().disposeCreateMenu(true);
			}
		});
		add(btnCancelar, BorderLayout.EAST);

		btnOk = new JButton("OK");
		btnOk.setPreferredSize(new Dimension(Size.hSize / 3, 25));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (validateMaze()) {
				case ValidateMaze.SUCCESS:
					JOptionPane.showMessageDialog(null,
							"Maze sucessfully created!", "",
							JOptionPane.INFORMATION_MESSAGE);
					gui.getPanel().disposeCreateMenu(false);
					break;
				case ValidateMaze.NOHEROEXISTS:
					JOptionPane.showMessageDialog(null,
							"The maze has to have a hero!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case ValidateMaze.NOEXITEXISTS:
					JOptionPane.showMessageDialog(null,
							"Your maze has to have an exit!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case ValidateMaze.NODRAGONSEXIST:
					JOptionPane.showMessageDialog(null,
							"The maze has to have at least a dragon!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case ValidateMaze.NOSWORDEXISTS:
					JOptionPane.showMessageDialog(null,
							"The maze has to have a sword!", "Error",
							JOptionPane.ERROR_MESSAGE);
					break;
				case ValidateMaze.NOSHIELDEXISTS:
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
				case ValidateMaze.DRAGONSNEXTTOHERO:
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
		if (customBoard.getHero().getPosicao() == -1)
			return ValidateMaze.NOHEROEXISTS;

		/* Testa se ha saida. */
		if (customBoard.getBoard().getExit() == -1)
			return ValidateMaze.NOEXITEXISTS;

		/* Testa se ha dragoes, espada e escudo */
		for (char tile = 0; tile < customBoard.getBoard().getMaze().size(); tile++) {
			if (customBoard.getBoard().getMaze().get(tile) == 'D')
				habemusDragao = true;
			else if (customBoard.getBoard().getMaze().get(tile) == 'E')
				habemusEspada = true;
			else if (customBoard.getBoard().getMaze().get(tile) == 'P')
				habemusEscudo = true;
		}
		if (!habemusDragao)
			return ValidateMaze.NODRAGONSEXIST;
		if (!habemusEspada)
			return ValidateMaze.NOSWORDEXISTS;

		/* Por ultimo, testa se ha dragoes ao lado do heroi */
		if (customBoard.getBoard().checkTile(customBoard.getHero().getPosicao() + 1) == 'D'
				|| customBoard.getBoard().checkTile(customBoard.getHero().getPosicao() - 1) == 'D'
				|| customBoard.getBoard().checkTile(customBoard.getHero().getPosicao() + customBoard.getBoard().getDimension()) == 'D'
				|| customBoard.getBoard().checkTile(customBoard.getHero().getPosicao() - customBoard.getBoard().getDimension()) == 'D')
			return ValidateMaze.DRAGONSNEXTTOHERO;

		/*
		 * Este check tem de estar no fim porque nao e um erro critico, apenas
		 * uma decisao extremamente questionavel.
		 */
		if (!habemusEscudo)
			return ValidateMaze.NOSHIELDEXISTS;

		/* Tudo bem, informar que o labirinto esta bem feito! */
		return ValidateMaze.SUCCESS;
	}

	/**
	 * Changes the custom board, validating the input.
	 * @param x The x coordinate of the tile.
	 * @param y The y coordinate of the tile.
	 * @param entity The entity to place in that tile.
	 */
	public void changeBoard(int x, int y, char entity) {
		int hTile = x * customBoard.getBoard().getDimension() / gui.getPanel().getHeight();
		int vTile = y * customBoard.getBoard().getDimension() / (gui.getPanel().getWidth()-Size.createGameYi);
		
		int tile = vTile * customBoard.getBoard().getDimension() + hTile;

		/*
		 * Validar a entrada, nao podemos permitir ao utilizador por dois
		 * herois, por exemplo!
		 */
		switch (entity) {
		case ' ':
			if (hTile == 0 || hTile == customBoard.getBoard().getDimension() - 1 || vTile == 0
					|| vTile == customBoard.getBoard().getDimension() - 1) {
				JOptionPane.showMessageDialog(null,
						"You can't place floor on the borders of the map!",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			break;
		case 'S':
			if (!(hTile == 0 || hTile == customBoard.getBoard().getDimension() - 1 || vTile == 0 || vTile == customBoard.getBoard()
					.getDimension() - 1)) {
				JOptionPane.showMessageDialog(null,
						"The exit has to be in the borders of the map!",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.getBoard().getExit() != -1)
				customBoard.getBoard().changeBoard(customBoard.getBoard().getExit(), 'X');
			customBoard.getBoard().setExit(tile);
			break;
		case 'H':
			if (customBoard.getBoard().getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The hero has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.getHero().getPosicao() != -1)
				customBoard.getBoard().changeBoard(customBoard.getHero().getPosicao(), ' ');
			customBoard.getHero().setPosicao(tile);
			break;
		case 'D':
			if (customBoard.getBoard().getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"A dragon has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			customBoard.getDragons().add(new Dragon(tile));
			break;
		case 'T':
			if (customBoard.getBoard().getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"A dart has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			customBoard.getDarts().add(new Dart(tile));
			break;
		case 'E':
			if (customBoard.getBoard().getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The sword has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.getPosEspada() != -1)
				customBoard.getBoard().changeBoard(customBoard.getPosEspada(), ' ');
			customBoard.setPosEspada(tile); 
			break;
		case 'P':
			if (customBoard.getBoard().getMaze().get(tile) != ' ') {
				JOptionPane.showMessageDialog(null,
						"The shield has to be placed on the floor!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (customBoard.getPosEscudo() != -1)
				customBoard.getBoard().changeBoard(customBoard.getPosEscudo(), ' ');
			customBoard.setPosEscudo(tile); 
			break;
		}
		
		if(customBoard.getBoard().getMaze().get(tile) == 'D')
			for(int dragon = 0; dragon < customBoard.getDragons().size(); dragon++){
				if(customBoard.getDragons().get(dragon).getPosicao() == tile) {
					customBoard.getDragons().remove(dragon);
					break;
				}
			}
		
		else if(customBoard.getBoard().getMaze().get(tile) == 'T')
			for(int dart = 0; dart < customBoard.getDarts().size(); dart++){
				if(customBoard.getDragons().get(dart).getPosicao() == tile) {
					customBoard.getDragons().remove(dart);
					break;
				}
			}
		
		customBoard.getBoard().changeBoard(tile, entity);
	}

	/**
	 * Converts the JComboBox index to the appropriate entity.
	 * @return The entity.
	 */
	
	public char getSeleccaoTile() {
		switch (jcbEntidades.getSelectedIndex()) {
		case 0:
			return symbolics.Tile.WALL;
		case 1:
			return symbolics.Tile.FLOOR;
		case 2:
			return symbolics.Tile.EXIT;
		case 3:
			return symbolics.Tile.HERO;
		case 4:
			return symbolics.Tile.DRAGON;
		case 5:
			return symbolics.Tile.DART;
		case 6:
			return symbolics.Tile.SHIELD;
		case 7:
			return symbolics.Tile.SWORD;
		default:
			return '\0';
		}
	}

}
