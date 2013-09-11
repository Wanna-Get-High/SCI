package Mvc;

import Mvc.Models.Environnement;
import Mvc.Models.Sma;

public class Main {

	static Sma sma;

	public static void main(String[] args) {
		Environnement e = new Environnement();
		
		sma = new Sma();
		
		sma.addAgent(e);

	}

}
