package model;

import java.awt.Color;
import java.util.Random;

/**
 * This is the agent class.
 * 
 * This class is used to store the position, current direction
 * and the behavior of the agent in the environment.
 * 
 * @author Francois Lepan - Alexis Linke
 *
 */
public class Agent {

	/** The type of the agent */
	private String type;
	
	/** The position of the agent on the x axis of the environment. */
	private int x;
	
	/** The position of the agent on the y axis of the environment. */
	private int y;
	
	/** The environment where the agent is moving */
	private Environment environment;
	
	/** The current direction of the agent */
	private Direction currentDirection;
	
	/** The id of this Agent */
	private int id;
	
	/** The color that will be used to represent this agent in the view */
	private Color color;
	
	public Agent(Environment env, int id) {
		this.environment = env;
		this.id = id;
		this.currentDirection = new Direction();
		
		this.init();
	}
	
	/**
	 * Initialize the place of this agent in the environment and it's direction.
	 * 
	 * For now it just get a random place, a random direction and a random color.
	 */
	private void init() {
		this.environment.getPlace(this);
		
		this.currentDirection.getRandomDirection();
		
		this.color = this.getRandomColor();
		
		this.type = "AGENT";
	}
	
	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		this.move();
	}
	
	
	/**
	 * An action of this Agent.
	 * 
	 * Move the agent in the environment.
	 */
	private void move() {
		
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

				this.reverseDirectionOfX();
				this.reverseDirectionOfY();
			}
		}
	}
	
	
	private Color getRandomColor() {
		Random randomfloat = new Random();
		
		return new Color(randomfloat.nextFloat(),randomfloat.nextFloat(),randomfloat.nextFloat());
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
	
	/**
	 * Get the environment of this Agent.
	 * 
	 * @return environment of this Agent.
	 */
	public Environment environment() { return this.environment; }
	
	/**
	 * Get the type of this Agent.
	 * 
	 * @return type of this Agent.
	 */
	public String type() { return this.type; }
	
	/**
	 * Set the type of this Agent.
	 * 
	 * @param type of this Agent.
	 */
	public void setType(String type) { this.type=type; }
	
	public Color color() { return this.color; }
	
	public void setColor(Color color) { this.color=color; }
	
}
