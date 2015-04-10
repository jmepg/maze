package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class CreationMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnCancelar;
	private JButton btnOk;
	private JComboBox<?> jcbEntidades;
	
	public CreationMenu(){
		setLayout(new BorderLayout(0, 0));
		setSize(Gui.hSize, 25);
		createButtons();
	}
	
	public void createButtons(){
		String[] entidades = {"Wall", "Floor", "Exit", "Hero", "Dragon", "Dart", "Shield"};
		jcbEntidades = new JComboBox<>(entidades);
		jcbEntidades.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		add(jcbEntidades, BorderLayout.WEST);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(btnCancelar, BorderLayout.CENTER);
		
		btnOk = new JButton("OK");
		btnOk.setPreferredSize(new Dimension(Gui.hSize / 3, 25));
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(btnOk, BorderLayout.EAST);
		
		
	}

}
