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
public abstract class Environment {	
	/** 
	  * The space in which the Agents will move and do actions.<b> 
	  * It is a square depending on the variable size entered at the creation of this class.  
	  */
	private Agent[][] space;
	
	/** The size of the space */
	private int size;
	

	
	/**
	 * The remaining empty cell of the space. <b>
	 * <b>
	 * The indexes stored in this ArrayList comes from the Agent space.<b>
	 * <b>
	 * Example : if the size is 3 the space look like that<b> 
	 * [0, 1, 2] <b>
	 * [3, 4, 5] <b>
	 * [6, 7, 8] <b>
	 * <b>
	 * to retrieve the middle value (index 4) i need to do these calculations :<b>
	 * <b>
	 * row -> x = 4 / size <b>
	 * col -> y = 4 % size <b>
	 * <b>
	 * And if we want to get the index from the position of x and y : <b>
	 * index = x*size + y 
	 */
	protected ArrayList<Integer> remainingIndexes;
	
	
	public Environment(int size) {
		this.size = size;		
		this.space = new Agent[size][size];
		
		this.remainingIndexes = new ArrayList<Integer>();
		this.initRemaningIndexes();
	}
	
	/**
	 * Initialize the remainingIndexes ArrayList
	 */
	private void initRemaningIndexes() {
		for (int i = 0; i < this.size*this.size; i++) {
				this.remainingIndexes.add(i);
		}
	}

	/**
	 * The method that add the new born agent to the global ArrayList containing the agent
	 * 
	 * @param agents the global ArrayList
	 */
	public abstract void addAgentsTo(ArrayList<Agent> agents);
	
	/**
	 * The method that remove the dead agent from the global ArrayList containing the agent
	 * 
	 * @param agents the global ArrayList
	 */
	public abstract void removeAgentsTo(ArrayList<Agent> agents);
	
	/**
	 * write datas to a file
	 */
	public abstract void writeData();
	
	/**
	 * This method search for a random place inside the space <b> 
	 * and when it found one it saves the place for this agent.
	 * 
	 * @param agent the agent that will be put inside a cell.
	 */
	public boolean getPlace(Agent agent) {
		
		// get a random place until it is empty
//		int xPlace = (int) (Math.random()*(this.size-1));
//		int yPlace = (int) (Math.random()*(this.size-1));
//		
//		while(this.space[xPlace][yPlace] != null ) {
//			xPlace = (int) (Math.random()*(this.size-1));
//			yPlace = (int) (Math.random()*(this.size-1));
//		}
		
		
		// set the place of the agent and fill the place with it
		//agent.x(xPlace);
		//agent.y(yPlace);
		
		//this.space[xPlace][yPlace] = agent;
		
//		for (int i = 0; i < this.size; i++) {
//		for (int j = 0; j < this.size; j++) {
//			if (this.space[i][j] == null) {
//				agent.x(i);
//				agent.y(j);
//				this.space[i][j] = agent;
//				return;
//			}
//		}
//	}
		
		
		if (this.remainingIndexes.isEmpty()) return false;
			
			// get a random empty place
			Collections.shuffle(this.remainingIndexes);
			
		//	System.out.println(this.remainingIndexes.size());
			
//			System.out.println("==================================");
			
			int value = this.remainingIndexes.get(0);
//			System.out.println("value = "+value);
			
			int xPlace = this.getXfromValue(value);
			int yPlace = this.getYfromValue(value);
			
//			System.out.println("x = "+xPlace);
//			System.out.println("y = "+yPlace);
			
//			System.out.println("calculated value = "+this.getValueFrom(xPlace, yPlace));
			
			
			// set the place and index of the agent
			agent.x(xPlace);
			agent.y(yPlace);
			
			this.space[xPlace][yPlace] = agent;
			this.remainingIndexes.remove((Integer)value);
			
//			System.out.println("contained : "+);
//			System.out.println("can get the removed item : "+this.remainingIndexes.contains((Integer)value));
		return true;
	}
	
	/**
	 * Remove an agent from the space and add its position to the remainingIndexes.
	 * 
	 * @param agent the agent to be removed
	 */
	public void removeAgent(Agent agent) {
		
		int x = agent.x();
		int y = agent.y();
		
//		System.out.println("=====================================================");
//		System.out.println("before : "+this.remainingIndexes.size());
		this.remainingIndexes.add(this.getValueFrom(x, y));
//		System.out.println("after : "+this.remainingIndexes.size());
		
		this.space[x][y] = null;
	}
	
	/**
	 * Add an agent to the space and remove its position to the remainingIndexes.
	 * 
	 * @param agent the agent to be added
	 * @return true if it has added the agent to the environment
	 */
	public boolean addAgent(Agent agent) {
		if (this.remainingIndexes.isEmpty()) return false;
		
		int x = agent.x();
		int y = agent.y();
		
		//System.out.println("=====================================================");
		//System.out.println("before : "+this.remainingIndexes.size());
		this.remainingIndexes.remove((Integer)this.getValueFrom(x, y));
		//System.out.println(b);
		//System.out.println("after : "+this.remainingIndexes.size());
		
		this.space[x][y] = agent;
		
		return true;
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
		this.addAgent(agent);
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
	 * Gets the space containing the agents
	 * 
	 * @return the space.
	 */
	public Agent[][] getAgents() { return this.space; }
	
	
	/**
	 * Calculate the value that has to be added to the ArrayList remainingIndexes
	 * 
	 * @param x the x position on the Agent space 
	 * @param y the y position on the Agent space
	 * @return the value corresponding to the position of the agent
	 */
	private int getValueFrom(int x, int y) {
		return x*this.size + y;
	}
	
	/**
	 * Calculate the x value depending on the retrieved value of the ArrayList remainingIndexes
	 * 
	 * @param value retrieved from the ArrayList 
	 * 
	 * @return the x position on the Agent space
	 */
	private int getXfromValue(int value) {
		return value / this.size;
	}
	
	/**
	 * Calculate the y value depending on the retrieved value of the ArrayList remainingIndexes
	 * 
	 * @param value retrieved from the ArrayList 
	 * 
	 * @return the y position on the Agent space
	 */
	private int getYfromValue(int value) {
		return value % this.size;
	}

	public int getIndexesSize() {
		return this.remainingIndexes.size();
	}
}
