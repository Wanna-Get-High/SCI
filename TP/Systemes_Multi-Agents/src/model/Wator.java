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
		
		if (agents.size() < this.getSize()*this.getSize()) {
			agents.addAll(this.agentsToAdd);
		}

		this.agentsToAdd.clear();
	}
	
	@Override
	public void removeAgentsTo(ArrayList<Agent> agents) {

		for (Agent agent : this.agentsToRemove) {
			this.removeAgent(agent);
		}
		
		agents.removeAll(this.agentsToRemove);
		
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
	
	public String getNbPredPrey() {
		int nbPred = 0;
		int nbPrey = 0;
		
		for (int i = 0; i< this.getSize(); i++) {
			for (int j = 0; j< this.getSize(); j++) {
				Agent agent = this.getAgents()[i][j];
				
				if (agent != null) {
					if ("PREY".equals(agent.type())) {
						nbPrey++;
					} else {
						nbPred++;
					}
				}
			}
		}
		
		return nbPred + " " + nbPrey + "\n";
	}
	
	public String getAges() {
		String ages = "";
		
		for (int i = 0; i< this.getSize(); i++) {
			for (int j = 0; j< this.getSize(); j++) {
				Agent agent = this.getAgents()[i][j];
				
				if (agent != null) {
					if ("PREY".equals(agent.type())) {
						ages += ((Prey)agent).getAge() + " ";
					} else {
						ages += ((Predator)agent).getAge() + " ";
					}
				}
			}
		}
		
		return ages + " \n";
	}
}
