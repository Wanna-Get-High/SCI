package model;

import java.awt.Color;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Predator extends Agent {
	
	/** The number of cycle of this predator */
	private int nbCyles;
	
	/** Number of cycles a predator must exist before reproducing */
	private int breed;
	
	private int starve;
	
	public Predator(Environment env, int breed, int starve) {
		super(env);
		
		this.breed = breed;
		this.starve = starve;
		this.nbCyles = 0;
		this.color = Color.RED;
		this.type("PREDATOR");
	}

	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		this.nbCyles++;
		
		if(this.nbCyles == this.starve) { 
			this.starveToDeath(); 
		} else {
			if(this.nbCyles % this.breed == 0) {
				this.reproduct();
			}
			
			this.move();
		}
	}

	/**
	 * An action of this Agent.
	 * 
	 * Move the agent in the environment.
	 */
	private void move() {
		
		int newXPlace = this.getXPlaceAfterMovement();
		int newYPlace = this.getYPlaceAfterMovement();
		int size = this.environment().getSize();
		
		
		
		
		// encounter a wall
		if (newXPlace >= size || newYPlace >= size || newXPlace < 0 || newYPlace < 0 ) {
			if (newXPlace >= size || newXPlace < 0) {
				this.reverseXDirection();
			}
			
			if (newYPlace >= size || newYPlace < 0) {
				this.reverseYDirection();
			}
			
		} else {
			
			Agent agent = this.environment().getAgentAt(newXPlace, newYPlace);
			
			// the new place is empty
			if (agent == null) {
				
				this.environment().setAgentAt(this.x(), this.y(), null);
				this.environment().setAgentAt(newXPlace, newYPlace, this);
				
				this.x(newXPlace);
				this.y(newYPlace);
				
			} else { // the new place isn't empty
				
				if(agent.type().equals("PREY")) {

					this.kill((Prey)agent);

					// remove from the environment
					this.environment().setAgentAt(newXPlace, newYPlace, null);
					this.environment().setAgentAt(newXPlace, newYPlace, this);

					// set the place of this agent
					this.x(newXPlace);
					this.y(newYPlace);
					
					
				} else {
					this.reverseXDirection();
					this.reverseYDirection();
				}
			}
		}
	}
		
	
	private void kill(Prey prey) {
	}
	
	private void reproduct() {
	}

	private void starveToDeath() {
	}
	

}
