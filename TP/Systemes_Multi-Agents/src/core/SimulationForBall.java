package core;

import java.util.ArrayList;

import view.EnvironmentRepresentation;

import model.Agent;
import model.Ball;
import model.Environment;
import model.MultiAgentSystem;

/**
 * The main class. <b>
 * It runs the simulation based on 4 parameters : <b>
 * the environment size, the number of agent, the number of turns and the delay between each turn. <b>
 * <b>
 * java Simulation.class <environment size> <number of agent> <number of turns> <delay between each turn>
 * 
 * @author Francois Lepan - Alexis Linke
 *
 */
public class SimulationForBall {

	/**
	 * prints the usage of this class if the 4 parameters aren't valid or present
	 */
	public void usage() {
		System.out.println("Usage :");
		System.out.println("java "+this.getClass().getCanonicalName()+" <environment size> <number of agent> <number of turns> <delay between each turn>");
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		SimulationForBall s = new SimulationForBall();
		
		int size = 0;
		int nbAgent = 0;
		int nbTurn = 0;
		int delay = 0;
		
		if (args.length < 4) {
			s.usage();
		} else {
			
			try {
				size = Integer.valueOf(args[0]);
				nbAgent = Integer.valueOf(args[1]);
				nbTurn = Integer.valueOf(args[2]);
				delay = Integer.valueOf(args[3]);
				
			} catch (NumberFormatException e) {
				System.out.println("the arguments has to be integers");
			}
			
			// check if the number of Agents is < to the number of available places.
			if (nbAgent > size*size) {
				System.out.println("You cannot have more Agents than the number of place : nbAgent < envSize^2");
				return;
			}
			
			// we create the environment
			Environment env = new Environment(size);
			
			// we create the agents
			ArrayList <Agent> agents = new ArrayList<Agent>();
			for (int i = 0; i < nbAgent; i++) agents.add(new Ball(env, i+1));
			
			// then we create the Model
			MultiAgentSystem mas = new MultiAgentSystem(env, agents);
			
			// we create the view
			new EnvironmentRepresentation(mas);
			
			// then we run the simulation
			mas.run(nbTurn,delay);
		}
	}
}
