package strategies;

import Game.Player;

/**
 * 
 * Strategy : I periodically use DC Strategy  
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Per_DC extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
	
		int size = p1.getActions().size();
		
		if (size % 2 == 0) {
			p1.addAction(false);
		} else {
			p1.addAction(true);
		}

	}

}
