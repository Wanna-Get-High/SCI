package strategies;

import Game.Player;

/**
 * 
 * Strategy : I betray if the score of my opponent is greater or equal than mine. If we have the same score i cooperate.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Hard_Not_More extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		if (p2.getScore() >= p1.getScore()) {
			p1.addAction(false);
		} else {
			p1.addAction(true);
		}

	}

}
