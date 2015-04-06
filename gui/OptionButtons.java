package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class OptionButtons extends JPanel {
	private JButton options;
	private JButton saveload;
	private JButton create;
	private Gui gui;
	
	public OptionButtons(Gui gui){
		setLayout(new BorderLayout(0, 0));
		createButtons();
		this.gui = gui;
	}
	
	public void createButtons() {
		options = new JButton("Options");
		options.setPreferredSize(new Dimension(Gui.hSize/3, 25));
		add(options, BorderLayout.WEST);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "To be implemented later!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		saveload = new JButton("Save/Load Game");
		saveload.setPreferredSize(new Dimension(Gui.hSize/3, 25));
		add(saveload, BorderLayout.CENTER);
		saveload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "To be implemented later!", "Oops!", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		
		create = new JButton("Create Maze");
		create.setPreferredSize(new Dimension(Gui.hSize/3, 25));
		add(create, BorderLayout.EAST);
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "To be implemented later!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}
}
