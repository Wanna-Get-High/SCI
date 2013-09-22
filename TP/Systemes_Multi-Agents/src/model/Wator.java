package model;

/**
 * 
 * 
 * @author Alexis Linke - Francois Lepan
 *
 */
public class Wator extends Environment {

	/** Number of cycles a prey must exist before reproducing */
	private int preybreed;
	
	/** Number of cycles a predator must exist before reproducing */
	private int predatorbreed;
	
	/** Number of cycles a predator has to find food before starving */
	private int starve;
	
	public Wator(int size, int preybreed, int predatorbreed, int starve) {
		super(size);
		this.preybreed = preybreed;
		this.predatorbreed = predatorbreed;
		this.starve = starve;
	}
	
	public int preybreed() { return this.preybreed; }
	
	public int predatorbreed() { return this.predatorbreed; }
	
	public int starve() { return this.starve; }
 
}
