package wator;

import java.util.ArrayList;

import core.Agent;
import core.MultiAgentSystem;

public class MAS_Wator extends MultiAgentSystem {
	
	public MAS_Wator(Wator env, ArrayList<Agent> agents) {
		super(env, agents);
	}

	@Override
	public void postProcessing() {
		
		((Wator)this.environment).removeAgentsTo(this.agents);			
		((Wator)this.environment).addAgentsTo(this.agents);
		((Wator)this.environment).writeData();
		
	}

}
