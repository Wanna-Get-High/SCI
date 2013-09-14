package Mvc.Models;


/**
 * This class stock the Environment.
 * 
 * The environment is an array of integer.
 * 0 if a cell of the array is empty and 1 if it contains an agent.  
 * 
 * @author Francois Lepan - Alexis Linke
 *
 */
public class Environment {
	
	/** */
	Agent[][] space;
	
	/** */
	int size;
	
	
	public Environment(int size) {
		
		this.size = size;		
		this.space = new Agent[size][size];
		
	}
	
	public void getPlace(Agent agent) {
		
		// get a random place until it is empty
		int xPlace = (int) (Math.random()*(this.size-1));
		int yPlace = (int) (Math.random()*(this.size-1));
		
		while(this.space[xPlace][yPlace] != null ) {
			xPlace = (int) (Math.random()*(this.size-1));
			yPlace = (int) (Math.random()*(this.size-1));
		}
		
		// set the place of the agent and fill the place with it
		agent.x(xPlace);
		agent.y(yPlace);
		
		this.space[xPlace][yPlace] = agent;
	}

	public int getSize() {
		return this.size;
	}
	
	public Agent getAgentAt(int x, int y) {
		return this.space[x][y];
	}
	
	public void setAgentAt(int x, int y, Agent agent) {
		this.space[x][y] = agent;
	}

	public Agent[][] getAgents() {
		return this.space;
	}
}
