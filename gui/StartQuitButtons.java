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
		//this.addMouseListener(this);
		setLayout(new BorderLayout(0, 0));
		createButtons();
		this.gui = gm;
	}

	public void createButtons() {
		StartButton = new JButton("Start game");
		StartButton.setPreferredSize(new Dimension(Gui.hSize/2, 25));
		add(StartButton, BorderLayout.WEST);
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to start a new game?");
				if (res == JOptionPane.YES_OPTION) {
					gui.startGame();
				}
				gui.getPanel().requestFocus();
			}
		});

		QuitButton = new JButton("Quit");
		QuitButton.setPreferredSize(new Dimension(Gui.hSize/2, 25));
		add(QuitButton, BorderLayout.EAST);
		QuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to quit the game?");
				if (res == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				gui.getPanel().requestFocus();
			}
		});

	}
}
