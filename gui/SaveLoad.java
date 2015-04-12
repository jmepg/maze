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

@SuppressWarnings("serial")
public class SaveLoad extends JDialog {

	private Gui gui;

	private JButton openSavedGame;
	private JButton openCustomMaze;
	private JButton saveCurrentState;
	private JButton cancel;

	private JPanel buttons;
	private JTextArea logs;
	private JFileChooser fc;

	private FileNameExtensionFilter f1;
	private FileNameExtensionFilter f2;
	private FileNameExtensionFilter f3;

	static private final String newline = "\n";
	static private final String mgsf = "mgsf";
	static private final String cmsf = "cmsf";

	/** Saved games folder path */
	public static final String savedGamesFolder = System
			.getProperty("user.dir") + "/Saved Games/";

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

		f1 = new FileNameExtensionFilter(
				"Maze Escape Save Files (*.cmsf, *.mgsf)", cmsf, mgsf);
		f2 = new FileNameExtensionFilter("Mid-game Save File (*.mgsf)", mgsf);
		f3 = new FileNameExtensionFilter("Custom Maze Save File (*.cmsf)", cmsf);

	}

	public void createTextArea() {
		logs = new JTextArea(5, 20);
		logs.setMargin(new Insets(5, 5, 5, 5));
		logs.setEditable(false);
		add(logs, BorderLayout.CENTER);
	}

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
					} catch (IOException | ClassNotFoundException e1) {}
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
					} catch (IOException | ClassNotFoundException e1) {}
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
		}

		try {
			Image img = ImageIO.read(getClass().getResource(
					"../resources/Save16.gif"));
			saveCurrentState.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		buttons.add(openCustomMaze, BorderLayout.WEST);
		buttons.add(openSavedGame, BorderLayout.CENTER);
		buttons.add(saveCurrentState, BorderLayout.EAST);

		add(buttons, BorderLayout.NORTH);
		add(cancel, BorderLayout.SOUTH);
	}
}

