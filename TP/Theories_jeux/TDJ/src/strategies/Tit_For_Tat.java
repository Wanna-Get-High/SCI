package strategies;

import Game.Player;

/**
 * 
 * Strategy : I cooperate the first time, and then i play the previous action of the opponent.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Tit_For_Tat extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		if (p1.getActions().isEmpty()) {
			p1.addAction(true);
		} else {
			p1.addAction(p2.getActions().get(p1.getActions().size()-1)); 
		}
		
	}

}
