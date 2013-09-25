package model;

import java.util.ArrayList;
import java.util.Collections;


/**
 * This class stock the Environment.
 * 
 * The environment is an array of integer.
 * 0 if a cell of the array is empty otherwise the id of an agent.  
 * 
 * @author Francois Lepan - Alexis Linke
 */
public abstract class Environment {
	
	protected ArrayList<Agent> agentsToAdd;
	
	protected ArrayList<Agent> agentsToRemove;
	
	protected ArrayList<Integer> remainingIndexes;
	
	
	/** 
	  * The space in which the Agents will move and do actions.<b> 
	  * It is a square depending on the variable size entered at the creation of this class.  
	  */
	private Agent[][] space;
	
	/** The size of the space */
	private int size;
	
	public Environment(int size) {
		this.agentsToAdd = new ArrayList<Agent>();
		this.agentsToRemove = new ArrayList<Agent>();	
		
		this.size = size;		
		this.space = new Agent[size][size];
		
		this.remainingIndexes = new ArrayList<Integer>();
		
		this.initRemaningIndexes();
	}
	
	
	private void initRemaningIndexes() {
		for (int i = 0; i < this.size*this.size; i++) {
				this.remainingIndexes.add(i);
		}
	}

	/**
	 * This method search for a random place inside the space <b> 
	 * and when it found one it saves the place for this agent.
	 * 
	 * @param agent the agent that will be put inside a cell.
	 */
	public void getPlace(Agent agent) {
		
		if (!this.remainingIndexes.isEmpty()) {
			
			// get a random empty place
			Collections.shuffle(this.remainingIndexes);
			
			int value = this.remainingIndexes.get(0);
			
			Integer xPlace = this.getXfromValue(value);
			Integer yPlace = this.getYfromValue(value);
			
			// set the place and index of the agent
			agent.x(xPlace);
			agent.y(yPlace);
			
			this.space[xPlace][yPlace] = agent;
			
			this.remainingIndexes.remove((Integer)value);
		}
	}
	
	
	private int getValueFrom(int x, int y) {
		return x*this.size + y;
	}
	
	private int getXfromValue(int value) {
		return value / this.size;
	}
	
	private int getYfromValue(int value) {
		return value % this.size;
	}
	

	public abstract void addAgentsTo(ArrayList<Agent> agents);
	
	public abstract void removeAgentsTo(ArrayList<Agent> agents);
	
	/**
	 * Get the size of the environment.
	 * 
	 * @return the size of the environment.
	 */
	public int getSize() { return this.size; }

	
	public void removeAgent(Agent agent) {
		
		int x = agent.x();
		int y = agent.y();
		
		this.remainingIndexes.add(this.getValueFrom(x, y));
		
		this.space[x][y] = null;
	}
	
	public void addAgent(Agent agent) {
		int x = agent.x();
		int y = agent.y();

		this.remainingIndexes.remove((Integer)this.getValueFrom(x, y));
		
		this.space[x][y] = agent;
	}
	
	
	public void moveAgent(Agent agent, int newXPlace, int newYPlace) {
		this.removeAgent(agent);
		agent.x(newXPlace);
		agent.y(newYPlace);
		this.addAgent(agent);
	}

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
	 * Gets the space containing the agents
	 * 
	 * @return the space.
	 */
	public Agent[][] getAgents() { return this.space; }
}
