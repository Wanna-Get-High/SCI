package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;


/**
 * This class is the Model of the MVC.
 * 
 * It contains the Environment and the Agents that will move inside.
 * The main method of this class is Run(int nbOfTurn, int delay).
 * 
 * @author Alexis Linke - Francois Lepan
 * 
 */
public class MultiAgentSystem extends Observable {

	/** The environment */
	private Environment environment;
	
	/** The list of Agents */
	private ArrayList<Agent> agents;
		
	
	public MultiAgentSystem(Environment env, ArrayList<Agent> agents) {
		this.environment = env;
		this.agents = agents;
	}
	
	/**
	 * The main method. <b>
	 * <b>
	 * It loop on all the agent and ask them to do something for a certain amount of time.
	 * 
	 * @param nbOfTurn the number of turns that the Agent will do something. <b> 
	 * 		  If -1 then it will loop until the application is closed.
	 * @param delay the delay between each turn (in milliseconds).
	 * 
	 */
	public void run(int nbOfTurn, int delay) {

		int i = 0;
		
		while (nbOfTurn == -1 || i < nbOfTurn) {
		
			// shuffle the collection of agent (for more "equality")
			Collections.shuffle(this.agents);
			
			for (Agent agent : this.agents) agent.doAction();
			
			this.environment.removeAgentsTo(this.agents);
			this.environment.addAgentsTo(this.agents);

			// telling that this model has changed
			// then notify its views
			this.setChanged();
			this.notifyObservers();

			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				System.err.println("This Thread was interrupted but it wasn't supposed to");
				e.printStackTrace();
			}
			
			i++;
		}
	}

	/**
	 * Gets the environment of this SMA.
	 * 
	 * @return the environment of this SMA
	 */
	public Environment getEnvironment() { return this.environment; }
	
	/**
	 * Gets the List of Agent.
	 * 
	 * @return an ArrayList containing all of the Agent of the environment
	 */
	public ArrayList<Agent> getAgents() { return this.agents; }
}