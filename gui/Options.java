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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public Options(JFrame frame, String title) {
		super(frame, title);
		initialize();
	}

	public void initialize() {
		setModal(true);
		setBounds(400, 300, 479, 329);
		setOptions();
		// setContentPane(tamanho);
		setResizable(false);
		setVisible(true);
	}

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

		JLabel lblTamanhoDoLabirinto = new JLabel("Tamanho do Labirinto");
		lblTamanhoDoLabirinto.setBounds(12, 12, 173, 15);
		getContentPane().add(lblTamanhoDoLabirinto);

		JLabel lblNmeroDeDrages = new JLabel("Número de dragões");
		lblNmeroDeDrages.setBounds(12, 87, 163, 15);
		getContentPane().add(lblNmeroDeDrages);

		JLabel lblModoDosDrages = new JLabel("Modo dos dragões");
		lblModoDosDrages.setBounds(12, 172, 143, 15);
		getContentPane().add(lblModoDosDrages);

		JLabel lblControlos = new JLabel("Controlos");
		lblControlos.setBounds(12, 204, 143, 15);
		getContentPane().add(lblControlos);

		final JSlider nDragoes = new JSlider(SwingConstants.HORIZONTAL, 0, 50,
				5);
		nDragoes.setPaintTicks(true);
		nDragoes.setPaintLabels(true);
		nDragoes.setMinorTickSpacing(2);
		nDragoes.setMajorTickSpacing(4);
		nDragoes.setBounds(12, 114, 455, 42);
		getContentPane().add(nDragoes);

		String[] comportamentos = { "Dragões parados", "Dragões em movimento",
				"Dragões adormecidos" };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JComboBox comportamentoDragoes = new JComboBox(comportamentos);
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

		JLabel lblCima = new JLabel("Cima");
		lblCima.setBounds(28, 230, 70, 15);
		getContentPane().add(lblCima);

		JLabel lblBaixo = new JLabel("Baixo");
		lblBaixo.setBounds(122, 230, 70, 15);
		getContentPane().add(lblBaixo);

		JLabel lblEsquerda = new JLabel("Esquerda");
		lblEsquerda.setBounds(221, 230, 70, 15);
		getContentPane().add(lblEsquerda);

		JLabel lblDireita = new JLabel("Direita");
		lblDireita.setBounds(353, 230, 70, 15);
		getContentPane().add(lblDireita);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(nDragoes.getValue());
				System.out.println(tamanho.getValue());
				System.out.println(comportamentoDragoes.getSelectedIndex()+1);
				dispose();
			}
		});
		btnOk.setBounds(76, 269, 117, 25);
		getContentPane().add(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(275, 269, 117, 25);
		getContentPane().add(btnCancelar);

	}
}
