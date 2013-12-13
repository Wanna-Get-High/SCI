package Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import strategies.*; 

/**
 * This class is the main class.
 * 		It contain all of the strategies that can confront each others. 
 * 
 * @author Francois Lepan, Benjamin Allaert.
 *
 */
public class Game {

	/**
	 * The score table. Contain the score for each strategies versus the other strategies
	 */
	long[][] scoreTable;

	/**
	 * The number of turn that each strategy do against other strategies.
	 */
	int nbTurn;

	/**
	 * The first player
	 */
	Player player1;

	/**
	 * The second player.
	 */
	Player player2;

	/**
	 * The table of strategies.
	 */
	Strategy[] strategies;


	private final String matrixCSVFilePath = "./matrix.csv";
	private final String curveCSVFilePath = "./curve.csv";


	public Game() {

		this.scoreTable = new long[22][22];
		this.nbTurn = 1000;
		this.player1 = new Player();
		this.player2 = new Player();
		this.strategies = new Strategy[22];

		this.initStrategies();
	}

	/**
	 * Add all the strategies to the table of strategies.
	 */
	private void initStrategies() {
		this.strategies[0] = new All_C();
		this.strategies[1] = new All_D();
		this.strategies[2] = new Spiteful();
		this.strategies[3] = new Easy_Go();
		this.strategies[4] = new Tit_For_Tat();
		this.strategies[5] = new Mistrust();
		this.strategies[6] = new Two_Tit_For_Tat();
		this.strategies[7] = new Three_Tit_For_Tat();
		this.strategies[8] = new Tf2t();
		this.strategies[9] = new Hard_tf2t();
		this.strategies[10] = new Slow_tft();
		this.strategies[11] = new Hard_tft();
		this.strategies[12] = new Soft_Majo();
		this.strategies[13] = new Hard_Majo();
		this.strategies[14] = new Soft_Not_More();
		this.strategies[15] = new Hard_Not_More();
		this.strategies[16] = new PavLov();
		this.strategies[17] = new Prober();
		this.strategies[18] = new Per_CD();
		this.strategies[19] = new Per_DC();
		this.strategies[20] = new Per_CCD();
		this.strategies[21] = new Per_DDC();
	}

	/**
	 * Initialize the matrix representing the score table and create the CSV file.
	 */
	public void initializeScoreTable() {

		// the confrontations
		for (int i = 0; i < this.strategies.length; i++) {
			// we set the current strategy for the first player
			this.player1.setCurrentStrategy(this.strategies[i]);

			for (int j = i; j < this.strategies.length; j++) {
				// we set the current strategy for the second player
				this.player2.setCurrentStrategy(this.strategies[j]);

				// we confront the 2 strategies
				for (int k = 0; k < this.nbTurn; k++) {
					this.player1.play(this.player2);
					this.player2.play(this.player1);

					// we compute the score at each turn
					this.computeScore(k);
				}

				// then we get the final score for each player and save it to the matrix
				this.scoreTable[i][j] = this.player1.getScore();
				this.scoreTable[j][i] = this.player2.getScore();

				// finally we reset the states and values of each player
				this.player1.reset();
				this.player2.reset();		
			}
		}

		// then we create the CSV file 
		this.createMatrixCSVFile();
	}

	/**
	 * Compute the score of each player at the k turn.
	 * 	Add the computed new score to each player. 
	 * 
	 * @param k the selected turn
	 */
	private void computeScore(int k) {
		// CC
		if (this.player1.getActions().get(k) && this.player2.getActions().get(k)) {
			this.player1.setScore(this.player1.getScore()+3);
			this.player2.setScore(this.player2.getScore()+3);
		}
		// DD
		else if (!this.player1.getActions().get(k) && !this.player2.getActions().get(k)) {
			this.player1.setScore(this.player1.getScore()+1);
			this.player2.setScore(this.player2.getScore()+1);
		}
		// CD
		else if (this.player1.getActions().get(k) && !this.player2.getActions().get(k)) {
			this.player2.setScore(this.player2.getScore()+5);
		} 
		// DC
		else {
			this.player1.setScore(this.player1.getScore()+5);
		}
	}

