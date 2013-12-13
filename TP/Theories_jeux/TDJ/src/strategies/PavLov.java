package strategies;

import Game.Player;

/**
 * 
 * Strategy : I cooperate then i cooperate only if my opponent and i played the same action the last turn else i betray him.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class PavLov extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		int size = p1.getActions().size(); 
		
		if (size > 0) {
			if (p1.getActions().get(size-1) == p2.getActions().get(size-1)) {
				p1.addAction(true);
			} else {
				p1.addAction(false);
			}
		} else {
			p1.addAction(true);
		}

	}

}
