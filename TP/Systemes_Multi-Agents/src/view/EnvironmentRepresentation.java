package view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

import model.MultiAgentSystem;

/**
 * This class is the view of the MVC.<b>
 * It displays the Agents moving and acting in the environment.
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public abstract class EnvironmentRepresentation extends JFrame implements Observer {

	/** */
	private static final long serialVersionUID = -2889322164507336236L;

	/** The model containing the data */
	protected MultiAgentSystem mas;

	public EnvironmentRepresentation(MultiAgentSystem mas) {
		this.mas = mas;
		
		this.init();
	}
	
	/**
	 * Initialize the view and add the this class as an observer of the model Sma. 
	 */
	protected abstract void init() {
		
		// subscribe to the model 
		this.mas.addObserver(this);

		this.setSize(500,500);
		
		// add the panel that draw the balls to this JFrame
		this.add(new BallsPanel(this.mas.getEnvironment().getAgents()));
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Add the  
	 */
	protected abstract void addPanel(); 

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
}