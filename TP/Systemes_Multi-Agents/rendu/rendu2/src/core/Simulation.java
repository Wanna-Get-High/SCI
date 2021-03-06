package core;

import java.awt.Color;
import java.util.ArrayList;

import particules.Ball;
import particules.Particules;
import shelling.People;
import shelling.Schelling;
import view.EnvironmentRepresentation;
import wator.Predator;
import wator.Prey;
import wator.Wator;

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
public class Simulation {

	/**
	 * prints the usage of this class if the 4 parameters aren't valid or present
	 */
	public void usage() {
		System.out.println("Usage :");
		System.out.println("\tfor the balls :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -b <environment size> <nb agent> <nb turns> <delay between each turn>");
		System.out.println("\tfor the prey - predator :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -w <environment size> <nb fish> <nb shark> <prey breed> <predator breed> <starve delay> <number of turns> <delay between each turn>");
		System.out.println("\tfor the segregation :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -s <environment size> <nb people> <threshold> <number of turns> <delay between each turn>");
		
		System.out.println("");
	}
	
	public static void main(String[] args) {
		
		Simulation s = new Simulation();
		
		int size = 0;
		int nbAgent = 0;
		int nfish = 0;
		int nsharks = 0;
		int nbTurn = 0;
		int delay = 0;
		int preybreed = 0;
		int predatorbreed = 0;
		int starve = 0;
		int nbPeople = 0;
		float threshold = 0;
		
		if(args[0].equals("-b")) {
			if (args.length < 4) {
				s.usage();
			} else {
				
				try {
					size = Integer.valueOf(args[1]);
					nbAgent = Integer.valueOf(args[2]);
					nbTurn = Integer.valueOf(args[3]);
					delay = Integer.valueOf(args[4]);
					
				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers");
				}
				
				// check if the number of Agents is < to the number of available places.
				if (nbAgent > size*size) {
					System.out.println("You cannot have more Agents than the number of place : nbAgent < envSize^2");
					return;
				}
				
				// we create the environment
				Environment env = new Particules(size);
				
				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i = 0; i < nbAgent; i++) agents.add(new Ball(env));
				
				// then we create the Model
				MultiAgentSystem mas = new MultiAgentSystem(env, agents);
				
				// we create the view
				new EnvironmentRepresentation(mas);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
			} 
		} else if(args[0].equals("-w")) {
			
				try {
					size = Integer.valueOf(args[1]);
					nfish = Integer.valueOf(args[2]);
					nsharks = Integer.valueOf(args[3]);
					preybreed = Integer.valueOf(args[4]);
					predatorbreed = Integer.valueOf(args[5]);
					starve = Integer.valueOf(args[6]);
					nbTurn = Integer.valueOf(args[7]);
					delay = Integer.valueOf(args[8]);
					
				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers");
				}
				
				// we create the environment
				Wator wat = new Wator(size, preybreed, predatorbreed, starve);
				
				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i=0; i < nfish; i++) agents.add(new Prey(wat,preybreed ));
				for (int i=0; i < nsharks; i++) agents.add(new Predator(wat, predatorbreed, starve));
				
				// then we create the Model
				MultiAgentSystem mas = new MultiAgentSystem(wat, agents);
				
				// we create the view
				new EnvironmentRepresentation(mas);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
				
		} else if(args[0].equals("-s")) {
			
				try {
					size = Integer.valueOf(args[1]);
					nbPeople = Integer.valueOf(args[2]);	
					threshold = Float.valueOf(args[3]);
					nbTurn = Integer.valueOf(args[4]);
					delay = Integer.valueOf(args[5]);
				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers");
					System.exit(-1);
				}
				
				// we create the environment
				Schelling sch = new Schelling(size);
				
				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i=0; i < nbPeople; i++) 
					if(i%2 == 0)
						agents.add(new People(sch,Color.BLUE,threshold));
					else
						agents.add(new People(sch,Color.YELLOW,threshold));
						
				// then we create the Model
				MultiAgentSystem mas = new MultiAgentSystem(sch, agents);
				
				// we create the view
				new EnvironmentRepresentation(mas);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
				
		}else {
			s.usage();
		}
	}
}