	/**
	 * Confront all of the strategies against each other.
	 * 
	 * 	Write the evolution of the population in a CSV file.
	 * 
	 * @param args the arguments entered by the user.
	 */
	public void confront() {
		
		this.printUsage();
		
		String arguments = "";

		while ( !"quit".equals((arguments = this.scanUserArgument())) ) {
			
			long[] args = this.parseArgs(arguments);
			
			if (args != null && args.length >= 4) {

				long[] chosenStrategies = new long[args.length/2];

				long[] currentPopulation = new long[args.length/2];
				long[] previousPopulation = new long[args.length/2]; 
				long[] scores = new long[args.length/2];

				int nbPeople = 0;
				long totalScore = 0;

				// we initialize the data of previousPopulation
				for (int i = 0; i < previousPopulation.length; i++) {
					previousPopulation[i] = -1;
				}

				// First we parse the arguments of the user in two arrays :
				// 		chosenStrategies and currentPopulation
				for (int i = 0; i < args.length; i++) {

					// the strategy 
					if (i%2 == 0) { 
						chosenStrategies[i/2] = args[i];
					}
					// the population
					else {		
						long val = args[i];
						currentPopulation[i/2] = val;
						nbPeople += val;
					}
				}

				// we create the CSV file
				this.createCurveCSV(chosenStrategies);
				this.writeDataToCurveCSV(currentPopulation);

				// then we confront all of the strategies
				while (this.hasChanged(previousPopulation, currentPopulation) ) {

					totalScore = 0;

					// we update the previous population
					for (int i = 0; i < scores.length; i++) {
						previousPopulation[i] = currentPopulation[i];
					}

					// then we compute the scores for each strategy 
					// depending on the number of population
					for (int i = 0; i < chosenStrategies.length; i++) {
						for (int j = 0; j < chosenStrategies.length; j++) {

							long nbActors = currentPopulation[j];

							if (i == j) nbActors--;

							scores[i] += (nbActors*this.scoreTable[(int)chosenStrategies[i]][(int)chosenStrategies[j]]);
						}

						// we multiply because it has to be for each actor.
						scores[i] = scores[i]*currentPopulation[i];
						totalScore += scores[i];
					}

					for (int i = 0; i < scores.length; i++) {
						currentPopulation[i] = (int)(((double)scores[i]/(double)totalScore)*nbPeople);
						scores[i] = 0;
					}

					// finally we write the data in the CSV file
					this.writeDataToCurveCSV(currentPopulation);
				}
			
				System.out.println("The data has been saved to the file: "+this.curveCSVFilePath);
				
			} else {
				System.out.println("You must enter at least 4 argument separated by a comma in order to confront 2 strategy.");
			}

		}
	}

	
	private long[] parseArgs(String arguments) {
		
		String[] stringArgs = arguments.split(",");
		long[] args = new long[stringArgs.length];
		
		for (int i = 0; i < args.length; i++) {
			
			try {
				args[i] = Long.valueOf(stringArgs[i]);
			} catch (NumberFormatException nfe) {
				System.out.println("You must enter only numbers.");
				return null;
			}
		}
		
		return args;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private String scanUserArgument() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n> ");
		return sc.next();
	}

	/**
	 * Tells whether the number of previous population and the number of current population has changed.
	 * 
	 * @param previousPopulation the number of previous population for each chosen strategy.
	 * @param currentPopulation the number of current population for each chosen strategy.
	 * @return true if the number of population has changed.
	 */
	private boolean hasChanged(long[] previousPopulation, long[] currentPopulation) {

		for (int i = 0; i < currentPopulation.length; i++) {
			if (previousPopulation[i] != currentPopulation[i]) {
				return true;
			}	
		}

		return false;
	}

	/**
	 * Create the CSV file for the matrix 
	 */
	private void createMatrixCSVFile() {
		// then we initialize it with the name of the chosen strategies 
		String strategies = "";

		// we create a new CSV file
		try {
			new FileWriter(new File(this.matrixCSVFilePath)).close();
		} catch (IOException ioe) {
			System.err.println("unable to create \""+this.matrixCSVFilePath+"\"");
			System.exit(-1);
		}

		for (int i = 0; i < this.strategies.length-1; i++) {
			strategies += this.strategies[i].getClass().getSimpleName() + ";";
		}

		strategies += this.strategies[this.strategies.length-1].getClass().getSimpleName() + "\n";

		this.writeDataToMatrixCSV(strategies);

		for (int i = 0; i < this.scoreTable.length; i++) {
			this.writeDataToMatrixCSV(this.scoreTable[i]);
		}

	}

