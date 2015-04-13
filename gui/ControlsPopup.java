package gui;

import java.awt.event.KeyEvent;

import javax.swing.JDialog;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import symbolics.Direction;

import java.awt.event.KeyAdapter;

/**
 * Manages the dialog that asks for the key to assign to each user action.
 */
public class ControlsPopup extends JDialog {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Id of the key the button will manage the assignment
	 */
	private int nControl;
	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;
	
	/**
	 * The class constructor.
	 * @param jdialog The owner of this object.
	 * @param nControl {@link #nControl}
	 * @param gui {@link #gui}
	 */
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
		setBounds(540, 425, 250, 75);
		getContentPane().setLayout(new BorderLayout(0,0));
		
		setResizable(false);
		
		JLabel lblPrimaUmaTecla = new JLabel("Hit a key for assignment... ");
		JLabel defaultKey;
		
		if(nControl == Direction.UP_INT)
			defaultKey = new JLabel("Default: UP_ARROW");
		else if(nControl == Direction.DOWN_INT)
			defaultKey = new JLabel("Default: DOWN_ARROW");
		else if(nControl == Direction.LEFT_INT)
			defaultKey = new JLabel("Default: LEFT_ARROW");
		else //Direction.RIGHT_INT
			defaultKey = new JLabel("Default: RIGHT_ARROW");

		getContentPane().add(lblPrimaUmaTecla, BorderLayout.CENTER);
		getContentPane().add(defaultKey, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	/**
	 * Assigns the key given by the user.
	 * @param arg0 the KeyEvent.
	 */
	public void setKey(KeyEvent arg0){
		gui.setControl(nControl, arg0.getKeyCode());
	}

}
