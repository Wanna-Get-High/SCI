package wator;

import java.awt.Color;

import core.Agent;
import core.Environment;


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
		this.environment.getPlace(this);
		this.currentDirection.getRandomDirection();
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

		int size = this.environment().getSize();
		
		// to make a toric environment 
		int newXPlace = (this.getXPlaceAfterMovement() + size) % size;
		int newYPlace = (this.getYPlaceAfterMovement() + size) % size;
		
		Agent agent = this.environment.getAgentAt(newXPlace, newYPlace);
		
		// the new place is empty
		if (agent == null) {
			
			this.environment.moveAgent(this, newXPlace, newYPlace);

		} else { // the new place isn't empty

			this.currentDirection.getDifferentRandomDirection();
		}
		
	}
	
	private void reproduct() { ((Wator)this.environment).addPrey(); }
	
	public String getAge() { return this.nbCyles + ""; }
	
}
