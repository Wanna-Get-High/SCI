package core;

import java.awt.Color;
import java.util.ArrayList;

import fiveormore.Player;
import fiveormore.MAS_FiveOrMore;
import fiveormore.Plan;
import fiveormore.Token;

import particules.Ball;
import particules.MAS_Particules;

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
 * The main class. <br>
 * It runs the simulation based on one or more parameters.<br>
 * <br>
 * Examples :<br>
 * 
 * <br>
 * For the ball:
 * <br><dd>
 * java core.Simulation -b 100 50 -1 5<br>
 * java core.Simulation -b 10 5 100 5<br>
 * java core.Simulation -b 50 40 -1 5<br>
 * </dd><br>
 * For Schelling :<br>
 * <br><dd>
 * java core.Simulation -s 100 9750 0.3 -1 1<br>
 * java core.Simulation -s 100 9750 0.6 -1 1<br>
 * java core.Simulation -s 50 2000 0.7 -1 100<br>
 * </dd><br>
 * For Wator:<br>
 * <br><dd>
 * java core.Simulation -w 50 1040 326 4 10 6 -1 50<br>
 * java core.Simulation -w 10 10 5 4 10 6 -1 50<br>
 * java core.Simulation -w 35 100 30 4 10 6 -1 50<br>
 * </dd><br>
 *  For the 5 or more game:<br>
 * <br><dd>
 * java core.Simulation -f<br>
 * </dd><br>
 * @author Francois Lepan - Alexis Linke
 */
public class Simulation {

	/**
	 * Prints the usage of this class if the 4 parameters aren't valid or present
	 */
	public void usage() {
		System.out.println("Usage :");
		System.out.println("\tfor the balls :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -b <environment size> <nb agent> <nb turns> <delay between each turn>");
		System.out.println("\tfor the prey - predator :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -w <environment size> <nb fish> <nb shark> <prey breed> <predator breed> <starve delay> <number of turns> <delay between each turn>");
		System.out.println("\tfor the segregation :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -s <environment size> <nb people> <threshold> <number of turns> <delay between each turn>");
		System.out.println("\tfor the 5 or more :");
		System.out.println("\t\tjava "+this.getClass().getCanonicalName()+" -f");
		System.out.println("");
	}

	
	public static void main(String[] args) {

		Simulation s = new Simulation();

		int size = 0;
		int nbAgent = 0;
		int numberOfPrey = 0;
		int numberOfPredator = 0;
		int nbTurn = 0;
		int delay = 0;
		int spawnPreyCycle = 0;
		int spawnPredatorCycle = 0;
		int maximumStarvingValue = 0;
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
				Environment env = new Environment(size);

				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i = 0; i < nbAgent; i++) agents.add(new Ball(env));

				// then we create the Model
				MultiAgentSystem mas = new MAS_Particules(env, agents);

				// we create the view
				DrawingPanel panel = new DrawingPanel(env.getAgentsSpace(), false, false);
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
					numberOfPrey = Integer.valueOf(args[2]);
					numberOfPredator = Integer.valueOf(args[3]);
					spawnPreyCycle = Integer.valueOf(args[4]);
					spawnPredatorCycle = Integer.valueOf(args[5]);
					maximumStarvingValue = Integer.valueOf(args[6]);
					nbTurn = Integer.valueOf(args[7]);
					delay = Integer.valueOf(args[8]);

				} catch (NumberFormatException e) {
					System.out.println("the arguments has to be integers");
				}

				// check if the number of Agents is < to the number of available places.
				if (numberOfPrey+numberOfPredator > size*size) {
					System.out.println("You cannot have more Agents than the number of place : nbAgent < envSize^2");
					return;
				}
				
				// we create the environment
				Wator wat = new Wator(size);

				// we create the agents
				ArrayList <Agent> agents = new ArrayList<Agent>();
				for (int i=0; i < numberOfPrey; i++) agents.add(new Prey(wat, spawnPreyCycle, true));
				for (int i=0; i < numberOfPredator; i++) agents.add(new Predator(wat, spawnPredatorCycle, maximumStarvingValue, true));

				// then we create the Model
				MultiAgentSystem mas = new MAS_Wator(wat, agents);

				// we create the view
				DrawingPanel panel = new DrawingPanel(wat.getAgentsSpace(), false, false);
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

				// check if the number of Agents is < to the number of available places.
				if (nbAgent > size*size) {
					System.out.println("You cannot have more Agents than the number of place : nbAgent < envSize^2");
					return;
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
				DrawingPanel panel = new DrawingPanel(sch.getAgentsSpace(), false, false);
				new EnvironmentRepresentation(mas, panel);

				// then we run the simulation
				mas.run(nbTurn,delay);
			}
		} else if("-f".equals(args[0])) {
			
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
			DrawingPanel panel = new DrawingPanel(plan.getAgentsSpace(), true, true);
			new EnvironmentRepresentation(mas, panel);

			// then we run the simulation
			mas.run(-1,50);
		}else {
			s.usage();
		}
	}
}
