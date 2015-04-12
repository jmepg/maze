package gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import logic.GameEngine;

/**
 * The class that manages the Save/Load Game Fialog.
 */
public class SaveLoad extends JDialog {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A newline. Used in the logs.
	 * @see logs
	 */
	static private final String newline = "\n";
	
	/**
	 * The maze game save file extension format.
	 */
	static private final String mgsf = "mgsf";
	
	/**
	 * The custom maze save file extension format.
	 */
	static private final String cmsf = "cmsf";
	

	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;

	/**
	 * The open a saved game button.
	 */
	private JButton openSavedGame;
	
	/**
	 * The open custom maze button.
	 */
	private JButton openCustomMaze;
	
	/**
	 * The save current state button.
	 */
	private JButton saveCurrentState;
	
	/**
	 * The cancel button.
	 */
	private JButton cancel;

	/**
	 * The panel with the save/load buttons
	 */
	private JPanel buttons;
	
	/**
	 * The text area that contains the save/load logs for this game session.
	 */
	private JTextArea logs;
	
	/**
	 * The file chooser that opens when the user wants to save or load something.
	 */
	private JFileChooser fc;

	/**
	 * The filename extension filter used to limit the options to the ones the game can recognize.
	 */
	private FileNameExtensionFilter f1 = new FileNameExtensionFilter(
			"Maze Escape Save Files (*.cmsf, *.mgsf)", cmsf, mgsf);
	/**
	 * The filename extension filter used to limit the options to the ones the game can recognize.
	 */
	private FileNameExtensionFilter f2 = new FileNameExtensionFilter("Mid-game Save File (*.mgsf)", mgsf);
	/**
	 * The filename extension filter used to limit the options to the ones the game can recognize.
	 */
	private FileNameExtensionFilter f3 = new FileNameExtensionFilter("Custom Maze Save File (*.cmsf)", cmsf);

	/** Saved games folder path */
	public static final String savedGamesFolder = System
			.getProperty("user.dir") + "/Saved Games/";

	/**
	 * The constructor of the class.
	 * @param gui {@link gui.Gui}
	 */
	public SaveLoad(Gui gui) {
		setTitle("Save/Load game");
		setModal(false);
		setLayout(new BorderLayout(0, 0));
		setResizable(false);
		createTextArea();
		createButtons();
		pack();
		setVisible(false);

		this.gui = gui;

		File savesFolder = new File(savedGamesFolder);
		if (!savesFolder.exists())
			savesFolder.mkdir();

		fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File(savedGamesFolder));
	}

	/**
	 * Creates the text area for the logs.
	 */
	public void createTextArea() {
		logs = new JTextArea(5, 20);
		logs.setMargin(new Insets(5, 5, 5, 5));
		logs.setEditable(false);
		add(logs, BorderLayout.CENTER);
	}

	/**
	 * Creates all the buttons in dialog and the respective actionPerformed listeners.
	 */
	public void createButtons() {
		buttons = new JPanel(new BorderLayout(0, 0));

		openSavedGame = new JButton("Open a saved game");
		openSavedGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gui.getPanel().isInGame() || gui.getPanel()
						.isInCreationMode()) {
					JOptionPane
							.showMessageDialog(
									null,
									"You can only open a save if you are not in the middle of a game or creating a maze!",
									"Error", JOptionPane.ERROR_MESSAGE);
					logs.append("Open command failed." + newline);
					return;
				}
				fc.resetChoosableFileFilters();
				fc.setFileFilter(f2);
				int returnVal = fc.showOpenDialog(SaveLoad.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ObjectInputStream is = null;
					try {
						is = new ObjectInputStream(new FileInputStream(savedGamesFolder + file.getName()));
						gui.setEngine((GameEngine) is.readObject());
						is.close();
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					gui.startGame(gui.getEngine());
					logs.append("Opening: " + file.getName() + newline);
					dispose();
				} else {
					logs.append("Open command cancelled by user." + newline);
				}
				logs.setCaretPosition(logs.getDocument().getLength());
			}
		});

		openCustomMaze = new JButton("Open a custom maze");
		openCustomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gui.getPanel().isInGame() || gui.getPanel()
						.isInCreationMode()) {
					JOptionPane
							.showMessageDialog(
									null,
									"You can only open a save if you are not in the middle of a game or creating a maze!",
									"Error", JOptionPane.ERROR_MESSAGE);
					logs.append("Open command failed." + newline);
					return;
				}
				fc.resetChoosableFileFilters();
				fc.setFileFilter(f3);
				int returnVal = fc.showOpenDialog(SaveLoad.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ObjectInputStream is = null;
					try {
						is = new ObjectInputStream(new FileInputStream(savedGamesFolder + file.getName()));
						gui.getPanel().getCm().setCustomBoard((GameEngine) is.readObject());
						is.close();
					} catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					gui.startGame(gui.getPanel().getCm().getCustomBoard());
					logs.append("Opening: " + file.getName() + newline);
					dispose();
				} else {
					logs.append("Open command cancelled by user." + newline);
				}
				logs.setCaretPosition(logs.getDocument().getLength());
			}
		});

		saveCurrentState = new JButton("Save current state");
		saveCurrentState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(gui.getPanel().isInGame() || gui.getPanel()
						.isInCreationMode())) {
					JOptionPane
							.showMessageDialog(
									null,
									"You can only save if you are in the middle of a game or creating a maze!",
									"Error", JOptionPane.ERROR_MESSAGE);
					logs.append("Save command failed." + newline);
					return;
				}
				fc.resetChoosableFileFilters();
				fc.setFileFilter(f1);
				int returnVal = fc.showSaveDialog(SaveLoad.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						ObjectOutputStream os;
						if (gui.getPanel().isInGame()) {
							logs.append("Saving: " + file.getName() + "."
									+ mgsf + newline);
							os = new ObjectOutputStream(new FileOutputStream(
									savedGamesFolder + file.getName() + "."
											+ mgsf));
						} else {
							logs.append("Saving: " + file.getName() + "."
									+ cmsf + newline);
							os = new ObjectOutputStream(new FileOutputStream(
									savedGamesFolder + file.getName() + "."
											+ cmsf));
						}
						
						os.writeObject(gui.getPanel().getCm().getCustomBoard());
						os.close();
						JOptionPane.showMessageDialog(null,
								"Game successfully saved.");
						setVisible(false);
					} catch (IOException arg0) {
						arg0.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Game was not successfully saved.");
					}
				} else {
					logs.append("Save command cancelled by user." + newline);
				}
				logs.setCaretPosition(logs.getDocument().getLength());
			}
		});

		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		try {
			Image img = ImageIO.read(getClass().getResource(
					"../resources/Open16.gif"));
			openSavedGame.setIcon(new ImageIcon(img));
			openCustomMaze.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		try {
			Image img = ImageIO.read(getClass().getResource(
					"../resources/Save16.gif"));
			saveCurrentState.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		buttons.add(openCustomMaze, BorderLayout.WEST);
		buttons.add(openSavedGame, BorderLayout.CENTER);
		buttons.add(saveCurrentState, BorderLayout.EAST);

		add(buttons, BorderLayout.NORTH);
		add(cancel, BorderLayout.SOUTH);
	}
}
