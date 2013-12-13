package strategies;

import Game.Player;

/**
 * 
 * Strategy : I cooperate until my opponent betray me and then i always betray him. 
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Spiteful extends Strategy {
	
	@Override
	public void resolve(Player p1, Player p2) {
		
		boolean lastAction = true;
		
		// to avoid going out of range (-1) for the first turn
		if (p1.getActions().size() > 0)
			lastAction = p2.getActions().get(p1.getActions().size()-1);
			
		if (p1.hasBeenBetrayed()) {
			p1.addAction(false);
		} else {
			if (lastAction) {		// C
				p1.addAction(true);
			} else { 				// D
				p1.setHasBeenBetrayed();
				p1.addAction(false);
			}
		}
		
	}

}