// private Gui gui;
//
// private JTextField textField;
// private JSpinner spinner;
// private List<String> saves = new LinkedList<String>();
//
// private JLabel lblSave;
// private JLabel lblLoad;
//
// private JButton btnSave;
// private JButton btnCancelar;
//
// /** Saved games folder path */
// public static final String savedGamesFolder = System
// .getProperty("user.dir") + "/Saved Games/";
//
// public SaveLoad(JFrame frame, String title, final Gui gui) {
// super(frame, title);
// this.gui = gui;
// setModal(true);
// setBounds(400, 300, 400, 172);
// setResizable(false);
// setVisible(false);
//
//
// initialize();
//
// }
//
// public void initialize() {
// SpringLayout springLayout = new SpringLayout();
// getContentPane().setLayout(springLayout);
//
// lblSave = new JLabel("Save");
// springLayout.putConstraint(SpringLayout.NORTH, lblSave, 21,
// SpringLayout.NORTH, getContentPane());
// springLayout.putConstraint(SpringLayout.WEST, lblSave, 25,
// SpringLayout.WEST, getContentPane());
// getContentPane().add(lblSave);
//
// lblLoad = new JLabel("Load");
// springLayout.putConstraint(SpringLayout.NORTH, lblLoad, 20,
// SpringLayout.SOUTH, lblSave);
// springLayout.putConstraint(SpringLayout.WEST, lblLoad, 0,
// SpringLayout.WEST, lblSave);
// springLayout.putConstraint(SpringLayout.SOUTH, lblLoad, -66,
// SpringLayout.SOUTH, getContentPane());
// springLayout.putConstraint(SpringLayout.EAST, lblLoad, -320,
// SpringLayout.EAST, getContentPane());
// getContentPane().add(lblLoad);
//
// btnSave = new JButton("Save");
// springLayout.putConstraint(SpringLayout.NORTH, btnSave, 0,
// SpringLayout.NORTH, lblSave);
// springLayout.putConstraint(SpringLayout.EAST, btnSave, -41,
// SpringLayout.EAST, getContentPane());
// btnSave.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {
// saveStateToFile();
// }
// });
// btnSave.setBounds(12, 0, 117, 25);
// getContentPane().add(btnSave);
//
// btnCancelar = new JButton("Cancelar");
// springLayout.putConstraint(SpringLayout.WEST, btnCancelar, 142,
// SpringLayout.WEST, getContentPane());
// springLayout.putConstraint(SpringLayout.SOUTH, btnCancelar, -10,
// SpringLayout.SOUTH, getContentPane());
// springLayout.putConstraint(SpringLayout.EAST, btnCancelar, -144,
// SpringLayout.EAST, getContentPane());
// btnCancelar.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {
// dispose();
// }
// });
// btnCancelar.setBounds(12, 50, 117, 25);
// getContentPane().add(btnCancelar);
//
// textField = new JTextField();
// springLayout.putConstraint(SpringLayout.WEST, btnSave, 59,
// SpringLayout.EAST, textField);
// springLayout.putConstraint(SpringLayout.SOUTH, btnSave, 0,
// SpringLayout.SOUTH, textField);
// springLayout.putConstraint(SpringLayout.NORTH, textField, 0,
// SpringLayout.NORTH, lblSave);
// springLayout.putConstraint(SpringLayout.WEST, textField, 38,
// SpringLayout.EAST, lblSave);
// springLayout.putConstraint(SpringLayout.SOUTH, textField, 19,
// SpringLayout.NORTH, lblSave);
// getContentPane().add(textField);
// textField.setColumns(10);
//
// JButton btnLoad = new JButton("Load");
// springLayout.putConstraint(SpringLayout.NORTH, btnLoad, 16,
// SpringLayout.SOUTH, btnSave);
// springLayout.putConstraint(SpringLayout.WEST, btnLoad, 0,
// SpringLayout.WEST, btnSave);
// springLayout.putConstraint(SpringLayout.SOUTH, btnLoad, 0,
// SpringLayout.SOUTH, lblLoad);
// springLayout.putConstraint(SpringLayout.EAST, btnLoad, 0,
// SpringLayout.EAST, btnSave);
// btnLoad.addActionListener(new ActionListener() {
// public void actionPerformed(ActionEvent e) {
// dispose();
// }
// });
// getContentPane().add(btnLoad);
//
// String[] emptyList = {""};
//
// SpinnerListModel meses;
// updateListOfSaves();
//
// if (saves.size() != 0) {
// meses = new SpinnerListModel(saves);
//
// } else
// meses = new SpinnerListModel(emptyList);
//
// spinner = new JSpinner(meses);
//
// springLayout.putConstraint(SpringLayout.NORTH, spinner, 2,
// SpringLayout.NORTH, lblLoad);
// springLayout.putConstraint(SpringLayout.WEST, spinner, 0,
// SpringLayout.WEST, textField);
// springLayout.putConstraint(SpringLayout.EAST, spinner, 0,
// SpringLayout.EAST, textField);
// getContentPane().add(spinner);
// }
//
// public void saveStateToFile() {
// if (textField.getText().isEmpty())
// JOptionPane.showMessageDialog(null,
// "You can't create a file with no name!", "Error",
// JOptionPane.ERROR_MESSAGE);
// else if (textField.getText().contains(".")) {
// JOptionPane.showMessageDialog(null,
// "You can't create a file with two formats!", "Error",
// JOptionPane.ERROR_MESSAGE);
// }
//
// else {
// File savesFolder = new File(savedGamesFolder);
// if (!savesFolder.exists())
// savesFolder.mkdir();
//
// try {
// ObjectOutputStream os = new ObjectOutputStream(
// new FileOutputStream(savedGamesFolder + textField.getText() + ".dat"));
// os.writeObject(gui.getEngine());
// os.close();
// JOptionPane.showMessageDialog(null, "Game successfully saved.");
// setVisible(false);
// } catch (IOException arg0) {
// arg0.printStackTrace();
// JOptionPane.showMessageDialog(null,
// "Game was not successfully saved.");
// }
//
// }
//
// updateListOfSaves();
// }
//
// public void loadStateFromFile() {
// String filename = (String) spinner.getValue();
//
// ObjectInputStream is = null;
// try {
// is = new ObjectInputStream(new FileInputStream(filename + ".dat"));
// gui.setEngine((GameEngine) is.readObject());
// is.close();
// } catch (IOException | ClassNotFoundException e) {
// }
//
// }
//
// public void updateListOfSaves() {
// File folder = new File(savedGamesFolder);
// if (!folder.isDirectory())
// return;
//
// saves.clear();
// for (File file : folder.listFiles())
// if(file.getName().contains(".dat"))
// saves.add(file.getName());
//
// }

