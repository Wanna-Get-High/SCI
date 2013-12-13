package strategies;

import Game.Player;

/**
 * 
 * Strategy : I always cooperate unless my opponent betrayed me once in the past 2 actions.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Hard_tft extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		int size = p1.getActions().size();
		
		if (size == 1) {
			if (!p2.getActions().get(0)) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
		} else if (size > 1) {
			if (!p2.getActions().get(size-1) || !p2.getActions().get(size-2)) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
		} else {
			p1.addAction(true);
		}
	}
	
}
