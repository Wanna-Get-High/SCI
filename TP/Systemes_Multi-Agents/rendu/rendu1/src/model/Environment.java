package model;

/**
 * This class stock the Environment.
 * 
 * The environment is an array of integer.
 * 0 if a cell of the array is empty otherwise the id of an agent.  
 * 
 * @author Francois Lepan - Alexis Linke
 */
public class Environment {
	
	/** 
	  * The space in which the Agents will move and do actions.<b> 
	  * It is a square depending on the variable size entered at the creation of this class.  
	  */
	private Agent[][] space;
	
	/** The size of the space */
	private int size;
	
	
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
	public Agent getAgentAt(int x, int y) {
		if (x > this.size || y > this.size || x < 0 || y < 0) return null;
		
		return this.space[x][y]; 
	}
	
	/**
	 * Set the agent passed in parameters at the specified position.
	 * 
	 * @param x the x axis value
	 * @param y the y axis value
	 * @param agent the agent to be placed
	 * @return if (x > this.size || y > this.size || x < 0 || y < 0) false else true.
	 */
	public boolean setAgentAt(int x, int y, Agent agent) {
		if (x > this.size || y > this.size || x < 0 || y < 0) return false;
		
		this.space[x][y] = agent;
		return true;
	}

	/**
	 * Gets the space containing the agents
	 * 
	 * @return the space.
	 */
	public Agent[][] getAgents() { return this.space; }
}
