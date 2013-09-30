package wator;

import java.awt.Color;

import core.Agent;
import core.Environment;


/**
 * This is the Predator agent used in the Simulation based on the prey - predator.
 * 
 * This class stores the data needed to be able to run the simulation :
 * the starving value
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Predator extends Agent {
	
	/** The number of cycle of this predator */
	private int nbCyles;
	
	/** The number of cycle done since this predator ate something */
	private int starveCyle;
	
	/** Number of cycles a predator must exist before reproducing */
	private int breed;
	
	private int starve;
	
	public Predator(Environment env, int breed, int starve, boolean getAPlace) {
		super(env);
		
		this.breed = breed;
		this.starve = starve;
		this.nbCyles = 1;
		this.starveCyle = 0;
		this.color = Color.RED;
		this.type("PREDATOR");
		
		if (getAPlace) this.environment.getPlace(this);
	}

	/**
	 * The main method of this class.
	 * 
	 * Decide what to do for this agent.
	 */
	public void doAction() {
		if (this.starveCyle >= this.starve) { 
			this.kill(this);
		} else {
			
			if(this.nbCyles % this.breed == 0) {
				this.reproduct();
			}
			
			this.move();	
		}
		
		this.nbCyles++;
		this.starveCyle++;
	}

	/**
	 * An action of this Agent.
	 * 
	 * Move the agent in the environment.
	 */
	private void move() {
		
		int size = this.environment().getSize();
		
		int newXPlace = (this.getXPlaceAfterMovement() + size) % size;
		int newYPlace = (this.getYPlaceAfterMovement() + size) % size;
		
		Agent agent = this.environment().getAgentAt(newXPlace, newYPlace);
		
		// the new place is empty
		if (agent == null) {
			
			//this.environment.moveAgent(this, newXPlace, newYPlace);
			this.move(newXPlace, newYPlace);
			
		} else { // the new place isn't empty
			
			if(agent.type().equals("PREY")) {
					
				this.starveCyle = 0;
				this.kill(agent);
				this.move(newXPlace, newYPlace);
				
			} else {
				this.reverseXDirection();
			}
		}
	}
	
	private void move(int newXPlace, int newYPlace) { ((Wator)(this.environment)).move(this, newXPlace, newYPlace); }
	
	private void kill(Agent agent) { ((Wator)(this.environment)).AddToAgentsToRemove(agent); }
	
	private void reproduct() { ((Wator)(this.environment)).addPredator(); }
	
	public String getAge() { return this.nbCyles + ""; }
}
