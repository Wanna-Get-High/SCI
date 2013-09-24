package view;

import java.awt.Graphics;
import javax.swing.JPanel;

import model.Agent;
import model.Ball;

/**
 * This class is the panel that draw the balls inside the environment.
 * It only needs the position of the Agent inside the space of the environment to draw them on a panel.
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class DrawingPanel extends JPanel {

	/** */
	private static final long serialVersionUID = -4236374735008339909L;
	
	/** The The space in which the Agents will move and do actions. */
	private Agent[][] space;
	
	public DrawingPanel(Agent[][] space) {
		this.space = space;
	}

	/**
	 * Paint the balls depending on their position in the space
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int size = space.length;

		float widthFactor = this.getWidth()/size;
		float heightFactor = this.getHeight()/size;
		
		for(int x=0; x< size; x++) {
			for(int y=0; y < size; y++) {
				
				if(space[x][y]!=null) {
					g.setColor((space[x][y]).color());
					g.fillOval((int)(x*widthFactor), (int)(y*heightFactor), (int)widthFactor, (int)heightFactor);
				}
			}
		}
		
	}
	
}
