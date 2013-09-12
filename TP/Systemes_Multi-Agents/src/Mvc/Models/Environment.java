package Mvc.Models;

public class Environment {

	int[][] space;
	
	public Environment(int size) {
		this.space = new int[size][size];
		this.init();
	}
	
	public void init(){
		
	}
	
	public void getPlace(Agent agent) {
		agent.x(0);
		agent.y(0);
	}	
}
