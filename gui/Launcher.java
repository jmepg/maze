package gui;

import java.awt.EventQueue;

/**
 * Contains the main. The project starts here!
 */
public class Launcher {
	
	/**
	 * Default constructor
	 */
	
	public Launcher(){}
	/**
	 * The main!
	 * @param args arguments of the main.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
