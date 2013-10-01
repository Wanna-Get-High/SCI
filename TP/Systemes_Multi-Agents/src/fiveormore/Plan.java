package fiveormore;

import java.awt.Color;
import java.util.Random;

import core.Environment;

/**
 * The Environment for the 5 or more game.<br>
 * <br>
 * Contains the different colors of the token<br>
 * and a table that tells whether a token has to be removed or not.
 * 
 * @author Francois Lepan, Alexis Linke
 *
 */
public class Plan extends Environment {

	/** The different colors of this game */
	private Color[] colors = { Color.BLUE, Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE };
	
	/** The table that contains true if an agent at the same place has to be removed. */
	private boolean[][] agentsToBeRemoved;  
	
	
	public Plan(int size) {
		super(size);
		
		this.agentsToBeRemoved = new boolean[size][size];
		
	}
	
	/**
	 * Get the table of agents to be removed
	 * 
	 * @return
	 */
	public boolean[][] getAgentsToBeRemoved() { return this.agentsToBeRemoved; }

	
	/**
	 * Reset the agents to be removed -> put false in every cell.
	 */
	public void resetAgentsToBeRemoved() { this.agentsToBeRemoved = new boolean[this.size][this.size];}
	
	
	/**
	 * Get a random color from the available colors.
	 *  
	 * @return a random Color
	 */
	public Color getRandomColor() { return this.colors[new Random().nextInt(colors.length)]; }
}
