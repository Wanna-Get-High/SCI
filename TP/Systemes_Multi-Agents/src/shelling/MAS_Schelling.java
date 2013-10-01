package shelling;

import java.util.ArrayList;

import core.Agent;
import core.Environment;
import core.MultiAgentSystem;

public class MAS_Schelling extends MultiAgentSystem {

	public MAS_Schelling(Environment env, ArrayList<Agent> agents) {
		super(env, agents);
	}

	@Override
	public void postProcessing() {
		((Schelling)this.environment).writeData();
	}
}