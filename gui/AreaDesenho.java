package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class AreaDesenho extends JPanel{

	public AreaDesenho() {
		super();
	}

	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, 100, 100);
	}
	

}