	/**
	 * Write the name of the strategies in the Matrix CSV file.
	 * 
	 * @param strategies the strategies.
	 */
	private void writeDataToMatrixCSV(String strategies) {
		this.writeDataToCSV(this.matrixCSVFilePath, strategies);
	}

	/**
	 * Write the population of all of the strategies in the matrix CSV file.
	 * 
	 * @param data the line of data.
	 */
	private void writeDataToMatrixCSV(long[] data) {
		this.writeDataToCSV(this.matrixCSVFilePath, data);
	}

	/**
	 * Create the CSV file and add the name of the chosen strategies on the first line.
	 * 
	 * @param chosenStrategies the chosen strategies.
	 */
	private void createCurveCSV(long[] chosenStrategies) {

		// we create a new CSV file
		try {
			new FileWriter(new File(this.curveCSVFilePath)).close();
		} catch (IOException ioe) {
			System.err.println("unable to create \""+this.curveCSVFilePath+"\"");
			System.exit(-1);
		}

		// then we initialize it with the name of the chosen strategies 
		String strategies = "";

		for (int i = 0; i < chosenStrategies.length-1; i++) {
			strategies += this.strategies[(int)chosenStrategies[i]].getClass().getSimpleName() + ";";
		}

		strategies += this.strategies[(int)chosenStrategies[(int)chosenStrategies.length-1]].getClass().getSimpleName() + "\n";

		this.writeDataToCurveCSV(strategies);
	}

	/**
	 * Write the current population on a new line in the curve CSV file.  
	 * 
	 * @param data the data to write.
	 */
	private void writeDataToCurveCSV(long[] data) {
		this.writeDataToCSV(this.curveCSVFilePath, data);
	}

	/**
	 * Write the name of the chosen strategies in the curve CSV file.
	 * 
	 * @param data the name of the strategies.
	 */
	private void writeDataToCurveCSV(String data) {
		this.writeDataToCSV(this.curveCSVFilePath, data);
	}

	/**
	 * Write the data in the CSV file name by filePath.
	 * 
	 * @param filePath the path of the CSV file.
	 * @param data the data to write.
	 */
	private void writeDataToCSV(String filePath, long[] data) {
		String s = "";

		for (int i = 0; i < data.length-1; i++) {
			s += data[i] + ";";
		}

		s += data[data.length-1] + "\n";

		this.writeDataToCSV(filePath, s);
	}

	/**
	 * Write the data in the CSV file name by filePath.
	 * 
	 * @param filePath the path of the CSV file.
	 * @param data the data to write.
	 */
	private void writeDataToCSV(String filePath, String data) {

		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.flush();
			bw.close();

		} catch (FileNotFoundException fnfe) {
			System.err.println("File \""+filePath+"\" not found.");
		} catch (IOException ioe) {
			System.err.println("IO Exception for the file: \""+filePath+"\" ");
		}

	}

	/**
	 * Print the score table.
	 */
	public void printScoreTable() {

		for (int i = 0; i < this.scoreTable.length; i++) {
			for (int j = 0; j < this.scoreTable[0].length; j++) {
				System.out.print(this.scoreTable[i][j] + "\t");
			}

			System.out.println();
		}

	}

	private void printUsage() {
		System.out.println("You must enter at least 4 argument separated by a comma in order to confront 2 strategy:\n");
		System.out.println("\t<num_of_strategy>,<number_of_population>,<num_of_strategy>,<number_of_population>,[<num_of_strategy>,<number_of_population>]*\n");
		System.out.println("num_of_strategy :\n");
		System.out.print("\t");
		for (int i = 0; i < this.strategies.length; i++) {
			System.out.print("|"+i+" : "+this.strategies[i].getClass().getSimpleName()+" |\t");
			if (i == this.strategies.length/3 || i == this.strategies.length*2/3-1)
				System.out.print("\n\t");
		}
		System.out.println("\n");
		System.out.println("Enter quit to exit the program.");
	}

	public static void main(String[] args) {

		Game game = new Game();

		game.initializeScoreTable();
		game.confront();

	}
}
