package Mvc;

import java.util.ArrayList;
import Mvc.Models.Agent;
import Mvc.Models.Environment;
import Mvc.Models.Sma;

public class Simulation {

	public void usage() {
		System.out.println("Usage :");
		System.out.println("java Simulation.java <environment size> <number of agent> <number of turns>");
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Simulation s = new Simulation();
		int size = 0;
		int nbAgent = 0;
		int nbTurn = 0;
		
		
		if (args.length < 3) {
			s.usage();
		} else {
			
			try {
				size = Integer.valueOf(args[0]);
				nbAgent = Integer.valueOf(args[1]);
				nbTurn = Integer.valueOf(args[2]);
				
			} catch (NumberFormatException e) {
				System.out.println("the arguments has to be integers");
			}
			
			Environment env = new Environment(size);
			
			ArrayList <Agent> agents = new ArrayList<Agent>();
			
			for (int i = 0; i < nbAgent; i++) {
				agents.add(new Agent(env, i+1));
			}
			
			Sma sma = new Sma(env, agents);
			
			sma.run(nbTurn);
		}
	}
}
