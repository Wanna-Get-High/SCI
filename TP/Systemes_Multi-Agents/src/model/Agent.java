package model;

import java.awt.Color;
import java.util.Random;

/**
 * This is the agent class.
 * 
 * This class is used to store the position, current direction
 * and the behavior of the agent in the environment.
 * 
 * 
 * @author Franois Lepan - Alexis Linke
 *
 */
public class Agent {

	/** The position of the agent on the x axis of the environment. */
	protected int x;
	
	/** The position of the agent on the y axis of the environment. */
	protected int y;
	
	/** The environment where the agent is moving */
	protected Environment environment;
	
	/** The current direction of the agent */
	protected Direction currentDirection;
	
	/** The id of this Agent */
	protected int id;
	
	/** The color that will be used to represent this agent in the view */
	protected Color color;
	
	public Agent(Environment env, int id) {
		this.environment = env;
		this.init();
		this.id = id;
	}
	
	/**
	 * Initialize the place of this agent in the environment and it's direction.
	 * 
	 * For now it just get a random place, a random direction and a random color.
	 */
	private void init() {
		this.environment.getPlace(this);
		
		int x = (int)(Math.random()*3)-1;
		int y = (int)(Math.random()*3)-1;
		
		this.currentDirection = new Direction(x, y);
		
		Random randomfloat = new Random();
		
		this.color = new Color(randomfloat.nextFloat(),randomfloat.nextFloat(),randomfloat.nextFloat());
	}
	
	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		this.move();
	}
	
	
	public void move() {
		
		int newXPlace = this.getXPlaceAfterMovement();
		int newYPlace = this.getYPlaceAfterMovement();
		int size = this.environment.getSize();
		
		// encounter a wall
		if (newXPlace >= size || newYPlace >= size || newXPlace < 0 || newYPlace < 0 ) {
			if (newXPlace >= size || newXPlace < 0) {
				this.reverseDirectionOfX();
			}
			
			if (newYPlace >= size || newYPlace < 0) {
				this.reverseDirectionOfY();
			}
			
		} else {
			
			Agent agent = this.environment.getAgentAt(newXPlace, newYPlace);
			
			// the new place is empty
			if (agent == null) {
				
				this.environment.setAgentAt(this.x, this.y, null);
				this.environment.setAgentAt(newXPlace, newYPlace, this);
				
				this.x(newXPlace);
				this.y(newYPlace);
				
			} else { // the new place isn't empty
				
				// TODO : make it more natural
				
				this.reverseDirectionOfX();
				this.reverseDirectionOfY();
				
				/*
				Agent encounterdAgent = this.space[newXPlace][newYPlace];
						
				int encounteredAgentXDirection = encounterdAgent.getDirectionOfX();
				int encounteredAgentYDirection = encounterdAgent.getDirectionOfY();
				
				int xDirection = agent.getDirectionOfX();
				int yDirection = agent.getDirectionOfY(); 
				
				if ( encounteredAgentXDirection + xDirection == 0 ) {
					agent.reverseDirectionOfX();
				} else if ( encounteredAgentXDirection + xDirection == 0 ) {
					
				}
				
				if ( encounteredAgentYDirection + yDirection == 0) {
					agent.reverseDirectionOfY();
				}
				*/
				
			}
		}
	}
	
	
	public void setNewDirectionOfX(int x) { this.currentDirection.x(x); }
	
	public void setNewDirectionOfY(int y) { this.currentDirection.y(y); }
	
	
	public void reverseDirectionOfX() { this.currentDirection.reverseXDirection(); }
	
	public void reverseDirectionOfY() { this.currentDirection.reverseYDirection(); }
	
	
	public int getXPlaceAfterMovement() { return this.x + this.currentDirection.x(); }
	
	public int getYPlaceAfterMovement() { return this.y + this.currentDirection.y(); }
	
	/**
	 * Get the current direction on the x axis of this agent;
	 * 
	 * @return the direction on the x axis of this agent.
	 */
	public int getDirectionOfX(){ return this.currentDirection.x(); }
	
	/**
	 * Get the current direction on the y axis of this agent;
	 * 
	 * @return the direction on the y axis of this agent.
	 */
	public int getDirectionOfY(){ return this.currentDirection.y(); }
	
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
	public void x (int x){ this.x = x; }
	
	/**
	 * Set the y position of this agent;
	 * 
	 * @param x the new position on the y axis.
	 */
	public void y (int y) { this.y = y; }
	
	/**
	 * Get the id of this Agent.
	 * 
	 * @return id of this Agent.
	 */
	public int id() { return this.id; }
	
	
	public Color color() { return this.color; }
	
}
