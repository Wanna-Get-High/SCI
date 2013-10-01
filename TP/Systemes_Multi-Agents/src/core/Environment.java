package core;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This class stock the Environment.
 * 
 * The environment is an array of integer.
 * null if a cell of the array is empty otherwise the agent. 
 * 
 * @author Francois Lepan - Alexis Linke
 */
public class Environment {	
	/** 
	  * The space in which the Agents will move and do actions.<b> 
	  * It is a square depending on the variable size entered at the creation of this class.  
	  */
	protected Agent[][] space;
	
	/** The size of the space */
	protected int size;
	
	public Environment(int size) {
		this.size = size;		
		this.space = new Agent[size][size];

	}
	
	/**
	 * This method search for a random place inside the space <b> 
	 * and when it found one it saves the place for this agent.
	 * 
	 * @param agent the agent that will be put inside a cell.
	 */
	public boolean getPlace(Agent agent) {
		
		ArrayList<Integer> freeIndexes = new ArrayList<Integer>();
		
		// We add all of the index to an ArrayList 
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.space[i][j] == null) {
					freeIndexes.add(this.getValueFrom(i, j));
				}
			}
		}
		
		if (freeIndexes.isEmpty()) return false;
		
		// We shuffle the ArrayList and get an index
		Collections.shuffle(freeIndexes);
		int value = freeIndexes.get(0);
		
		int xPlace = this.getXfromValue(value);
		int yPlace = this.getYfromValue(value);
		
		agent.x(xPlace);
		agent.y(yPlace);
		
		this.space[xPlace][yPlace] = agent;
			
		return true;
	}
	
	/**
	 * Remove an agent from the space.
	 * 
	 * @param agent the agent to be removed
	 */
	public void removeAgent(Agent agent) {
		int x = agent.x();
		int y = agent.y();
		
		this.space[x][y] = null;
	}
	

	/**
	 * Move an agent from its previous place to the new one.
	 * 
	 * @param agent the agent and its old position to be moved
	 * @param newXPlace the new position on the x axis
	 * @param newYPlace the new position on the y axis
	 */
	public void moveAgent(Agent agent, int newXPlace, int newYPlace) {
		this.removeAgent(agent);
		agent.x(newXPlace);
		agent.y(newYPlace);
		this.space[newXPlace][newYPlace] = agent;
	}

	
	/**
	 * Get the size of the environment.
	 * 
	 * @return the size of the environment.
	 */
	public int getSize() { return this.size; }

	
	/**
	 * Get the agent at the specified position in the space.
	 * 
	 * @param x the x axis value
	 * @param y the y axis value
	 * @return if (x > this.size || y > this.size || x < 0 || y < 0) null else the Agent.
	 */
	public Agent getAgentAt(int x, int y) { return this.space[x][y]; }

	/**
	 * Gets the space containing the agents
	 * 
	 * @return the space.
	 */
	public Agent[][] getAgentsSpace() { return this.space; }
	
	
	/**
	 * Calculate the value that has to be added to the ArrayList remainingIndexes
	 * 
	 * @param x the x position on the Agent space 
	 * @param y the y position on the Agent space
	 * @return the value corresponding to the position of the agent
	 */
	private int getValueFrom(int x, int y) { return x*this.size + y; }
	
	/**
	 * Calculate the x value depending on the retrieved value of the ArrayList remainingIndexes
	 * 
	 * @param value retrieved from the ArrayList 
	 * 
	 * @return the x position on the Agent space
	 */
	private int getXfromValue(int value) { return value / this.size; }
	
	/**
	 * Calculate the y value depending on the retrieved value of the ArrayList remainingIndexes
	 * 
	 * @param value retrieved from the ArrayList
	 * @return the y position on the Agent space
	 */
	private int getYfromValue(int value) { return value % this.size; }
}
