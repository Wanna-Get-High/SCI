package strategies;

import Game.Player;

/**
 * This class is the abstract class for each strategy.
 * 
 * 	It contains the main method that has to be implemented by each strategy resolve.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public abstract class Strategy {

	/**
	 * Confront the two current strategy used by the two player.
	 * 
	 * @param p1 the first player.
	 * @param p2 the second player.
	 */
	public abstract void resolve(Player p1, Player p2);
}
