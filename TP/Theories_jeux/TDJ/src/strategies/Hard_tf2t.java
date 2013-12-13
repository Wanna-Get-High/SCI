package strategies;

import Game.Player;

/**
 * 
 * Strategy : I cooperate unless my opponent betrayed me 2 times in a row in the past 3 actions.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Hard_tf2t extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {


		int size = p1.getActions().size();
		
		if (p1.getActions().size() == 2) {
			if ((!p2.getActions().get(size-1) && !p2.getActions().get(size-2))) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
		} else if (p1.getActions().size() > 2) {
			if ((!p2.getActions().get(size-1) && !p2.getActions().get(size-2)) ||
				(!p2.getActions().get(size-2) && !p2.getActions().get(size-3))) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
		} else {
			p1.addAction(true);
		}
		
	}
}
