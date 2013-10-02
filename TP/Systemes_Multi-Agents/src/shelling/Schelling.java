package shelling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import core.Environment;


/**
 * This is the environment for the Schelling method.
 * 
 * @author Francois Lepan, Alexis Linke
 */
public class Schelling extends Environment {
	
	public Schelling(int size) {
		super(size);
	}
	
	public void writeData() {
		
		BufferedWriter bufWriter;
	    FileWriter fileWriter;
	    
		float satisfaction=0;
		int nAgent=0;
		
		for (int i=0; i<this.getSize(); i++)
			for(int j=0; j<this.getSize(); j++) {
				if(this.getAgentsSpace()[i][j]!=null) {
					nAgent++;
					People people = (People)this.getAgentsSpace()[i][j];
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
