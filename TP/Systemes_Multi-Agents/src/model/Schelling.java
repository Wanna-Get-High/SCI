package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Schelling extends Environment {
	
	BufferedWriter bufWriter;
    FileWriter fileWriter;
	
	public Schelling(int size) {
		super(size);
	}

	public void addAgentsTo(ArrayList<Agent> agents) {}

	public void removeAgentsTo(ArrayList<Agent> agents) {}
	
	public void writeData() {
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
