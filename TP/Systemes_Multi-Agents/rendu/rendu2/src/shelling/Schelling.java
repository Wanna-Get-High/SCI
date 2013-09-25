package shelling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.Agent;
import core.Environment;

public class Schelling extends Environment {
	
	public Schelling(int size) {
		super(size);
	}

	public void addAgentsTo(ArrayList<Agent> agents) {}

	public void removeAgentsTo(ArrayList<Agent> agents) {}
	
	public void writeData() {
		
		BufferedWriter bufWriter;
	    FileWriter fileWriter;
	    
		float satisfaction=0;
		int nAgent=0;
		
		for (int i=0; i<this.getSize(); i++)
			for(int j=0; j<this.getSize(); j++) {
				if(this.getAgents()[i][j]!=null) {
					nAgent++;
					People people = (People)this.getAgents()[i][j];
					satisfaction+=people.satisfaction();
				}
			}
			
		satisfaction=satisfaction/nAgent;
		
		try {
			 fileWriter = new FileWriter("Schelling.txt", true);
		     bufWriter = new BufferedWriter(fileWriter);
	         bufWriter.write(String.valueOf(satisfaction));
	         bufWriter.newLine();
	         bufWriter.close();
		} catch (IOException e) {}
	}

}
