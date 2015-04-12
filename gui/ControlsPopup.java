package gui;

import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JLabel;

import java.awt.event.KeyAdapter;

@SuppressWarnings("serial")
public class ControlsPopup extends JDialog {
	private int nControl;
	private Gui gui;

	public ControlsPopup(Options jdialog, int nControl, Gui gui) {
		super(jdialog, "Set key");
		this.gui = gui;
		this.nControl = nControl;
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				setKey(arg0);
				dispose();
			}
		});
		
		setModal(true);
		setBounds(540, 425, 250, 50);
		getContentPane().setLayout(new BorderLayout(0,0));
		
		setResizable(false);
		
		JLabel lblPrimaUmaTecla = new JLabel(" Hit a key for assignment...");
		getContentPane().add(lblPrimaUmaTecla, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	public void setKey(KeyEvent arg0){
		gui.setControl(nControl, arg0.getKeyCode());
	}

}
