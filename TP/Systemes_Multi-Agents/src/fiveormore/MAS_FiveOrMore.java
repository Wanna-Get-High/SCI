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
		
		// we remove the agents
		this.removeAgents();
		
		// we refresh the view
		this.setChanged();
		this.notifyObservers();
		
		// we let the player do something
		this.player.doAction();
				
		// we add the 3 token
		this.addToken(3);
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
		((Plan)this.environment).addToken(nbToken, this.agents);
	}

	/**
	 * We remove the agents from the environment and the space 
	 * depending on the table agentsToBeRemoved of the class Plan.
	 */
	private void removeAgents() {
		((Plan)this.environment).removeAgentsFrom(this.agents);
	}
	
}