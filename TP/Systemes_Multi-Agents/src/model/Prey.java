package model;

import java.awt.Color;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Prey extends Agent {

	/** The number of cycle of this prey */
	private int nbCyles;
	
	/** Number of cycles a prey must exist before reproducing */
	private int breed;

	public Prey(Environment env, int breed) {
		super(env);
		
		this.color = Color.GREEN;
		this.breed = breed;
		this.nbCyles = 0;
		this.type("PREY");
	}
	
	@Override
	public void doAction() {
		this.nbCyles++;

		if(this.nbCyles % this.breed == 0) { 
			this.reproduct();
		}
			
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
				this.environment.setAgentAt(this.x, this.y, null);
				this.environment.setAgentAt(newXPlace, newYPlace, this);
			
				this.x(newXPlace);
				this.y(newYPlace);
				
			} else { // the new place isn't empty

				this.reverseXDirection();
				this.reverseYDirection();
			}
		}
	}
	
	private void reproduct() { ((Wator)this.environment).addPrey(); }
	
	
}
