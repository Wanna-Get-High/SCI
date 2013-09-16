package Mvc.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

import Mvc.Views.View;


public class Sma extends Observable {

	public Environment environment;
	ArrayList<Agent> agents;
		
	public Sma(Environment env, ArrayList<Agent> agents) {
		this.environment = env;
		this.agents = agents;
	}
	
	public void run(int nbOfTurn) {
		
		this.printTable();
		
		for (int i = 0; i < nbOfTurn; i++) {
			Collections.shuffle(this.agents);
			
			for (Agent agent : this.agents) {
				agent.doAction();
				this.setChanged();
			}

			this.notifyObservers();
			this.printTable();
			
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void printTable() {
		int size = this.environment.getSize();
		Agent[][] agents = this.environment.getAgents();
				
		String separator = "";
		for (int i = 0; i < size ; i++) {
			for (int j = 0; j < size; j++) {
				if (agents[i][j] == null) {
					System.out.print(0 + " ");
				} else {
					System.out.print(agents[i][j].id() + " ");
				}
			}
			System.out.println();
			separator += "==";
		}
		
		System.out.println(separator);
	}
}