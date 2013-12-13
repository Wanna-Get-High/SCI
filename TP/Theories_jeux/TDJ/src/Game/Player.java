package Game;

import java.util.ArrayList;
import strategies.Strategy;

/**
 * This class is used to confront each strategies of the Game class.
 * 		It contains the states and actions used by the current selected strategy.
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Player {

	/**
	 * The current score of the selected strategy.
	 */
	private int score;
	
	/**
	 * The current strategy.
	 */
	private Strategy currentStrategy;
	
	/**
	 * The list of actions that is already donedepending on the selected strategy. 
	 */
	private ArrayList<Boolean> actions;
	
	/**
	 * A state used by the Strategy Spiteful. It tells whether this player has already been betrayed by his opponent.
	 */
	private boolean hasBeenBetrayed;
	
	/**
	 * A state used by the Strategy Easy_Go. It tells whether the other player has cooperated once in the game.
	 */
	private boolean isCooperating;

	/**
	 * A counter used by the strategies tit_for_tat and tit_for_tat. It saves the number of revenge these strategies has to do before cooperating.  
	 */
	private int remainingNumOfRevenge;
	
	/**
	 * A state used by the strategy Prober. It tells whether it has to use tit_for_tat strategy or not.
	 */
	private boolean useProberTFT;
	
	/**
	 * A state used by the strategy Prober. It tells whether it has to use the All_D strategy or not.
	 */
	private boolean useProberAllD;
	
	
	public Player() {
		this.actions = new ArrayList<Boolean>();
		this.score = 0;
		this.hasBeenBetrayed = false;
		this.isCooperating = false;
		this.remainingNumOfRevenge = 0;
		this.useProberTFT = false;
		this.useProberAllD = false;
	}
	
	/**
	 * Play one turn against the other opponent with their current strategy.
	 * 
	 * @param opponent the opponent.
	 */
	public void play(Player opponent) {
		this.currentStrategy.resolve(this, opponent);
	}
	
	/**
	 * Add an action to the list of actions.
	 * @param action the action that will be added.
	 */
	public void addAction(Boolean action) { this.actions.add(action); }

	/**
	 * Increment the number of revenge this player will take. 
	 * 
	 * @param numberOfRevenge the number of revenge to add.
	 */
	public void incrNumberOfRevenge(int numberOfRevenge) {
		this.remainingNumOfRevenge += numberOfRevenge;
	}
	
	/**
	 * Tells whether this player will avenge depending on the number of revenge he has to take.
	 * 	Decrement the number of revenge if > 0.
	 * 
	 * @return true if this player has to avenge.
	 */
	public boolean avenge() {
		if (this.remainingNumOfRevenge > 0) {
			this.remainingNumOfRevenge--;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Reset the values and states for this player.
	 */
	public void reset() {
		this.actions.clear();
		this.score = 0;
		this.hasBeenBetrayed = false;
		this.isCooperating = false;
		this.remainingNumOfRevenge = 0;
		this.useProberTFT = false;
		this.useProberAllD = false;
	}
	
	/**
	 * Get the current score of this player for the current strategy.
	 * @return the score.
	 */
	public int getScore() { return score; }
	
	/**
	 * Get the actions. done by this player for the current strategy.
	 * @return
	 */
	public ArrayList<Boolean> getActions() { return actions; }
	
	/**
	 * Set the new score for this player.
	 * @param score the new score.
	 */
	public void setScore(int score) { this.score = score; }
	
	/**
	 * Set the current strategy for this player.
	 * @param currentStrategy the new current strategy.
	 */
	public void setCurrentStrategy(Strategy currentStrategy) { this.currentStrategy = currentStrategy; }
	
	/**
	 * Set the value of this.hasBeenBetrayed to true.
	 */
	public void setHasBeenBetrayed() { this.hasBeenBetrayed = true; }
	
	/**
	 * Tells whether this player has been betrayed by his opponent.
	 * 
	 * @return true if this player has been betrayed.
	 */
	public boolean hasBeenBetrayed() { return this.hasBeenBetrayed; }
	
	/**
	 * Set the value of this.isCooperating to true.
	 */
	public void setIsCooperating() { this.isCooperating = true; }
	
	/**
	 * Tells whether this player will be cooperating for the end of the confrontation.
	 * 		It is set when the opponent have cooperated.
	 * 
	 * @return true if this player will cooperate.
	 */
	public boolean isCooperating() { return this.isCooperating; }
	
	/**
	 * Tells whether this player will use the tft strategy until the end of the confrontation. 
	 * 
	 * @return true if he will use the tft strategy.
	 */
	public boolean useProberTFT() { return this.useProberTFT; }
	
	/**
	 * Set the value of this.useProberTFT to true.
	 */
	public void setUseProberTFT() { this.useProberTFT = true; }
	
	/**
	 * Tells whether this player will use the All_D strategy until the end of the confrontation. 
	 * 
	 * @return true if he will use the All_D strategy.
	 */
	public boolean useProberAllD() { return this.useProberAllD; }
	
	/**
	 * Set the value of this.useProberAllD to true.
	 */
	public void setUseProberAllD() { this.useProberAllD = true; }

	/**
	 * Print the current actions of this player.
	 */
	public void printActions() {
		for (Boolean b : this.actions) {
			System.out.print(b + " ");
		}
		
		System.out.println();
	}
}
