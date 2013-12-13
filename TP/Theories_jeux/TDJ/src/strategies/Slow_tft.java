package strategies;

import Game.Player;


/**
 * 
 * Strategy : I cooperate 2 times then i always cooperate unless my opponent betrayed me at least once in the past 2 actions.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Slow_tft extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		if (p1.getActions().size() > 1) {
			int size = p1.getActions().size();
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
