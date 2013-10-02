package fiveormore;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import core.Agent;
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
	private Color[] colors = { Color.BLUE, Color.PINK, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW, Color.BLACK };
	
	/** The table that contains true if an agent at the same place has to be removed. */
	private boolean[][] agentsToBeRemoved;  
	
	public Plan(int size) {
		super(size);
		this.agentsToBeRemoved = new boolean[size][size];
	}
	
	/**
	 * We remove the agents from the environment and the space 
	 * depending on the table agentsToBeRemoved.
	 */
	public void removeAgentsFrom(ArrayList<Agent> agents) {

		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				
				// if the value at this point is true then 
				if (this.agentsToBeRemoved[i][j]) {
					
					// remove from the table
					this.removeAgent(this.space[i][j]);
					
					// remove from the list
					agents.remove(this.space[i][j]);
				}
			}
		}
		
		// reset the values
		this.resetAgentsToBeRemoved();	
	}

	/**
	 * Add some token at random place in the environment
	 * 
	 * @param nbToken the number of token to be added
	 */
	public void addToken(int nbToken, ArrayList<Agent> agents) {
		for (int i = 0; i < nbToken; i++) {
			// we create a token that isn't placed in the environement 
			Token token = new Token(this, this.getRandomColor(), false);
			
			// we try to get a place for this token
			// if doesn't get one we don't add it to the agents list 
			if (this.getPlace(token)) agents.add(token);
		}
		
	}
	
	/**
	 * Get a random color from the available colors.
	 *  
	 * @return a random Color
	 */
	public Color getRandomColor() { return this.colors[new Random().nextInt(colors.length)]; }

	/**
	 * Put true at the place where an agent has to be removed
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
	public void setAgentToBeRemoved(int x, int y) { this.agentsToBeRemoved[x][y] = true; }

	
	public void resetAgentsToBeRemoved() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.agentsToBeRemoved[i][j] = false;
			}
		}
	}
}
