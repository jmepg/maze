package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StartQuitButtons extends JPanel implements MouseListener {
	private JButton StartButton;
	private JButton QuitButton;
	private Gui gui;

	public StartQuitButtons(Gui gm) {
		this.addMouseListener(this);
		setLayout(new BorderLayout(0, 0));
		setSize(100, 700);
		createButtons();
		this.gui = gm;
	}

	public void createButtons() {
		StartButton = new JButton("Start game");
		StartButton.setPreferredSize(new Dimension(200, 25));
		add(StartButton, BorderLayout.WEST);
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to start a new game?");
				if (res == JOptionPane.YES_OPTION) {
					gui.startGame();
				}
			}
		});

		QuitButton = new JButton("Quit");
		QuitButton.setPreferredSize(new Dimension(200, 25));
		add(QuitButton, BorderLayout.EAST);
		QuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int res = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to quit the game?");
				if (res == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
