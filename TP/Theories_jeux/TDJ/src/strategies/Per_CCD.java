package strategies;

import Game.Player;


/**
 * 
 * Strategy : I periodically use CCD Strategy  
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Per_CCD extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {

		int size = p1.getActions().size();
		
		if (size == 0) {
			p1.addAction(true);
		} else if ((size+1) % 3 == 0) {
			p1.addAction(false);
		} else {
			p1.addAction(true);
		}
		
	}

}
