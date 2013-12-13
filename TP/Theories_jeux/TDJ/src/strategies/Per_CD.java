package strategies;

import Game.Player;


/**
 * 
 * Strategy : I periodically use CD Strategy  
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Per_CD extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		int size = p1.getActions().size();
		
		if (size % 2 == 0) {
			p1.addAction(true);
		} else {
			p1.addAction(false);
		}
		
	}

}
