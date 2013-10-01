package core;

import java.awt.Color;
import java.util.ArrayList;

import fiveormore.Player;
import fiveormore.MAS_FiveOrMore;
import fiveormore.Plan;
import fiveormore.Token;

import particules.Ball;
import particules.MAS_Particules;
import particules.Particules;
import shelling.MAS_Schelling;
import shelling.People;
import shelling.Schelling;
import view.DrawingPanel;
import view.EnvironmentRepresentation;
import wator.MAS_Wator;
import wator.Predator;
import wator.Prey;
import wator.Wator;


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
	
//	public static void main(String[] args) {
//		ArrayList<Integer> ai = new ArrayList<Integer>();
//		
//		Integer[] p = new Integer[2];
//		int neighborStep[][] = { {-1,-1}, {-1,1}, {-1,0}, {0,1}, {0,-1}, {1,-1}, {1,1}, {1,0}};
//		
//		for (int i = 0; i < neighborStep.length; i++)
//			System.out.println(neighborStep[i][0]+ " "+ neighborStep[i][1]);
//	}
	
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
		
		if("-b".equals(args[0])) {
			if (args.length < 4) {
				s.usage();
			} else {
				// we retrieve the needed arguments
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
				MultiAgentSystem mas = new MAS_Particules(env, agents);
				
				// we create the view
				DrawingPanel panel = new DrawingPanel(mas.getEnvironment().getAgentsSpace(), false, false);
				new EnvironmentRepresentation(mas, panel);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
			} 
		} else if("-w".equals(args[0])) {
			if (args.length < 8) {
				s.usage();
			} else {
				// we retrieve the needed arguments
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
				for (int i=0; i < nfish; i++) agents.add(new Prey(wat, preybreed, true));
				for (int i=0; i < nsharks; i++) agents.add(new Predator(wat, predatorbreed, starve, true));
				
				// then we create the Model
				MultiAgentSystem mas = new MAS_Wator(wat, agents);
				
				// we create the view
				DrawingPanel panel = new DrawingPanel(mas.getEnvironment().getAgentsSpace(), false, false);
				new EnvironmentRepresentation(mas, panel);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
			}
		} else if("-s".equals(args[0])) {
			if (args.length < 5) {
				s.usage();
			} else {
				// we retrieve the needed arguments
				try {
					size = Integer.valueOf(args[1]);
					nbPeople = Integer.valueOf(args[2]);	
					threshold = Float.valueOf(args[3]);
					nbTurn = Integer.valueOf(args[4]);
					delay = Integer.valueOf(args[5]);
				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers except the 3rd one that is a float");
					System.exit(-1);
				}
				
				// we create the environment
				Schelling sch = new Schelling(size);
				
				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i=0; i < nbPeople; i++) {
					if(i%2 == 0) 	agents.add(new People(sch,Color.BLUE,threshold));
					else 			agents.add(new People(sch,Color.YELLOW,threshold));
				}
				
				// then we create the Model
				MultiAgentSystem mas = new MAS_Schelling(sch, agents);
				
				// we create the view
				DrawingPanel panel = new DrawingPanel(mas.getEnvironment().getAgentsSpace(), false, false);
				new EnvironmentRepresentation(mas, panel);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
			}
		} else if("-f".equals(args[0])) {
			if (args.length < 3) {
				s.usage();
			} else {
				// we retrieve the needed arguments
				try {
					nbTurn = Integer.valueOf(args[1]);
					delay = Integer.valueOf(args[2]);
				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers");
					System.exit(-1);
				}
				
				// we create the environment
				Plan plan = new Plan(9);
				
				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i=0; i < 3; i++) {
					agents.add(new Token(plan,plan.getRandomColor(),true));
				}
				
				// then we create the Model
				MultiAgentSystem mas = new MAS_FiveOrMore(plan, agents, new Player(plan));
				
				// we create the view
				DrawingPanel panel = new DrawingPanel(mas.getEnvironment().getAgentsSpace(), true, true);
				new EnvironmentRepresentation(mas, panel);
				
				// then we run the simulation
				mas.run(nbTurn,delay);
			}
	}else {
			s.usage();
		}
	}
}
