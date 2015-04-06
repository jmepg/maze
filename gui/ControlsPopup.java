package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;

import java.awt.event.KeyAdapter;

@SuppressWarnings("serial")
public class ControlsPopup extends JDialog {
	private int nControl;
	private Gui gui;

	public ControlsPopup(Options jdialog, int nControl, Gui gui) {
		super(jdialog, "Definir uma tecla...");
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
		
		JLabel lblPrimaUmaTecla = new JLabel(" Prima uma tecla para atribuição...");
		getContentPane().add(lblPrimaUmaTecla, BorderLayout.CENTER);
		setVisible(true);
	}
	
	
	public void setKey(KeyEvent arg0){
		gui.setControl(nControl, arg0.getKeyCode());
	}

}
