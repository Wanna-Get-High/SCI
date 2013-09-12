package Mvc;

import Mvc.Models.Environment;
import Mvc.Models.Sma;

public class Main {

	static Sma sma;

	public static void main(String[] args) {
		Environment e = new Environment(0);
		
		sma = new Sma();
		
		sma.addAgent(e);

	}
	
}
