package core;

import java.awt.Color;

/**
 * This is the agent class.<br>
 * <br>
 * This abstract class is used to store :<br>
 * - its environment,<br>
 * - its position (x,y),<br>
 * - the current direction of the agent in the environment.<b>
 * - the type of agent,<br>
 * - and the color of this agent.<br>
 * <br>
 * It has to be inherited by another class that will implement the behavior (doAction) of a particular agent.
 * 
 * @author Francois Lepan - Alexis Linke
 *
 */
public abstract class Agent {
	
	/** The position of the agent on the x axis of the environment. */
	protected int x;
	
	/** The position of the agent on the y axis of the environment. */
	protected int y;
	
	/** the color of this agent in the Environment*/
	protected Color color;
	
	/** The environment where the agent is moving */
	protected Environment environment;
	
	/** The current direction of the agent */
	protected Direction currentDirection;
	
	public Agent(Environment env) {
		this.environment = env;
		this.currentDirection = new Direction();
	}
	
	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public abstract void doAction();
	
	/**
	 * Get the new position of X after the movement of this agent 
	 * 
	 * @return the new position of X
	 */
	public int getXPlaceAfterMovement() { return this.x + this.currentDirection.x(); }
	
	/**
	 * Get the new position of Y after the movement of this agent
	 * 
	 * @return the new position of Y
	 */
	public int getYPlaceAfterMovement() { return this.y + this.currentDirection.y(); }
	
	/**
	 * Reverse the direction on the X axis
	 */
	public void reverseXDirection() { this.currentDirection.reverseXDirection(); }
	
	/**
	 * Reverse the direction on the Y axis
	 */
	public void reverseYDirection() { this.currentDirection.reverseYDirection(); }
	
	/**
	 * Get the current direction on the x axis of this agent;
	 * 
	 * @return the direction on the x axis of this agent.
	 */
	public int getXDirection(){ return this.currentDirection.x(); }
	
	/**
	 * Get the current direction on the y axis of this agent;
	 * 
	 * @return the direction on the y axis of this agent.
	 */
	public int getYDirection(){ return this.currentDirection.y(); }
	
	/**
	 * Set the new direction on the x axis of this agent;
	 * 
	 * @param x the new direction on the x axis
	 */
	public void setNewXDirection(int x) { this.currentDirection.x(x); }
	
	/**
	 * Set the new direction on the y axis of this agent;
	 * 
	 * @param y the new direction on the y axis
	 */
	public void setNewYDirection(int y) { this.currentDirection.y(y); }
	
	/**
	 * Set the x position of this agent;
	 * 
	 * @param x the new position on the x axis.
	 */
	public int x() { return this.x; }
	
	/**
	 * Set the y position of this agent;
	 * 
	 * @param x the new position on the y axis.
	 */
	public int y() { return this.y; }
	
	/**
	 * Set the x position of this agent;
	 * 
	 * @param x the new position on the x axis.
	 */
	public void x (int x) { this.x = x; }
	
	/**
	 * Set the y position of this agent;
	 * 
	 * @param x the new position on the y axis.
	 */
	public void y (int y) { this.y = y; }
	
	/**
	 * Get the environment of this Agent.
	 * 
	 * @return environment of this Agent.
	 */
	public Environment environment() { return this.environment; }
	
	/**
	 * Get the color of this agent.
	 * 
	 * @return the color of this agent
	 */
	public Color color() { return this.color; }
	
}
