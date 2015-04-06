package gui;

import java.awt.BorderLayout;
import java.awt.RenderingHints;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Options extends JDialog {

	private JSlider tamanho;
	private JSlider nDragoes;
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	private JLabel lblCima;
	private JLabel lblBaixo;
	private JLabel lblEsquerda;
	private JLabel lblDireita;
	private JLabel lblTamanhoDoLabirinto;
	private JLabel lblNmeroDeDrages;
	private JLabel lblModoDosDrages;
	private JLabel lblControlos;
	
	private JButton btnOk;
	private JButton btnCancelar;
	
	private int tamanhoLabirinto;
	private int numeroDragoes;
	private int modoDragoes;
	
	@SuppressWarnings("rawtypes")
	private JComboBox comportamentoDragoes;
	
	

	public Options(JFrame frame, String title) {
		super(frame, title);
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

	public JTextField getTextField() {
		return textField;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public JTextField getTextField_2() {
		return textField_2;
	}

	public JTextField getTextField_3() {
		return textField_3;
	}

	public JLabel getLblCima() {
		return lblCima;
	}

	public JLabel getLblBaixo() {
		return lblBaixo;
	}

	public JLabel getLblEsquerda() {
		return lblEsquerda;
	}

	public JLabel getLblDireita() {
		return lblDireita;
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

	public JComboBox getComportamentoDragoes() {
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
		tamanho = new JSlider(JSlider.HORIZONTAL, 7, 55, 15);
		tamanho.setBounds(12, 33, 455, 42);
		tamanho.setMinorTickSpacing(2);
		tamanho.setMajorTickSpacing(4);
		tamanho.setPaintTicks(true);
		tamanho.setPaintLabels(true);
		tamanho.setLabelTable(tamanho.createStandardLabels(4));

		getContentPane().setLayout(null);
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

		textField = new JTextField();
		textField.setBounds(66, 228, 40, 20);
		getContentPane().add(textField);
		textField.setColumns(1);

		textField_1 = new JTextField();
		textField_1.setColumns(1);
		textField_1.setBounds(164, 228, 40, 20);
		getContentPane().add(textField_1);
		//textfield_1.setDocument(new JTextFieldLimit(10));

		textField_2 = new JTextField();
		textField_2.setColumns(1);
		textField_2.setBounds(293, 228, 40, 20);
		getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(1);
		textField_3.setBounds(405, 228, 40, 20);
		getContentPane().add(textField_3);

		lblCima = new JLabel("Cima");
		lblCima.setBounds(28, 230, 70, 15);
		getContentPane().add(lblCima);

		lblBaixo = new JLabel("Baixo");
		lblBaixo.setBounds(122, 230, 70, 15);
		getContentPane().add(lblBaixo);

		lblEsquerda = new JLabel("Esquerda");
		lblEsquerda.setBounds(221, 230, 70, 15);
		getContentPane().add(lblEsquerda);

		lblDireita = new JLabel("Direita");
		lblDireita.setBounds(353, 230, 70, 15);
		getContentPane().add(lblDireita);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroDragoes = nDragoes.getValue();
				tamanhoLabirinto = tamanho.getValue();
				modoDragoes = comportamentoDragoes.getSelectedIndex()+1;
				dispose();
			}
		});
		btnOk.setBounds(76, 269, 117, 25);
		getContentPane().add(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(275, 269, 117, 25);
		getContentPane().add(btnCancelar);

	}
}
