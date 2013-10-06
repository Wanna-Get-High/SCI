package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import core.Agent;

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
	protected Agent[][] space;
	
	/** A boolean that tells if the view has to draw lines between each cell or not */
	private boolean drawLines;
	
	/** A boolean that tells if the view has to draw the axis or not */
	private boolean drawAxis;
	
	/**
	 * The basic constructor.
	 * 
	 * @param space the space where the agents move
	 * @param drawLines true if you want lines to be drawn between each cell of the space
	 * @param drawAxis true if you want to show x and y axis.
	 */
	public DrawingPanel(Agent[][] space, boolean drawLines, boolean drawAxis) {
		this.space = space;
		this.drawLines = drawLines;
		this.drawAxis = drawAxis;
	}

	/**
	 * Paint the balls depending on their position in the space
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int step = 0;
		
		if (this.drawAxis) step = 1;
		
		int size = space.length+step;

		float widthFactor = this.getWidth()/size;
		float heightFactor = this.getHeight()/size;
		
		
		if (this.drawAxis) {
			g.setColor(Color.black);
			g.drawLine(0, 0, (int)(widthFactor) , (int)(heightFactor));
			g.drawString("X", (int)(widthFactor/2-widthFactor/4), (int)(heightFactor/2+heightFactor/4));
			g.drawString("Y", (int)(widthFactor/2+widthFactor/6), (int)(heightFactor/2));
			
			for (int i = 1; i < size; i++) {
				g.drawString(i-1+"",(int)(i*widthFactor+widthFactor/2), (int)(heightFactor/2) );
				g.drawString(i-1+"",(int)(widthFactor/2), (int)(i*heightFactor+heightFactor/2) );
			}
		}
		
		
		if (this.drawLines) {
			g.setColor(Color.black);
			
			// horizontal Lines
			for (int i = 1; i < size; i++ ) {
				g.drawLine(0, (int)(i*heightFactor), this.getWidth(), (int)(i*heightFactor));
			}
			
			// vertical lines
			for (int i = 1; i < size; i++) {
				g.drawLine((int)(i*widthFactor), 0, (int)(i*widthFactor), this.getHeight());
			}
		}
		
		for(int x=step; x< size; x++) {
			for(int y=step; y < size; y++) {
				if(space[x-step][y-step]!=null) {
					g.setColor(space[x-step][y-step].color());
					g.fillOval((int)(y*widthFactor), (int)(x*heightFactor), (int)widthFactor, (int)heightFactor);
					
				}
			}
		}
		
	}
	
}
