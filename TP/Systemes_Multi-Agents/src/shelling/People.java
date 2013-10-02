package shelling;

import java.awt.Color;

import core.Agent;
import core.Environment;

/**
 * This the agent for the Schelling method.<br>
 * It calculate it's satisfaction depending on the neighbors.
 * 
 * @author Francois Lepan, Alexis Linke
 *
 */
public class People extends Agent {
	
	/** the threshold of satisfaction minimum */ 
	private float threshold;
	
	/** the satisfaction of this agent */
	private float satisfaction;

	public People(Environment env, Color color, float threshold) {
		super(env);
		
		this.color=color;
		this.satisfaction=0;
		this.threshold=threshold;
		this.environment().getPlace(this);
	}	

	@Override
	public void doAction() {
		Agent[][] agents = this.environment().getAgentsSpace();
		
		float nNeighbor=0; 
		float nSatisfaction=0;
		
		int size = this.environment.getSize();
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				
				int newX = this.x()+i;
				int newY = this.y()+j;
				
				if(newX >= 0 && newY >= 0 &&
					newX < size && newY < size &&
					agents[newX][newY]!=null) {
					
					nNeighbor++;
					if(agents[newX][newY].color().equals(this.color())) 
						nSatisfaction++;
					
				}
			}
		}
		
		if(nNeighbor!=0) this.satisfaction=nSatisfaction/nNeighbor;
		
		if(this.satisfaction<threshold) {
			this.move();
		}
	}

	/**
	 * Move this agent to another random place.
	 */
	private void move() {		
		this.environment.removeAgent(this);
		this.environment.getPlace(this);
	}
	
	/**
	 * Get the satisfaction of this agent.
	 * 
	 * @return the satisfaction of this agent.
	 */
	public float satisfaction() { return this.satisfaction; }

}
