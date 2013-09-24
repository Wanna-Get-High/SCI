package model;

import java.awt.Color;

public class People extends Agent {
	
	/** */ 
	private float threshold;
	
	/** */
	private float satisfaction;

	public People(Environment env, Color color, float threshold) {
		super(env);
		
		this.color=color;
		this.satisfaction=0;
		this.threshold=threshold;
		this.environment().getPlace(this);
	}	

	public void doAction() {
		Agent[][] agents = this.environment().getAgents();
		float nbVoisin=0; 
		float nbSatisfaction=0;
		for(int i=this.x-1; i<=this.x+1;i++) {
			for(int j=this.y-1; j<=this.y+1; j++) {
				if(i!=this.x && j!=this.y)
					if(i>-1 && i<this.environment().getSize() && j>-1 && j<this.environment().getSize()) {
						if(agents[i][j] !=null) {
							nbVoisin++;
							if(agents[i][j].color().equals(this.color)) {
								nbSatisfaction++;
							}
						}
					}
			}
		}
		if(nbVoisin!=0)
			this.satisfaction=nbSatisfaction/nbVoisin;
		if(this.satisfaction<threshold) {
			this.move();
		}
	}

	private void move() {
		this.environment().getAgents()[this.x][this.y]=null;
		this.environment().getPlace(this);	
	}

}
