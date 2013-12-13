package strategies;

import Game.Player;


/**
 * 
 * Strategy : I play the majority action of the opponent if he played 50/50 then i betray him.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Hard_Majo extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		int size = p1.getActions().size();
		
		if (size == 0) {
			p1.addAction(false);
		} else {
			double nbC = 0;
			
			for (int i = 0; i < size; i++) {
				if (p2.getActions().get(i)) {
					nbC++;
				}
			}
			
			if ( (nbC / ((double)size)) > 0.5 ) {
				p1.addAction(true);
			} else {
				p1.addAction(false);
			}
			
		}	

	}

}
