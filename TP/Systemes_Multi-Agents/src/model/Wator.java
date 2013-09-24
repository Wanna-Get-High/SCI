package model;

import java.util.ArrayList;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Wator extends Environment {
	
	private int preybreed;
	private int predatorbreed;
	private int starve;
	
	public Wator(int size, int preybreed, int predatorbreed, int starve) {
		super(size);
		this.preybreed = preybreed;
		this.predatorbreed = predatorbreed;
		this.starve = starve;
	}
	
	
	@Override
	public void addAgentsTo(ArrayList<Agent> agents) {
		agents.addAll(this.agentsToAdd);
		this.agentsToAdd.clear();
	}
	
	@Override
	public void removeAgentsTo(ArrayList<Agent> agents) {
		agents.addAll(this.agentsToAdd);
		this.agentsToRemove.clear();
	}
	
	public void addPrey() {
		this.agentsToAdd.add(this.newPrey());
	}
	
	public void addPredator() {
		this.agentsToAdd.add(this.newPredator());
	}
	
	public void removePredator(Predator pred) {
		this.agentsToRemove.add(pred);
	}
	
	public void removePrey(Prey prey) {
		this.agentsToRemove.add(prey);
	}	
	private Prey newPrey() {
		return new Prey(this, this.preybreed);
	}
	
	private Predator newPredator() {
		return new Predator(this, this.predatorbreed, this.starve);
	}
	
	public void writeData() {}
}
