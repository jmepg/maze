package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartQuitButtons extends JPanel {
	private JButton StartButton;
	private JButton QuitButton;
	private Gui gui;

	public StartQuitButtons(Gui gm) {
		setLayout(new BorderLayout(0, 0));
		createButtons();
		this.gui = gm;
	}

	public void createButtons() {
		StartButton = new JButton("Start game");
		StartButton.setPreferredSize(new Dimension(Gui.hSize / 2, 25));
		add(StartButton, BorderLayout.WEST);
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res;
				if (gui.getPanel().getCm().getCustomBoard().board == null) {
					res = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to start a new game?",
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
						gui.startGame(false);
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
						gui.startGame(true);
					} else if (res == JOptionPane.NO_OPTION)
						gui.startGame(false);
				}
				gui.getPanel().requestFocus();
			}
		});

		QuitButton = new JButton("Quit");
		QuitButton.setPreferredSize(new Dimension(Gui.hSize / 2, 25));
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
