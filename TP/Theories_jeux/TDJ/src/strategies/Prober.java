package strategies;

import Game.Player;


/**
 * 
 * I start with the dcc strategy then if my opponent cooperated at the second and third action i use All_D strategy else Tit_For_Tat strategy.   
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Prober extends Strategy {

	@Override
	public void resolve(Player p1, Player p2) {
		
		int size = p1.getActions().size();
		
		if (p1.useProberAllD()) {
			p1.addAction(false);
		} else if (p1.useProberTFT()) {
			p1.addAction(p2.getActions().get(p1.getActions().size()-1));
		} else {
			if (size == 0) {
				p1.addAction(false);
			} else if (size == 1 || size == 2) {
				p1.addAction(true);
			} else {
				if (p2.getActions().get(1) && p2.getActions().get(2)) {
					p1.setUseProberAllD();
					p1.addAction(false);
				} else {
					p1.setUseProberTFT();
					p1.addAction(p2.getActions().get(p1.getActions().size()-1));
				}
				
			}
		}
	}

}
