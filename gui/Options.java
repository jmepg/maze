package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Options extends JDialog {

	private JSlider tamanho;
	private JSlider nDragoes;
	
	private JLabel lblTamanhoDoLabirinto;
	private JLabel lblNmeroDeDrages;
	private JLabel lblModoDosDrages;
	private JLabel lblControlos;
	
	private JButton btnOk;
	private JButton btnCancelar;
	private JButton btnCima;
	private JButton btnBaixo;
	private JButton btnEsquerda;
	private JButton btnDireita;
	
	private Gui gui;
	
	private int tamanhoLabirinto;
	private int numeroDragoes;
	private int modoDragoes;
	
	private JComboBox<?> comportamentoDragoes;
	
	

	public Options(JFrame frame, String title, Gui gui) {
		super(frame, title);
		this.gui = gui;
		initialize();
	}

	public int getTamanhoLabirinto() {
		return tamanhoLabirinto;
	}

	public int getNumeroDragoes() {
		return numeroDragoes;
	}

	public int getModoDragoes() {
		return modoDragoes;
	}
	
	public JSlider getTamanho() {
		return tamanho;
	}

	public JSlider getnDragoes() {
		return nDragoes;
	}

	public JLabel getLblTamanhoDoLabirinto() {
		return lblTamanhoDoLabirinto;
	}

	public JLabel getLblNmeroDeDrages() {
		return lblNmeroDeDrages;
	}

	public JLabel getLblModoDosDrages() {
		return lblModoDosDrages;
	}

	public JLabel getLblControlos() {
		return lblControlos;
	}

	public JButton getBtnOk() {
		return btnOk;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	
	public JComboBox<?> getComportamentoDragoes() {
		return comportamentoDragoes;
	}

	public void initialize() {
		tamanhoLabirinto = 15;
		numeroDragoes = 5;
		modoDragoes = 2;
		
		setModal(true);
		setBounds(400, 300, 479, 329);
		setOptions();

		setResizable(false);
		setVisible(false);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setOptions() {
		getContentPane().setLayout(null);
		
		tamanho = new JSlider(JSlider.HORIZONTAL, 7, 55, 15);
		tamanho.setBounds(12, 33, 455, 42);
		tamanho.setMinorTickSpacing(2);
		tamanho.setMajorTickSpacing(4);
		tamanho.setPaintTicks(true);
		tamanho.setPaintLabels(true);
		tamanho.setSnapToTicks(true);
		tamanho.setLabelTable(tamanho.createStandardLabels(4));
		getContentPane().add(tamanho);

		lblTamanhoDoLabirinto = new JLabel("Tamanho do Labirinto");
		lblTamanhoDoLabirinto.setBounds(12, 12, 173, 15);
		getContentPane().add(lblTamanhoDoLabirinto);

		lblNmeroDeDrages = new JLabel("Número de dragões");
		lblNmeroDeDrages.setBounds(12, 87, 163, 15);
		getContentPane().add(lblNmeroDeDrages);

		lblModoDosDrages = new JLabel("Modo dos dragões");
		lblModoDosDrages.setBounds(12, 172, 143, 15);
		getContentPane().add(lblModoDosDrages);

		lblControlos = new JLabel("Controlos");
		lblControlos.setBounds(12, 204, 143, 15);
		getContentPane().add(lblControlos);

		nDragoes = new JSlider(SwingConstants.HORIZONTAL, 0, 50,
				5);
		nDragoes.setPaintTicks(true);
		nDragoes.setPaintLabels(true);
		nDragoes.setMinorTickSpacing(2);
		nDragoes.setMajorTickSpacing(4);
		nDragoes.setBounds(12, 114, 455, 42);
		getContentPane().add(nDragoes);

		String[] comportamentos = { "Dragões parados", "Dragões em movimento",
				"Dragões adormecidos" };
		
		comportamentoDragoes = new JComboBox(comportamentos);
		comportamentoDragoes.setMaximumRowCount(3);
		comportamentoDragoes.setBounds(173, 172, 200, 20);
		getContentPane().add(comportamentoDragoes);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(76, 269, 117, 25);
		getContentPane().add(btnCancelar);
		
		

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroDragoes = nDragoes.getValue();
				tamanhoLabirinto = tamanho.getValue();
				modoDragoes = comportamentoDragoes.getSelectedIndex()+1;
				dispose();
			}
		});
		
		btnOk.setBounds(275, 269, 117, 25);
		getContentPane().add(btnOk);

		
		btnCima = new JButton("Cima");
		
		btnCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,0,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnCima.setBounds(17, 228, 100, 25);
		getContentPane().add(btnCima);
		
		btnBaixo = new JButton("Baixo");
		btnBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,1,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnBaixo.setBounds(132, 228, 100, 25);
		getContentPane().add(btnBaixo);
		
		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,2,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnEsquerda.setBounds(247, 228, 105, 25);
		getContentPane().add(btnEsquerda);
		
		btnDireita = new JButton("Direita");
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,3,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnDireita.setBounds(367, 228, 100, 25);
		getContentPane().add(btnDireita);
		
	}
}
