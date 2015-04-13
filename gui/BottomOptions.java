package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Manages the panel below the game screen.
 *
 */
public class BottomOptions extends JPanel {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The options button.
	 */
	private JButton options;

	/**
	 * The save/load game button.
	 */
	private JButton saveload;

	/**
	 * The create maze button.
	 */
	private JButton create;

	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;

	/**
	 * The options dialog, for when the user clicks the options button.
	 * @see #options
	 */
	private Options optDialog;
	
	/**
	 * The save/load dialog, for when the user clicks the save/load game button.
	 * @see #saveload
	 */
	private SaveLoad saveLoadDialog;

	/**
	 * The class constructor.
	 * @param gui {@link gui.Gui}
	 */
	public BottomOptions(Gui gui) {
		setLayout(new GridLayout(1, 2));
		this.gui = gui;
		createButtons();
	}

	/**
	 * Get @see #options
	 * 
	 * @return {@link #options}
	 */
	public JButton getOptions() {
		return options;
	}

	/**
	 * Get @see #saveload
	 * 
	 * @return {@link #saveload}
	 */
	public JButton getSaveload() {
		return saveload;
	}

	/**
	 * Get @see #create
	 * 
	 * @return {@link #create}
	 */
	public JButton getCreate() {
		return create;
	}

	/**
	 * Get @see #gui
	 * 
	 * @return {@link #gui}
	 */
	public Gui getGui() {
		return gui;
	}

	/**
	 * Get @see #optDialog
	 * 
	 * @return {@link #optDialog}
	 */
	public Options getOptDialog() {
		return optDialog;
	}

	/**
	 * Creates all the buttons in this class.
	 * @see options
	 * @see saveload
	 * @see create
	 */
	
	public void createButtons() {
		options = new JButton("Options");
		options.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(options, BorderLayout.WEST);
		optDialog = new Options(null, "Game Options", gui);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				optDialog.setVisible(true);
				gui.getPanel().requestFocus();
			}

		});

		saveload = new JButton("Save/Load Game");
		saveload.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(saveload, BorderLayout.CENTER);
		saveLoadDialog = new SaveLoad(gui);
		saveload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveLoadDialog.setVisible(true);
				gui.getPanel().requestFocus();
			}

		});

		create = new JButton("Create Maze");
		create.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(create, BorderLayout.EAST);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.createGame();
			}
		});

	}
}
