package wator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.Agent;
import core.Environment;


/**
 * This is the environment for the Prey-Predator Simulation.<br>
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Wator extends Environment {

	/** This list contains the new agent after a cycle */
	protected ArrayList<Agent> agentsToAdd;
	
	/** This list contains the agents that are dead and has to be removed */
	protected ArrayList<Agent> agentsToRemove;

	
	public Wator(int size) {
		super(size);

		this.agentsToAdd = new ArrayList<Agent>();
		this.agentsToRemove = new ArrayList<Agent>();		
	}
	
	
	public void addAgentsTo(ArrayList<Agent> agents) {
		
		for (Agent agent : this.agentsToAdd) {
			if(this.getPlace(agent)) {
				agents.add(agent);
			}
		}
		
		this.agentsToAdd.clear();		
	}

	
	public void removeAgentsTo(ArrayList<Agent> agents) {

		for (Agent agent : this.agentsToRemove) {
			// only remove the predator from the board because the prey places 
			// are already used by the predator that killed them
			if ( agent.getClass().equals(Predator.class)) this.removeAgent(agent);
			
			agents.remove(agent);
		}

		this.agentsToRemove.clear();
	}
	
	public void addAgentToList(Agent agent) {
		this.agentsToAdd.add(agent);
	}
	
	public void removeAgentFromList(Agent agent) {
		this.agentsToRemove.add(agent);
	}
	
	
	public String getNbPredPrey() {
		int nbPred = 0;
		int nbPrey = 0;
		
		for (int i = 0; i< this.getSize(); i++) {
			for (int j = 0; j< this.getSize(); j++) {
				Agent agent = this.getAgentsSpace()[i][j];
				
				if (agent != null) {
					if (agent.getClass().equals(Prey.class)) {
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
				Agent agent = this.getAgentsSpace()[i][j];
				
				if (agent != null) {
					if (agent.getClass().equals(Prey.class)) {
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
