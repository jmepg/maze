package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpinnerListModel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import logic.GameEngine;

@SuppressWarnings("serial")
public class SaveLoad extends JDialog {
	private Gui gui;

	private JTextField textField;
	private JSpinner spinner;
	private List<String> saves = new LinkedList<String>();

	private JLabel lblSave;
	private JLabel lblLoad;

	private JButton btnSave;
	private JButton btnCancelar;

	/** Saved games folder path */
	private static final String savedGamesFolder = System
			.getProperty("user.dir") + "/Saved Games/";

	public SaveLoad(JFrame frame, String title, final Gui gui) {
		super(frame, title);
		this.gui = gui;
		setModal(true);
		setBounds(400, 300, 400, 172);
		setResizable(false);
		setVisible(false);

		initialize();

	}

	public void initialize() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		lblSave = new JLabel("Save");
		springLayout.putConstraint(SpringLayout.NORTH, lblSave, 21,
				SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSave, 25,
				SpringLayout.WEST, getContentPane());
		getContentPane().add(lblSave);

		lblLoad = new JLabel("Load");
		springLayout.putConstraint(SpringLayout.NORTH, lblLoad, 20,
				SpringLayout.SOUTH, lblSave);
		springLayout.putConstraint(SpringLayout.WEST, lblLoad, 0,
				SpringLayout.WEST, lblSave);
		springLayout.putConstraint(SpringLayout.SOUTH, lblLoad, -66,
				SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblLoad, -320,
				SpringLayout.EAST, getContentPane());
		getContentPane().add(lblLoad);

		btnSave = new JButton("Save");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 0,
				SpringLayout.NORTH, lblSave);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -41,
				SpringLayout.EAST, getContentPane());
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveStateToFile();
			}
		});
		btnSave.setBounds(12, 0, 117, 25);
		getContentPane().add(btnSave);

		btnCancelar = new JButton("Cancelar");
		springLayout.putConstraint(SpringLayout.WEST, btnCancelar, 142,
				SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancelar, -10,
				SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCancelar, -144,
				SpringLayout.EAST, getContentPane());
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(12, 50, 117, 25);
		getContentPane().add(btnCancelar);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 59,
				SpringLayout.EAST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSave, 0,
				SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 0,
				SpringLayout.NORTH, lblSave);
		springLayout.putConstraint(SpringLayout.WEST, textField, 38,
				SpringLayout.EAST, lblSave);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, 19,
				SpringLayout.NORTH, lblSave);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnLoad = new JButton("Load");
		springLayout.putConstraint(SpringLayout.NORTH, btnLoad, 16,
				SpringLayout.SOUTH, btnSave);
		springLayout.putConstraint(SpringLayout.WEST, btnLoad, 0,
				SpringLayout.WEST, btnSave);
		springLayout.putConstraint(SpringLayout.SOUTH, btnLoad, 0,
				SpringLayout.SOUTH, lblLoad);
		springLayout.putConstraint(SpringLayout.EAST, btnLoad, 0,
				SpringLayout.EAST, btnSave);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnLoad);

		getListOfSaves();

		if (saves.size() != 0) {
			SpinnerListModel meses = new SpinnerListModel(saves);
			spinner = new JSpinner(meses);
		} else
			spinner = new JSpinner();
		springLayout.putConstraint(SpringLayout.NORTH, spinner, 2,
				SpringLayout.NORTH, lblLoad);
		springLayout.putConstraint(SpringLayout.WEST, spinner, 0,
				SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.EAST, spinner, 0,
				SpringLayout.EAST, textField);
		getContentPane().add(spinner);
	}

	public void saveStateToFile() {
		if (textField.getText().isEmpty())
			JOptionPane.showMessageDialog(null,
					"You can't create a file with no name!", "Error",
					JOptionPane.ERROR_MESSAGE);
		else if (textField.getText().contains(".")) {
			JOptionPane.showMessageDialog(null,
					"You can't create a file with two formats!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		else {
			File savesFolder = new File(savedGamesFolder);
			if (!savesFolder.exists())
				savesFolder.mkdir();

			try {
				ObjectOutputStream os = new ObjectOutputStream(
						new FileOutputStream("Cenas.txt"));
				os.writeObject(gui.getEngine());
				os.close();
				JOptionPane.showMessageDialog(null, "Game successfully saved.");
				setVisible(false);
			} catch (IOException arg0) {
				JOptionPane.showMessageDialog(null,
						"Game was not successfully saved.");
			}

		}
	}

	public void loadStateFromFile() {
		String filename = (String) spinner.getValue();

		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(filename + ".dat"));
			gui.setEngine((GameEngine) is.readObject());
			is.close();
		} catch (IOException | ClassNotFoundException e) {
		}

	}

	public void getListOfSaves() {
		File folder = new File(savedGamesFolder);
		if (!folder.isDirectory())
			return;

		saves.clear();
		for (File file : folder.listFiles())
			saves.add(file.getName());
	}
}
