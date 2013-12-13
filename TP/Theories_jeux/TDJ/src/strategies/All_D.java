package strategies;

import Game.Player;

/**
 * Strategy : I always betray my opponent.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class All_D extends Strategy {
	
	@Override
	public void resolve(Player p1, Player p2) {
		p1.addAction(false);
	}
	
}
