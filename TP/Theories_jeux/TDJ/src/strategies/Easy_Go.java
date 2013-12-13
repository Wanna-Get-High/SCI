package strategies;

import Game.Player;

/**
 * 
 * Strategy : I betray my opponent until he cooperate and then i always cooperate.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Easy_Go extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		boolean lastAction = false;
		
		// to avoid going out of range (-1) for the first turn
		if (p1.getActions().size() > 0)
			lastAction = p2.getActions().get(p1.getActions().size()-1);
		
		if (p1.isCooperating()) {
			p1.addAction(true);
		} else {
			if (lastAction) {		// C
				p1.setIsCooperating();
				p1.addAction(true);
			} else { 				// D
				p1.addAction(false);
			}
		}
		
	}
}
