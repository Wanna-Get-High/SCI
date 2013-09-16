package Mvc.Views;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Mvc.Models.Agent;
import Mvc.Models.Sma;

public class View extends JFrame implements Observer {

	JPanel space;
	Sma Sma;
	int sizeFactor = 30;
	Agent[][] agents;

	public View(Sma sma) {
		this.Sma = sma;
		this.Sma.addObserver(this);
		space = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				agents = Sma.environment.getAgents();
				for(int x=0; x<Sma.environment.getSize(); x++) {
					for(int y=0; y<Sma.environment.getSize(); y++) {
						if(agents[x][y]!=null) {
							g.fillOval(y*sizeFactor, x*sizeFactor, sizeFactor, sizeFactor);
						}
					}
				}
			}
		};
		/*this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
								
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});*/
		this.setLayout(new BorderLayout(1,1));
		this.getContentPane().add(space,BorderLayout.CENTER);
		this.pack();
		this.setSize(Sma.environment.getSize()*sizeFactor +getWidth()-getContentPane ().getWidth(), 
				Sma.environment.getSize()*sizeFactor + getHeight()-getContentPane().getHeight());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void update(Observable o, Object arg) {
		this.repaint();
	}

}