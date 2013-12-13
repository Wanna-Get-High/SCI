package strategies;

import Game.Player;

/**
 * Strategy : I cooperate but i betray my opponent two times each time he betray me. 
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Two_Tit_For_Tat extends Strategy {

	private int numberOfRevenge = 2;
	
	@Override
	public void resolve(Player p1, Player p2) {
		
		if (p1.getActions().isEmpty()) {
			p1.addAction(true);
		} else {
			// if he betrayed me on the last turn, we add the number of revenge
			if (!p2.getActions().get(p1.getActions().size()-1)) {
				p1.incrNumberOfRevenge(this.numberOfRevenge);
			}
			
			// if the player has to avenge, we decrement 
			// the remaining number of revenge and we betray.
			if (p1.avenge()) {
				p1.addAction(false);
			} else {
				p1.addAction(true);
			}
			
		}
		
	}

}
