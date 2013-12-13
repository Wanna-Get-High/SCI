package strategies;

import Game.Player;

/**
 * Strategy : I always cooperate.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class All_C extends Strategy {
	
	@Override
	public void resolve(Player p1, Player p2) {
		p1.addAction(true);
	}
}
