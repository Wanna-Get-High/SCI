package shelling;

import java.awt.Color;

import core.Agent;
import core.Environment;

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
		float nNeighbor=0; 
		float nSatisfaction=0;
		try { if(agents[this.x()-1][this.y()-1]!=null) {
			nNeighbor++;
			if(agents[this.x()-1][this.y()-1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()][this.y()-1]!=null) {
			nNeighbor++;
			if(agents[this.x()][this.y()-1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()+1][this.y()-1]!=null) {
			nNeighbor++;
			if(agents[this.x()+1][this.y()-1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()-1][this.y()]!=null) {
			nNeighbor++;
			if(agents[this.x()-1][this.y()].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()+1][this.y()]!=null) {
			nNeighbor++;
			if(agents[this.x()+1][this.y()].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()-1][this.y()+1]!=null) {
			nNeighbor++;
			if(agents[this.x()-1][this.y()+1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()][this.y()+1]!=null) {
			nNeighbor++;
			if(agents[this.x()][this.y()+1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		try {if(agents[this.x()+1][this.y()+1]!=null) {
			nNeighbor++;
			if(agents[this.x()+1][this.y()+1].color().equals(this.color()))
				nSatisfaction++;
		}} catch(ArrayIndexOutOfBoundsException e) {}
		if(nNeighbor!=0)
			this.satisfaction=nSatisfaction/nNeighbor;
		if(this.satisfaction<threshold) {
			this.move();
		}
	}

	private void move() {		
		this.environment.removeAgent(this);
		this.environment.getPlace(this);
	}
	
	public float satisfaction() { return this.satisfaction; }

}
