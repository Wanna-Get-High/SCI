package fiveormore;

import java.awt.Color;
import java.util.ArrayList;

import core.Agent;
import core.Environment;

/**
 * This is an actor for the five or more game.<br>
 * <br>
 * It checks if there are 5 or more in a line of his kind :<br>
 *  - top right,<br>
 *  - right,<br>
 *  - bottom right,<br>
 *  - bottom.<br>
 * 
 * @author Francois Lepan, Alexis Linke
 *
 */
public class Token extends Agent {

	public Token(Environment env, Color color, boolean getAPlace) {
		super(env);
		
		this.color = color;
		
		if (getAPlace) this.environment().getPlace(this);
	}

	@Override
	public void doAction() {
		
		Agent[][] agents = this.environment.getAgentsSpace();
		int environmentSize = this.environment.getSize();
		
		// top right 
		this.processNeightbourAgents(-1, 1, agents, environmentSize);
		
		// right
		this.processNeightbourAgents(0, 1, agents, environmentSize);
		
		// bottom right
		this.processNeightbourAgents(1, 1, agents, environmentSize);
		
		// bottom
		this.processNeightbourAgents(1, 0, agents, environmentSize);
	}
	
	/**
	 * This method is used to check if a line of 5 agent of the same color than this one.<br>
	 * it goes in the direction specified by the step. 
	 * 
	 * @param xStep the step on the x axis
	 * @param yStep the step on the y axis
	 * @param agents the table of agent
	 * @param environmentSize the size of the environment
	 */
	private void processNeightbourAgents(int xStep, int yStep, Agent[][] agents, int environmentSize) {
		// initialize the indexes
		int currentXIndex = this.x + xStep; 
		int currentYIndex = this.y + yStep;
		
		// initialize the list and the countedAgent value to 1 (himself)
		int countedAgent = 1;
		ArrayList<Agent> agentsToRemove = new ArrayList<Agent>();
		
		// check if the new indexes are in the range of the table
		// then the existence of an agent at the new indexes place
		// then the color
		while(	currentXIndex >= 0 && currentXIndex < environmentSize 
				&& currentYIndex >= 0 && currentYIndex < environmentSize 
				&& agents[currentXIndex][currentYIndex] != null
				&& agents[currentXIndex][currentYIndex].color() == this.color ) {
			
			// we add the agent to the agents to remove
			agentsToRemove.add(agents[currentXIndex][currentYIndex]);
			
			countedAgent++;
			currentXIndex += xStep;
			currentYIndex += yStep;
		}
		
		// if there is 5 or more agent in line,
		// put true in the table at the place of the agents
		if (countedAgent >= 5) {
			
			agentsToRemove.add(this);
			
			for (Agent agent : agentsToRemove) {
				((Plan)this.environment).setAgentToBeRemoved(agent.x(),agent.y());
			}
		}
		
		// set to null for garbage collector
		agentsToRemove.clear();
		agentsToRemove = null;
	}
}
