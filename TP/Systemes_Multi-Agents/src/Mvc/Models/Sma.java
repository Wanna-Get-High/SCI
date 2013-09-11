package Mvc.Models;

import java.util.ArrayList;
import java.util.Observable;

public class Sma extends Observable {

	Environnement e;
	ArrayList<Agent> a;
	
	public Sma() {
		e=new Environnement();
		a=new ArrayList<Agent>();
	}
	
	public void addAgent(Environnement env) {
		a.add(new Agent(env));
	}
}
