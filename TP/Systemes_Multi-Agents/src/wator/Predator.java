package wator;

import java.awt.Color;
import java.util.ArrayList;

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
			
			this.environment.moveAgent(this, newXPlace, newYPlace);
			this.addToNextGeneration(this);
			
			
		} else { // the new place isn't empty
			
			if(agent.type().equals("PREY")) {
				
				//Prey prey = ((Prey)agent);
				this.removePrey((Prey)agent);

				this.environment.moveAgent(this, newXPlace, newYPlace);
				this.addToNextGeneration(this);
				
				this.starveCyle = 0;
				
			} else {
				this.reverseXDirection();
			}
		}
	}
	
	private void removePrey(Prey prey) {
		//ArrayList<Agent> list = ;
		int index = ((Wator)(this.environment)).agentsToAdd.indexOf(prey);
		
		if (index != -1 ) {
			Agent agent = ((Wator)(this.environment)).agentsToAdd.get(index);
			this.kill(agent);
			((Wator)(this.environment)).agentsToAdd.remove(index);
		} else {
			prey.isDead();
		}
		
		this.kill(prey);
		
	} 
	
	
	
	private void addToNextGeneration(Agent agent) { ((Wator)(this.environment)).addToNextGeneration(this); }
	
	private void kill(Agent agent) { ((Wator)(this.environment)).freeAgent(agent); }
	
	private void reproduct() { ((Wator)(this.environment)).addPredator(); }
	
	public String getAge() { return this.nbCyles + ""; }
}
