package Mvc.Views;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Mvc.Models.Sma;
import Mvc.support.Panel;

public class View implements Observer {
	
	JFrame window;
	ArrayList<Panel> panels;
	Sma sma;
	
	public View(Sma sma) {
		this.sma = sma;
		this.sma.addObserver(this);
		
		this.init();
	}
	
	public void init() {
		
		this.sma.getAgents();
		
		//this.window;
	}
	
	
	public void update(Observable o, Object arg) {
		
	}
	
}