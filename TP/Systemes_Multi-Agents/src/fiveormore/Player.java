package fiveormore;

import java.util.InputMismatchException;
import java.util.Scanner;

import core.Agent;
import core.Environment;

/**
 * The Agent that will move a token if a path exists between the start and the arrival of the selected token.<br>
 * It search for the shortest path between the start and arrival.  
 * 
 * @author Francois Lepan, Alexis Linke
 *
 */
public class Player extends Agent {
	
	private int startX;
	private int startY;
	private int arrivalX;
	private int arrivalY;
	
	public Player(Environment env) {
		super(env);
	}

	@Override
	public void doAction() {
		
		int size = this.environment.getSize();
		
		boolean aPathIsntFound = true; 
		
		Integer[][] pathGrid;
		
		// the steps to make all of the neighbor of the current token
		int neighborStep[][] = { {-1,-1}, {-1,1}, {-1,0}, {0,1}, {0,-1}, {1,-1}, {1,1}, {1,0}};
		
		// while it hasn't found a path
		// try to find one between start and arrival
		while (aPathIsntFound) {
			// get the values from the user
			// it has to be correct
			while(!this.getValuesFromUser()){}
			
			// initialize the cell to check indexes.
			Integer[][] cellToCheck = new Integer[size*size][2]; 
			
			// initialize the neighbor with the cell to arrive
			cellToCheck[0][0] = this.arrivalX;
			cellToCheck[0][1] = this.arrivalY;
			
			int index = 0;
			int addNeighborIndex = 1;
			
			pathGrid = this.initializeGrid();
			
			// while there are neighbors to check or the start isn't founded
			while (aPathIsntFound && (index < size*size) && cellToCheck[index][0] != null ) {
				
				
				// check the neighbors of the current cell to check
				for (int i = 0; aPathIsntFound && i < neighborStep.length; i++) {
					int neighbourgXIndex = cellToCheck[index][0]+neighborStep[i][0];
					int neighbourgYIndex = cellToCheck[index][1]+neighborStep[i][1];
					
					// if the index is in the environment
					if (neighbourgXIndex < size && 
							neighbourgXIndex >= 0 &&
							neighbourgYIndex < size && 
							neighbourgYIndex >= 0) {
						
						// if we found the arrival
						if (neighbourgXIndex == this.startX && neighbourgYIndex == this.startY) {
							
							aPathIsntFound = false;
							
							// we put the current value + 1
							pathGrid[neighbourgXIndex][neighbourgYIndex] = pathGrid[cellToCheck[index][0]][cellToCheck[index][1]] +1;
						}
						// else if the place is empty
						else if (pathGrid[neighbourgXIndex][neighbourgYIndex] == null ) {
							
							// we put the current value + 1
							pathGrid[neighbourgXIndex][neighbourgYIndex] = pathGrid[cellToCheck[index][0]][cellToCheck[index][1]] +1;
							
							// we add  this cell to the cell to check
							cellToCheck[addNeighborIndex][0] = neighbourgXIndex;
							cellToCheck[addNeighborIndex][1] = neighbourgYIndex;
							
							// we move the addNeighborIndex
							addNeighborIndex++;
						}
					}
				} // end for
				
				// we move the index for the cell to check
				index++;
			}
			
			if (aPathIsntFound)
				System.out.println("No path is available from ["+this.startX+","+this.startY+"] to ["+this.arrivalX+","+this.arrivalY+"]\n");
		}

		// TODO : add a visible movement between start and arrival
		
		
		
		// we move the agent to the position
		this.environment.moveAgent(this.environment.getAgentAt(this.startX, this.startY), this.arrivalX , this.arrivalY);
	}

	/**
	 * Initialize the table that will be filled of values to detect if there is a path between the start and the arrival.<br>
	 * It puts Integer.MAX_VALUE where an Agent is at the same place, 0 to the arrival and null to the start.
	 * 
	 * @return the table that will be used to check the smallest path.
	 */
	private Integer[][] initializeGrid() {
		
		int size = this.environment.getSize();
		Integer[][] table = new Integer[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.environment.getAgentAt(i, j) != null) {
					table[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		
		table[this.arrivalX][this.arrivalY] = 0;
		table[this.startX][this.startY] = null;
		
		return table;
	}

	
	/**
	 * Retrieve the 4 values (start and arrival) from the user.
	 * 
	 * @return true if he entered correctly 4 integer and correct indexes (start must contain a token and arrival should be empty).
	 */
	private boolean getValuesFromUser() {
		
		Scanner user_input = new Scanner(System.in);
		
		try {
			System.out.print("Enter the X start : ");
			this.startX = user_input.nextBigInteger().intValue();

			System.out.print("Enter the Y start : ");
			this.startY = user_input.nextBigInteger().intValue();

			System.out.print("Enter the X arrival : ");
			this.arrivalX = user_input.nextBigInteger().intValue();
			
			System.out.print("Enter the Y arrival : ");
			this.arrivalY = user_input.nextBigInteger().intValue();
			System.out.println();
			
		} catch (InputMismatchException e) {
			System.out.println("you must enter an Integer");
			return false;
		}

		if (this.environment.getAgentAt(startX, startY) != null
				&& this.environment.getAgentAt(arrivalX, arrivalY) == null )
		{
			return true;
		} else {
			System.out.println("you must enter valid indexes : start must contain a token and arrival should be empty");
			return false;
		}
	}
}
