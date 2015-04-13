package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Manages the panel on top of the game screen.
 */
public class TopOptions extends JPanel {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The start game button.
	 */
	private JButton StartButton;
	
	/**
	 * The quit game button.
	 */
	private JButton QuitButton;
	
	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;

	/**
	 * The class constructor.
	 * @param gui {@link gui.Gui}
	 */
	public TopOptions(Gui gui) {
		this.gui = gui;
		setLayout(new GridLayout(1, 3));
		createButtons();
	}

	/**
	 * Creates the two buttons and adds them a actionPerformed listener.
	 */
	public void createButtons() {
		StartButton = new JButton("Start game");
		//StartButton.setSize(new Dimension(gui.getFrame().getWidth() / 2, 25));
		add(StartButton, BorderLayout.WEST);
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res;
				if (gui.getPanel().getCm().getCustomBoard().getBoard() == null) {
					res = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to start a new game?",
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						gui.startGame(null);
					}
				} else {
					if (gui.getPanel().isInCreationMode()) {
						JOptionPane
								.showMessageDialog(
										null,
										"Please finish or discard the maze you are creating before starting a new game",
										"Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					res = JOptionPane
							.showConfirmDialog(
									null,
									"A user-created labirinth has been detected. Do you want to use it? (Note: You will be able to use it even if you don't choose it)",
									"Choose the labirinth",
									JOptionPane.YES_NO_CANCEL_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						gui.startGame(gui.getPanel().getCm().getCustomBoard());
					} else if (res == JOptionPane.NO_OPTION)
						gui.startGame(null);
				}
				gui.getPanel().requestFocus();
			}
		});

		QuitButton = new JButton("Quit");
		//QuitButton.setSize(new Dimension(gui.getFrame().getWidth() / 2, 25));
		add(QuitButton, BorderLayout.EAST);
		QuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to quit the game?",
						"Confirmation", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				gui.getPanel().requestFocus();
			}
		});

	}
}
