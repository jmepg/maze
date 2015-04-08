package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OptionButtons extends JPanel {
	private JButton options;
	private JButton saveload;
	private JButton create;
	private Gui gui;
	private JFrame frame;
	private Options optDialog;
	private SaveLoad saveLoadDialog;

	public OptionButtons(JFrame frame, Gui gui) {
		setLayout(new BorderLayout(0, 0));
		this.gui = gui;
		createButtons();
	}
	
	

	public JButton getOptions() {
		return options;
	}



	public JButton getSaveload() {
		return saveload;
	}



	public JButton getCreate() {
		return create;
	}



	public Gui getGui() {
		return gui;
	}



	public JFrame getFrame() {
		return frame;
	}



	public Options getOptDialog() {
		return optDialog;
	}



	public void createButtons() {
		options = new JButton("Options");
		options.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(options, BorderLayout.WEST);
		optDialog = new Options(frame, "Opções de jogo",gui);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				optDialog.setVisible(true);
				gui.getPanel().requestFocus();
			}
			
		});

		saveload = new JButton("Save/Load Game");
		saveload.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(saveload, BorderLayout.CENTER);
		saveLoadDialog = new SaveLoad(frame, "Save/Load Game", gui);
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
				JOptionPane.showMessageDialog(null, "To be implemented later!",
						"Oops!", JOptionPane.ERROR_MESSAGE);
			}
		});

	}
}
