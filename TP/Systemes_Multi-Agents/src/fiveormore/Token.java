package fiveormore;

import java.awt.Color;

import core.Agent;
import core.Environment;

public class Token extends Agent {

	public Token(Environment env, Color color) {
		super(env);
		this.color=color;
		this.environment().getPlace(this);
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
	}

}
