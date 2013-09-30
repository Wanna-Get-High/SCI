package wator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.Agent;
import core.Environment;


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
	
	/**
	 * this list contains the new born agent
	 */
	protected ArrayList<Agent> agentsToAdd;
	
	/**
	 * this list contains the agents that are dead and has to be removed
	 */
	protected ArrayList<Agent> agentsToRemove;
	
	/**
	 * 
	 */
	protected ArrayList<Agent> agentsToMove;
	protected ArrayList<Agent> agentsToRemoveMove;

	
	
	
	public Wator(int size, int preybreed, int predatorbreed, int starve) {
		super(size);
		this.preybreed = preybreed;
		this.predatorbreed = predatorbreed;
		this.starve = starve;

		this.agentsToAdd = new ArrayList<Agent>();
		this.agentsToRemove = new ArrayList<Agent>();	
		this.agentsToMove = new ArrayList<Agent>();
		this.agentsToRemoveMove = new ArrayList<Agent>();
		
	}
	
	
	@Override
	public void addAgentsTo(ArrayList<Agent> agents) {
		
//		System.out.println("principale list : "+agents.size());
//		System.out.println("add list : "+this.agentsToAdd.size());
		
		//System.out.println("to be added : ");
		
//		for (Agent agent : this.agentsToMove) {
//			this.addAgent(agent);
//			//agents.add(agent);
//		}
		
//		for (Agent agent : this.agentsToAdd) {
//			if(this.getPlace(agent)) {
//				agents.add(agent);
//			}
//		}
		agents.clear();
		agents.addAll(this.agentsToAdd);
		
		

		//System.out.println("agentsToMove : "+this.agentsToMove.size());
		//System.out.println("agentsToAdd : "+this.agentsToAdd.size());
		
//		System.out.println("list after : "+agents.size());
		
		this.agentsToAdd.clear();
		
	}
	
	@Override
	public void removeAgentsTo(ArrayList<Agent> agents) {

//		System.out.println("principale list : "+agents.size());
		System.out.println("agentsToRemove : "+this.agentsToRemove.size());
		System.out.println("agentsToRemoveMove : "+this.agentsToRemoveMove.size());
		
//		for (Agent agent : this.agentsToRemoveMove) {
//			this.removeAgent(agent);
//		}
		
		for (Agent agent : this.agentsToRemove) {
			this.removeAgent(agent);
			agents.remove(agent);
		}
		
//		System.out.println("list after : "+agents.size());
		
		this.agentsToRemove.clear();
		this.agentsToRemoveMove.clear();
	}
	

	public void addToNextGeneration(Agent agent) {

		this.agentsToAdd.add(agent);
	}
	
	public void addPrey() {
		this.agentsToAdd.add(new Prey(this, this.preybreed, true));
	}
	
	public void addPredator() {
		this.agentsToAdd.add(new Predator(this, this.predatorbreed, this.starve, true));
	}
	
	public void freeAgent(Agent agent) {
		this.removeAgent(agent);
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
		
		return nbPred + " " + nbPrey;
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
		
		return ages;
	}

	public void writeData() {
		BufferedWriter bufWriter;
	    FileWriter fileWriter;
	    
		try {
			 fileWriter = new FileWriter("ages_prey_pred.txt", true);
		     bufWriter = new BufferedWriter(fileWriter);
	         bufWriter.write(this.getAges());
	         bufWriter.newLine();
	         bufWriter.close();
	         
	         fileWriter = new FileWriter("population_prey_pred.txt", true);
		     bufWriter = new BufferedWriter(fileWriter);
	         bufWriter.write(this.getNbPredPrey());
	         bufWriter.newLine();
	         bufWriter.close();
		} catch (IOException e) {}
	}
}
