package Mvc.Views;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import Mvc.Models.Sma;

public class View implements Observer {
	
	JFrame window;
	Sma sma;
	
	public View(Sma sma) {
		this.sma = sma;
	}
	
	public void update(Observable o, Object arg) {
		
	}
	
}