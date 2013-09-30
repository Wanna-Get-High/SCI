package fiveormore;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import core.Agent;
import core.Environment;

public class Plan extends Environment {

	private Color[] colors = { Color.BLUE, Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.YELLOW, Color.ORANGE };
	
	public Plan(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addAgentsTo(ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAgentsTo(ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeData() {
		// TODO Auto-generated method stub
		
	}
	
	public Color[] colors() { return this.colors; }

}
