package wator;

import java.awt.Color;

import core.Agent;
import core.Environment;


/**
 * This is the Prey agent used in the Simulation based on the prey - predator.<br>
 * <br>
 * This class stores the data needed to be able to run the simulation :<br>
 * - the spawn cycle value<br>
 * - and the age of this prey.
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Prey extends Agent {
	
	/** The number of cycle this prey has done */
	private int age;

	/** Number of cycles a prey must exist before reproducing */
	private int spawnCycle;

	public Prey(Environment env, int spawnCycle, boolean getAPlace) {
		super(env);

		this.color = Color.GREEN;
		this.spawnCycle = spawnCycle;
		this.age = 1;
		this.currentDirection.getRandomDirection();

		if (getAPlace) this.environment.getPlace(this);
	}


	@Override
	public void doAction() {
		if(this.age % this.spawnCycle == 0) { 
			this.spawnNewPrey();
		}

		this.move();

		this.age++;
	}	

	/** 
	 *  Move the prey in the environment.<br> 
	 *  <br>
	 *  If it encounters something get another direction,<br>
	 *  else just move to the new position.
	 */
	private void move() {

		int size = this.environment().getSize();

		// get the new place for a toric environment
		int newXPlace = (this.getXPlaceAfterMovement() + size) % size;
		int newYPlace = (this.getYPlaceAfterMovement() + size) % size;

		Agent agent = this.environment.getAgentAt(newXPlace, newYPlace);

		// the new place is empty
		if (agent == null) {
			this.environment.moveAgent(this, newXPlace, newYPlace);
		}
		// the new place isn't empty
		else {
			this.currentDirection.getDifferentRandomDirection();
		}
	}
	
	/** 
	 * Create a new prey without giving him a new position.
	 */
	private void spawnNewPrey() { 
		((Wator)this.environment).addAgentToList(new Prey(this.environment, this.spawnCycle, false));
	}

	/**
	 * Get the age of this agent.
	 * 
	 * @return his age.
	 */
	public String getAge() { return this.age + ""; }
}
