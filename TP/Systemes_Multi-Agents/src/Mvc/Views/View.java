package Mvc.Views;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JWindow;

import Mvc.Models.Agent;
import Mvc.Models.Sma;

public class View extends JPanel implements Observer {

	JWindow window;
	Sma sma;
	int size;
	Agent[][] space;

	public View(Sma sma) {
		this.sma = sma;
		this.size = sma.environment.getSize()*50;
		sma.addObserver(this);
		this.space = sma.environment.getAgents();
		window = new JWindow();
		window.add(this);
		window.setSize(size, size);
		window.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i<size; i=i+50) {
			g.drawLine(0, i, size, i);
			g.drawLine(i, 0, i, size);
		}
		for(int x=0; x<size/50; x++) {
			for(int y=0; y<size/50; y++) {
				if(space[x][y]!=null) {
					g.fillOval(y*50, x*50, 50, 50);
				}
			}
		}
	}
	
	public void update(Observable o, Object arg) {
		this.repaint();
	}

}