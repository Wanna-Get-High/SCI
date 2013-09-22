package model;

import java.awt.Color;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Prey extends Agent {

	/** The color of preys */
	private static Color preyColor = Color.GREEN;
	
	/** The number of cycle of this prey */
	private int nbCyles;
	
	public Prey(Wator env, int id) {
		super(env, id);
		this.init();
	}
	
	/**
	 * 
	 */
	private void init() {
		
		this.nbCyles = 0;
		
		this.setColor(preyColor);
		
		this.setType("PREY");
		
	}
	
	
	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		this.nbCyles++;
		Wator wat = (Wator)this.environment();
		if(this.nbCyles == wat.preybreed()) { this.reproduct(); this.nbCyles = 0; }
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
				this.reverseDirectionOfX();
			}
			
			if (newYPlace >= size || newYPlace < 0) {
				this.reverseDirectionOfY();
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

				this.reverseDirectionOfX();
				this.reverseDirectionOfY();
			}
		}
	}
	
	/**
	 *
	 */
	private void reproduct() { 	
		// TODO adding correctly a new prey
	}

}
