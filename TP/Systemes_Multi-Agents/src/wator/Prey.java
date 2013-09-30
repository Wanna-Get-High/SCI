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
	
	public Prey(Environment env, int breed, boolean getAPlace) {
		super(env);
		
		this.color = Color.GREEN;
		this.breed = breed;
		this.nbCyles = 1;
		this.type("PREY");
		this.currentDirection.getRandomDirection();
		
		if (getAPlace) this.environment.getPlace(this);
	}
	
	
	@Override
	public void doAction() {
		if(this.nbCyles % this.breed == 0) { 
			this.reproduct();
		}
			
		this.move();
		this.nbCyles++;
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
			
			this.move(newXPlace, newYPlace);

		} else { // the new place isn't empty

			this.reverseXDirection();
		}
		
	}
	
	private void move(int newXPlace, int newYPlace) { ((Wator)(this.environment)).move(this, newXPlace, newYPlace); }
	
	private void reproduct() { ((Wator)this.environment).addPrey(); }
	
	public String getAge() { return this.nbCyles + ""; }
	
}
