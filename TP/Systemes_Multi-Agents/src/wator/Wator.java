package wator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.Agent;
import core.Environment;


/**
 * This is the environment for the Prey-Predator Simulation.<br>
 * It handle 2 lists of agent that contains agent to be added or removed at the end of each turn. <br>
 * It also write data to 2 file for graphic view.
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Wator extends Environment {

	/** The file writer used to write the ages in a file */
	private FileWriter ageFileWriter;

	/** The file writer used to write the number of predator and prey in a file */
	private FileWriter nbPredPreyFileWriter;

	/** This list contains the new agent after a cycle */
	protected ArrayList<Agent> agentsToAdd;

	/** This list contains the agents that are dead and has to be removed */
	protected ArrayList<Agent> agentsToRemove;

	/**
	 * The basic constructor.<br>
	 * It initialize the file that will contain the data <br>
	 * and the arrayList of agent to be added and removed.
	 * 
	 * @param size the size of the Environment
	 */
	public Wator(int size) {
		super(size);

		// we delete data from the files
		try {
			this.nbPredPreyFileWriter = new FileWriter("population_prey_pred.txt", false);
			this.ageFileWriter = new FileWriter("ages_prey_pred.txt", false);
		} catch (IOException e) {
			System.err.println("could not open the file : population_prey_pred.txt or ages_prey_pred.txt");
		}

		this.agentsToAdd = new ArrayList<Agent>();
		this.agentsToRemove = new ArrayList<Agent>();		
	}


	/**
	 * This method is called by the MAS of this environment with its list of agent as parameters.<br>
	 * It will add some agents to this list.
	 * 
	 * @param agents the list of agent in which we will add Agent.
	 */
	public void addAgentsTo(ArrayList<Agent> agents) {

		for (Agent agent : this.agentsToAdd) {
			if(this.getPlace(agent)) {
				agents.add(agent);
			}
		}

		// empty the list
		this.agentsToAdd.clear();		
	}

	/**
	 * Remove agents from the Global List that contain all of the agent of the MAS.<br>
	 * <br>
	 * Only remove the predator from the board because the prey places<br> 
	 * are already used by the predator that killed them. ( Predator.move )
	 * 
	 * @param agents the list of agent in which we will remove Agent.
	 */
	public void removeAgentsTo(ArrayList<Agent> agents) {

		for (Agent agent : this.agentsToRemove) {
			// only remove the predator from the board because the prey places 
			// are already used by the predator that killed them
			if ( agent.getClass().equals(Predator.class)) this.removeAgent(agent);

			// remove from the list
			agents.remove(agent);
		}

		// empty the list
		this.agentsToRemove.clear();
	}

	/**
	 * Add an agent to the list that will be added to the global list contained in the MAS.
	 * 
	 * @param agent the agent to be added
	 */
	public void addAgentToList(Agent agent) { this.agentsToAdd.add(agent); }

	/**
	 * Add an agent to the list that will be removed from the global list contained in the MAS.
	 * 
	 * @param agent the agent to be removed.
	 */
	public void removeAgentFromList(Agent agent) { this.agentsToRemove.add(agent); }


	/**
	 * Get the number of predator and prey currently in the Environement
	 * 
	 * @return a String that contain : "nbPredator nbPrey"
	 */
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

	/**
	 * Get the ages of all of the Agents.
	 * 
	 * @return a String that contain : "Agent.age()  Agent1.age() ..."
	 */
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

	/**
	 * Write the number of prey - pred to a file and the ages to another one.<br>
	 * This is used to make graphics.
	 */
	public void writeData() {
		BufferedWriter bufWriter;

		// we write at the end of the files
		try {
			this.ageFileWriter = new FileWriter("ages_prey_pred.txt", true);
			bufWriter = new BufferedWriter(this.ageFileWriter);
			bufWriter.write(this.getAges());
			bufWriter.newLine();
			bufWriter.close();

			this.nbPredPreyFileWriter = new FileWriter("population_prey_pred.txt", true);
			bufWriter = new BufferedWriter(this.nbPredPreyFileWriter);
			bufWriter.write(this.getNbPredPrey());
			bufWriter.newLine();
			bufWriter.close();

		} catch (IOException e) {
			System.err.println("could not open the file : population_prey_pred.txt or ages_prey_pred.txt");
		}
	}
}
