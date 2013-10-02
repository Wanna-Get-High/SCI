package shelling;

import java.util.ArrayList;

import core.Agent;
import core.Environment;
import core.MultiAgentSystem;

/**
 * This is the MAS for the Schelling method.
 * 
 * It adds as post processing the writing of the data.
 * 
 * @author Francois Lepan, Alexis Linke
 *
 */
public class MAS_Schelling extends MultiAgentSystem {

	public MAS_Schelling(Environment env, ArrayList<Agent> agents) {
		super(env, agents);
	}

	@Override
	public void postProcessing() {
		((Schelling)this.environment).writeData();
	}
}