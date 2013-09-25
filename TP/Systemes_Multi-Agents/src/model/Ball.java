package model;

import java.awt.Color;
import java.util.Random;



public class Ball extends Agent {
	
	/** The color that will be used to represent this agent in the view */
	protected Color color;
	
	public Ball(Environment env) {
		super(env);
		this.environment.getPlace(this);
		this.currentDirection.getRandomDirection();
		this.color = this.getRandomColor();
	}

	@Override
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
				this.reverseXDirection();
			}
			
			if (newYPlace >= size || newYPlace < 0) {
				this.reverseYDirection();
			}
			
		} else {
			
			Agent agent = this.environment.getAgentAt(newXPlace, newYPlace);
			
			// the new place is empty
			if (agent == null) {
				
				this.environment.moveAgent(this, newXPlace, newYPlace);
				
			} else { // the new place isn't empty

				this.reverseXDirection();
				this.reverseYDirection();
			}
		}
	}
	
	
	private Color getRandomColor() {
		Random randomfloat = new Random();
		
		return new Color(randomfloat.nextFloat(),randomfloat.nextFloat(),randomfloat.nextFloat());
	}
}
