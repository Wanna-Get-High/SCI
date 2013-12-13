package strategies;

import Game.Player;


/**
 * 
 * Strategy : I cooperate unless my opponent betrayed me 2 times in a row.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Tf2t extends Strategy{

	@Override
	public void resolve(Player p1, Player p2) {
		
		if (p1.getActions().size() > 1) {
			int size = p1.getActions().size();
			if (!p2.getActions().get(size-1) && !p2.getActions().get(size-2)) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
		} else {
			p1.addAction(true);
		}
		
	}

}
