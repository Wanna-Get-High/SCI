package Mvc.Models;

import java.util.ArrayList;
import java.util.Observable;

public class Sma extends Observable {

	Environment e;
	ArrayList<Agent> a;
	
	public Sma() {
		e=new Environment(0);
		a=new ArrayList<Agent>();
	}
	
	public void addAgent(Environment env) {
		
		a.add(new Agent(env));
	}
}