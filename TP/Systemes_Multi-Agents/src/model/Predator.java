package model;

import java.awt.Color;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Predator extends Agent {
	
	/** The color of predators */
	private static Color predatorColor = Color.RED;
	
	/** The number of cycle of this predator */
	private int nbCyles;
	
	/** The wator environment where the predator is moving */
	private Wator wat;
	
	public Predator(Wator env, int id) {
		super(env, id);
		this.init();
	}
	 /**
	  * 
	  */
	private void init() {
		
		this.nbCyles = 0;
		
		this.setColor(predatorColor);
		
		this.setType("PREDATOR");
		
		this.wat = (Wator)this.environment();
		
	}
	
	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		this.nbCyles++;
		Wator wat = (Wator)this.environment();
		if(this.nbCyles % wat.predatorbreed() == 0) this.reproduct();
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
				
				if(this.nbCyles % this.wat.starve() == 0) { this.dies(); }
				
			} else { // the new place isn't empty
				if(agent.type().equals("PREY")) {
					
					// TODO removing correctly the prey
					/* this.environment().setAgentAt(newXPlace, newYPlace, null);
					this.environment().setAgentAt(newXPlace, newYPlace, this);

					this.x(newXPlace);
					this.y(newYPlace); */
					
				} else {
					this.reverseDirectionOfX();
					this.reverseDirectionOfY();
					
					if(this.nbCyles % this.wat.starve() == 0) { this.dies(); }
				}
			}
		}
	}
		
	
	private void reproduct() {
		// TODO adding correctly a new predator
	}

	private void dies() {	
		// TODO removing correctly this predator
		//this.environment().setAgentAt(this.x(), this.y(), null);
	}
	

}
