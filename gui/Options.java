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

import logic.Dragon;
import logic.Dragon.Mode;

/**
 * Manages the options dialog.
 */
public class Options extends JDialog {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The slider that asks for the maze's size input.
	 */
	private JSlider tamanho;
	
	/**
	 * The slider that asks for the number of dragons.
	 */
	private JSlider nDragoes;
	
	
	/**
	 * The label for the labyrinth's size.
	 */
	private JLabel lblTamanhoDoLabirinto;
	
	/**
	 * The label for the number of dragons.
	 */
	private JLabel lblNmeroDeDrages;
	
	/**
	 * The label for the dragon's behaviour.
	 */
	private JLabel lblModoDosDrages;
	
	/**
	 * The label for the controls.
	 */
	private JLabel lblControlos;
	
	/**
	 * The confirm button.
	 */
	private JButton btnOk;
	
	/**
	 * The cancel button.
	 */
	private JButton btnCancelar;
	
	/**
	 * The up control button.
	 */
	private JButton btnCima;
	
	/**
	 * The down control button.
	 */
	private JButton btnBaixo;
	
	/**
	 * The left control button.
	 */
	private JButton btnEsquerda;
	
	/**
	 * The right control button.
	 */
	private JButton btnDireita;
	
	/**
	 * {@link gui.Gui}
	 */
	private Gui gui;
	
	/**
	 * The variable that keeps the labyrinth size for the other classes to use.
	 * <p> Updated only when the user presses ok.
	 */
	private int tamanhoLabirinto;
	
	/**
	 * The variable that keeps the number of dragons for the other classes to use.
	 * <p> Updated only when the user presses ok.
	 */
	private int numeroDragoes;
	
	/**
	 * The variable that keeps the dragon behaviour for the other classes to use.
	 * <p> Updated only when the user presses ok.
	 */
	private Mode modoDragoes;
	
	/**
	 * The Combo box that lets the user choose which behaviour he wants for the dragons.
	 */
	private JComboBox<?> comportamentoDragoes;
	
	
	/**
	 * The class constructors
	 * @param frame The owner of this dialog.
	 * @param title The title to display in the top of the dialog.
	 * @param gui {@link gui.Gui}
	 */
	public Options(JFrame frame, String title, Gui gui) {
		super(frame, title);
		this.gui = gui;
		initialize();
	}

	/**
	 * Get @see #tamanhoLabirinto
	 * 
	 * @return {@link #tamanhoLabirinto}
	 */
	public int getTamanhoLabirinto() {
		return tamanhoLabirinto;
	}

	/**
	 * Get @see #numeroDragoes
	 * 
	 * @return {@link #numeroDragoes}
	 */
	
	public int getNumeroDragoes() {
		return numeroDragoes;
	}

	/**
	 * Get @see #modoDragoes
	 * 
	 * @return {@link #modoDragoes}
	 */
	public Mode getModoDragoes() {
		return modoDragoes;
	}
	
	/**
	 * Get @see #tamanho
	 * 
	 * @return {@link #tamanho}
	 */
	public JSlider getTamanho() {
		return tamanho;
	}

	/**
	 * Get @see #nDragoes
	 * 
	 * @return {@link #nDragoes}
	 */
	public JSlider getnDragoes() {
		return nDragoes;
	}


	/**
	 * Initializes the variables with the information for the other classes and some configurations.
	 * @see #tamanhoLabirinto
	 * @see #numeroDragoes
	 * @see #modoDragoes
	 */
	public void initialize() {
		tamanhoLabirinto = 15;
		numeroDragoes = 5;
		modoDragoes = convertSelectionToMode(1);
		
		setModal(true);
		setBounds(400, 300, 479, 329);
		setOptions();

		setResizable(false);
		setVisible(false);
	}
	
	/**
	 * Converts the Combo Box selection to the corresponding dragon behaviour (mode).
	 * @param mode The index of the selected JComboBox option.
	 * @return The desired mode.
	 */
	public Mode convertSelectionToMode(int mode){
		switch (mode) {
		case 1:
			return Dragon.Mode.STATIC;
		case 2:
			return Dragon.Mode.MOVABLE;
		case 3:
			return Dragon.Mode.SLEEPING;
		default:
			break;
		}
		return null;
	}

	/**
	 * Initializes all the buttons, labels, sliders and the combo box.
	 */
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

		lblTamanhoDoLabirinto = new JLabel("Maze Size");
		lblTamanhoDoLabirinto.setBounds(12, 12, 173, 15);
		getContentPane().add(lblTamanhoDoLabirinto);

		lblNmeroDeDrages = new JLabel("Number of Dragons");
		lblNmeroDeDrages.setBounds(12, 87, 163, 15);
		getContentPane().add(lblNmeroDeDrages);

		lblModoDosDrages = new JLabel("Dragon Behaviour");
		lblModoDosDrages.setBounds(12, 172, 143, 15);
		getContentPane().add(lblModoDosDrages);

		lblControlos = new JLabel("Controls");
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

		String[] comportamentos = { "Static Dragons", "Moving Dragons",
				"Sleepy Dragons" };
		
		comportamentoDragoes = new JComboBox<>(comportamentos);
		comportamentoDragoes.setMaximumRowCount(3);
		comportamentoDragoes.setBounds(173, 172, 200, 20);
		getContentPane().add(comportamentoDragoes);

		btnCancelar = new JButton("Cancel");
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
				modoDragoes = convertSelectionToMode(comportamentoDragoes.getSelectedIndex()+1);
				dispose();
			}
		});
		
		btnOk.setBounds(275, 269, 117, 25);
		getContentPane().add(btnOk);

		
		btnCima = new JButton("Up");
		
		btnCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,0,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnCima.setBounds(17, 228, 100, 25);
		getContentPane().add(btnCima);
		
		btnBaixo = new JButton("Down");
		btnBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,1,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnBaixo.setBounds(132, 228, 100, 25);
		getContentPane().add(btnBaixo);
		
		btnEsquerda = new JButton("Left");
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ControlsPopup(null,2,gui);
				gui.getPanel().requestFocus();
			}
		});
		btnEsquerda.setBounds(247, 228, 105, 25);
		getContentPane().add(btnEsquerda);
		
		btnDireita = new JButton("Right");
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
