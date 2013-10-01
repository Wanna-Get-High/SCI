package fiveormore;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import core.Agent;
import core.Environment;
import core.MultiAgentSystem;

/**
 * The MAS specialized for the game five or more.<br>
 * <br>
 * It adds as post processing : <br>
 * 	- check if the game board is full, <br>
 *  - let the human ask for a move to the Player Agent, <br>
 * 	- remove agents if there are lines of 5 or more token of the same color,<br>
 *  - add 3 random token.
 * 
 * @author Francois Lepan, Alexis Linke
 */
public class MAS_FiveOrMore extends MultiAgentSystem {

	/** The Agent that will try to move some token asked by the human */
	Player player;
	
	public MAS_FiveOrMore(Environment env, ArrayList<Agent> agents, Player player) {
		super(env, agents);
		this.player = player;
	}

	@Override
	public void postProcessing() {
		
		// we find out if the gameboard is full
		if (this.isGameBoardFull()) {
			JOptionPane.showMessageDialog(null, "game over");
			System.exit(0);
		}
		
		// we let the player do something
		this.player.doAction();
				
		// we remove the agents
		this.removeAgents();
		
		// we add the 3 token
		this.addToken(3);
		
		//printTable();
	}

	/**
	 * Go through all of the Agent table and check if there is an empty space.
	 * 
	 * @return true if there is an empty space.
	 */
	private boolean isGameBoardFull() {
		int size = this.environment.getSize();
	
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.environment.getAgentAt(i, j) == null) return false;
			}
		}
		
		return true;
	}

	/**
	 * Add some token at random place in the environment
	 * 
	 * @param nbToken the number of token to be added
	 */
	private void addToken(int nbToken) {
		
		for (int i = 0; i < nbToken; i++) {
			// we create a token that isn't placed in the environement 
			Token token = new Token(this.environment, ((Plan)this.environment).getRandomColor(), false);
			
			// we try to get a place for this token
			// if doesn't get one we don't add it to the agents list 
			if (this.environment.getPlace(token)) this.agents.add(token);
		}
		
	}

	/**
	 * We remove the agents from the environment and the space 
	 * depending on the table agentsToBeRemoved of the class Plan.
	 */
	private void removeAgents() {
		// the table that contains the agent to be removed
		boolean[][] agentsToRemove = ((Plan)this.environment).getAgentsToBeRemoved();
		
		// the agents in the space of the environement
		Agent[][] agents = this.environment.getAgentsSpace();
		
		int size = this.environment.getSize();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// if the value at this point is true then 
				if (agentsToRemove[i][j]) {
					// remove from the list
					this.agents.remove(agents[i][j]);
					// remove from the table
					this.environment.removeAgent(agents[i][j]);
				}
			}
		}
		
		// reset the values
		((Plan)this.environment).resetAgentsToBeRemoved();
	}

	/**
	 * print the boolean table that contains the agent to be removed.
	 */
	public void printTable() {
		
		boolean[][] agentsToRemove = ((Plan)this.environment).getAgentsToBeRemoved();
		int size = this.environment.getSize();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(agentsToRemove[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("=============================");
	}
	
	
	
}