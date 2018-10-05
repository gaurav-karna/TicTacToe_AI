// Gaurav Karna - ID: 260723118
// Assignment 3
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

// this program allows the game of Tic-Tac-Toe to be played against an AI
public class TicTacToe {
	public static void main(String[] args) {
		//play();
		//char[][] board = createBoard(3);
		//board[1][1] = 'x';
		//writeOnBoard(board, 'x', 1, 1);
		//getUserMove(board);
		play();
	}
	
	// this method returns a square board of the given integer as both dimensions
	public static char[][] createBoard(int n) {
		char[][] board = new char[n][n];
		int i;
		int j;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				board[i][j] = ' ';
			}
		}
		return board;
	}
	
	// This method takes the 2D array as input, and prints out the board.
	public static void displayBoard(char[][] board) {
		int i;
		int j;
		int dataRow;
		dataRow = -1;
		int dataColumn;
		for (i = 0; i < (((board.length)*2)+1); i++) {
			if ((i%2 == 0)) {
				System.out.print(helperPlusMinus(((board.length)*2)+1));
			} else {
				dataRow++;
				dataColumn = -1;
				for (j = 0; j < (((board.length)*2)+1); j++) {
					if ((j%2 == 0)) {
						System.out.print('|');
					} else {
						dataColumn++;
						System.out.print(board[dataRow][dataColumn]);
					}
				}
			}
			if (!(i == (((board.length)*2)+1))) {
			System.out.println();
			}
		}
	}
	
	// helper method that generates a line of alternating +/- of a given length
	public static String helperPlusMinus(int length) {
		String line = "";
		for (int i = 1; i <= length; i++) {
			if (i%2 == 0) {
				System.out.print(line + '-');
			} else {
				System.out.print(line + '+');
			}
		}
		return line;
	}
	
	// method that inputs a given character into a given space on the board.
	// method also throws exception if coordinates are out of bounds, or if space is used.
	public static void writeOnBoard(char[][] board, char symbol, int x, int y) {
		if ((x < 0 || x >= board.length) || (y < 0 || y >= board.length)) {
			throw new IllegalArgumentException ("No space exists in the coordinates given.");
		}
		if (!(board[x][y] == ' ')) {
			throw new IllegalArgumentException ("This space has already been used. Try another!");
		}
		// inputting move into the board at desired coordinates.
		board[x][y] = symbol;
	}
	
	// method that is designed to get a desired move from the user and input it into the board
	public static void getUserMove(char[][] board) {
		Scanner read = new Scanner(System.in);
		int x = 0;
		int y = 0;
		System.out.println("Please enter your move: ");
		while (read.hasNextLine()) {
			if (!(read.hasNextInt())) {
				System.out.println("Must be a valid int inside the bounds of the grid. Try again.");
				read.nextLine();
			} else {
				x = read.nextInt();
				break;
			}
		}
		while (read.hasNextLine()) {
			if (!(read.hasNextInt())) {
				System.out.println("Must be a valid int inside the bounds of the grid. Try again.");
				read.nextLine();
			} else {
				y = read.nextInt();
				break;
			}
		}
		writeOnBoard(board, 'x', x, y);
	}
	
	public static boolean checkForObviousMove (char[][] board) {
		int index;
		// The following few sets code scan for a place to insert 'o' to win the game
		// checks all the rows for a winning obvious move
		for (int i = 0; i < board.length; i++) {
			index = -1;
			index = helperWinningAI(helperRowReturn(board, i), 'o');
			if ((!(index == -1)) && (board[i][index] == ' ')){
				writeOnBoard(board, 'o', i, index);
				return true;
			} else {
				continue;
			}
		}
		// checks all the columns for a winning obvious move
		for (int i = 0; i < board.length; i++) {
			index = -1;
			index = helperWinningAI(helperColumnReturn(board, i), 'o');
			if ((!(index == -1)) && (board[i][index] == ' ')) {
				writeOnBoard(board, 'o', i, index);
				return true;
			} else {
				continue;
			}
		}
		index = -1;
		// checks both diagonals for a winning obvious move
		index = helperWinningAI(helperDiagonalReturn(board, "left"), 'o');
		if ((!(index == -1)) && (board[index][index] == ' ')) {
			writeOnBoard(board, 'o', index, index);
			return true;
		} else {
			index = -1;
			index = helperWinningAI(helperDiagonalReturn(board, "right"), 'o');
			if ((!(index == -1)) && (board[index][index] == ' ')) {
				writeOnBoard(board, 'o', index, index);
				return true;
			}
		}
		// The following few sets code scan for a place to insert 'o' to prevent losing the game
		// checks all the rows for an obvious move to prevent loss.
		for (int i = 0; i < board.length; i++) {
			index = -1;
			index = helperWinningAI(helperRowReturn(board, i), 'x');
			if ((!(index == -1)) && (board[i][index] == ' ')) {
				writeOnBoard(board, 'o', i, index);
				return true;
			} else {
				continue;
			}
		}
		// checks all the columns for an obvious move to prevent loss.
		for (int i = 0; i < board.length; i++) {
			index = -1;
			index = helperWinningAI(helperColumnReturn(board, i), 'x');
			if ((!(index == -1)) && (board[i][index] == ' ')) {
				writeOnBoard(board, 'o', i, index);
				return true;
			} else {
				continue;
			}
		}
		// checks both diagonals for an obvious move to prevent loss.
		index = -1;
		index = helperWinningAI(helperDiagonalReturn(board, "left"), 'x');
		if ((!(index == -1)) && (board[index][index] == ' ')) {
			writeOnBoard(board, 'o', index, index);
			return true;
		} else {
			index = -1;
			index = helperWinningAI(helperDiagonalReturn(board, "right"), 'x');
			if ((!(index == -1)) && (board[index][index] == ' ')) {
				writeOnBoard(board, 'o', index, index);
				return true;
			}
		}
		return false;
	}
	/* helper method that counts the number of 'x's in a given array, that can represent
	* a row, column, or diagonal */
	public static int helperCharCounter (char[] line) {
		int charNumber = 0;
		int i;
		for (i = 0; i < line.length; i++) {
			if (line[i] == 'x') {
				charNumber++;
			}
		}
		return charNumber;
	}
	/* helper method that looks for the space character in a given array, that can
	* represent a row, column, or diagonal */
	public static int helperSpaceLooker (char[] line) {
		int spacePosition = -1;
		/* initialized to -1, so that if no space exists, -1 would return, and we would
		* know whether there was a space or not; because if there is, then we would never
		* get a negative number, since array indexes are >= 0*/
		int i;
		for (i = 0; i < line.length; i++) {
			if (line[i] == ' ') {
					spacePosition = i;
			}
		}
		return spacePosition;
	}
	// method that returns a row as a 1-D array
	public static char[] helperRowReturn (char[][] board, int rowIndex) {
		// setting up 1D array to return row, with length = row length
		char [] row = new char[(board.length)];
		int i;
		// ensuring that index given is correct
		if ((rowIndex < 0) || (rowIndex >= board.length)) {
			System.out.println("Row Index out of bounds. Try again.");
		} else {
			// populating char 1D array with row content
			for (i = 0; i < board[rowIndex].length; i++) {
				row[i] = board[rowIndex][i];
			}
		}
		return row;
	}
	// method that returns a column as a 1-D array
	public static char[] helperColumnReturn(char[][] board, int colIndex) {
		char[] col = new char[(board.length)];
		int i;
		int j;
		for (i = 0; i < board.length; i++) {
			for (j = 0; j < board.length; j++) {
				if (j == colIndex) {
					col[i] = board[i][j];
				}
			}
		}
		return col;
	}
	// method that returns a diagonal in the grid as a 1D array. 
	public static char[] helperDiagonalReturn(char[][] board, String s) {
		char[] diag = new char[(board.length)];
		int i;
		int j;
		int rightCounter = 0;
		if (!((s == "left") || (s == "right"))) {
			System.out.println("Please say which diagonal desired - left or right?");
		} else {
			if (s == "left") {
				j = 0;
				for (i = 0; i < board.length; i++) {
					while (j == i) {
						diag[i] = board[i][j];
						j++;
					}
				}
			} else {
				if (s == "right") {
					j = board.length - 1;
					i = 0;
					for (i = 0; i < board.length; i++) {
						while (j + i == board.length - 1) {
							diag[i] = board[i][j];
							j--;
						}
					}
				}
			}
		}
		return diag;
	}
	/* method to check whether or not the AI is winning in a row, and returns the index
	* where to insert the character if required to win, or prevent a loss 
	* We know that if a value of -1 is returned, then there is no such move.*/
	public static int helperWinningAI(char[] line, char symbol) {
		int desiredIndex = -1;
		int counter = 0;
		int i;
		int spaceCounter = 0;
		int spacePosition = 0;
		for (i = 0; i < line.length; i++) {
			if (line[i] == symbol) {
				counter++;
			} else {
				if (line[i] == ' ') {
					spaceCounter++;
					spacePosition = i;
				} else {
					continue;
				}
			}
		}
		/* note for myself: if method is ran with char as 'x', and 2 is returned
		* tell AI to input 'o' to prevent loss; same if it is run with char as 'o', 
		* tell AI to input 'o' to get win. */
		if ((counter == line.length - 1) && (spaceCounter == 1)) {
			desiredIndex = spacePosition;
		} else {
			desiredIndex = -1;
		}
		return desiredIndex;
	}
	/* method serves to generate a move for the AI, either by giving it an obvious move
	* to execute, or generating a random one. */
	public static void getAIMove(char[][] board) {

		int seed = board.length;
		int row;
		int col;
		// setting up the coordinates that will be randomized within the bounds of the board
		Random xRandom = new Random ();
		Random yRandom = new Random ();
		// if there is no obvious move, then this block will execute.
		if (checkForObviousMove(board) == false) {
			row = xRandom.nextInt(seed);
			col = yRandom.nextInt(seed);
			/* this loop will keep running until a valid coordinate is generated where a 'o'
			* can be inserted */
			while (!(board[row][col] == ' ')) {
				row = xRandom.nextInt(seed);
				col = yRandom.nextInt(seed);
				//System.out.println(row + " " + col);
			}
			if (board[row][col] == ' ') {
				writeOnBoard(board, 'o', row, col);
			}
		}
	}
	// this method checks to see if the game has been won, either by the player or AI
	public static char checkForWinner (char[][] board) {
		char symbol;
		int counterAI;
		int counterPlayer;
		char [] line = new char[(board.length)];
		// let us choose to check all the rows first, for a winner or loser
		for (int i = 0; i < board.length; i++) {
			counterAI = 0;
			counterPlayer = 0;
			line = helperRowReturn(board, i);
			for (int j = 0; j < line.length; j++) {
				if (line[j] == 'x') {
					counterPlayer++;
				} else {
					if (line[j] == 'o') {
						counterAI++;
					} else {
						continue;
					}
				}
			}
			// If the number of x/o equals the length of the board, we have a winning line
			if (counterPlayer == board.length) {
				return 'x';
			} else {
				if (counterAI == board.length)  {
					return 'o';
				} else {
					continue;
				}
			}
		}
		//return ' '; would be used here, except that we should check the columns and diagnols too
		for (int i = 0; i < board.length; i++) {
			counterAI = 0;
			counterPlayer = 0;
			line = helperColumnReturn(board, i);
			for (int j = 0; j < line.length; j++) {
				if (line[j] == 'x') {
					counterPlayer++;
				} else {
					if (line[j] == 'o') {
						counterAI++;
					} else {
						continue;
					}
				}
			}
			/* here again, we are saying that if the number of x/o equals the length of the board,
			* we have a winning line */
			if (counterPlayer == board.length) {
				return 'x';
			} else {
				if (counterAI == board.length)  {
					return 'o';
				} else {
					continue;
				}
			}
		}
		counterAI = 0;
		counterPlayer = 0;
		// now we check the diagonals for any possible winning combinations.
		// checking the LEFT diagonal
		line = helperDiagonalReturn(board, "left");
		for (int j = 0; j < line.length; j++) {
			if (line[j] == 'x') {
				counterPlayer++;
			} else {
				if (line[j] == 'o') {
					counterAI++;
				} else {
					continue;
				}
			}
		}
		/* here again, we are saying that if the number of x/o equals the length of the board,
		* we have a winning line */
		if (counterPlayer == board.length) {
			return 'x';
		} else {
			if (counterAI == board.length)  {
				return 'o';
			}
		}
		// checking the RIGHT diagonal
		line = helperDiagonalReturn(board, "right");
		for (int j = 0; j < line.length; j++) {
			if (line[j] == 'x') {
				counterPlayer++;
			} else {
				if (line[j] == 'o') {
					counterAI++;
				} else {
					continue;
				}
			}
		}
		/* here again, we are saying that if the number of x/o equals the length of the board,
		* we have a winning line */
		if (counterPlayer == board.length) {
			return 'x';
		} else {
			if (counterAI == board.length)  {
				return 'o';
			}
		}
		return ' ';
	}
	public static String coinFlip() {
		String result;
		int randomResult;
		randomResult = (int) ((Math.random()*2));
		if (randomResult%2 == 0) {
			result = "heads";
		} else {
			result = "tails";
		}
		return result;
	}
	// this method checks if there is a tie in the game
	public static boolean gameTie(char[][] board) {
		boolean tie = false;
		int spaceCounter = 0;
		char [] line = new char[(board.length)];
		/* if we check all the rows for a space, then we have essentially scanned the
		* entire board */
		for (int i = 0; i < board.length; i++) {
			spaceCounter = 0;
			line = helperRowReturn(board, i);
			for (int j = 0; j < line.length; j++) {
				if (line[j] == ' ') {
					spaceCounter++;
				}
			}			
		}
		if (spaceCounter > 0) {
			tie = false;
		} else {
			if (spaceCounter == 0) {
				tie = true;
			}
		}
		return tie;
	}
	public static void play() {
		String name;
		String coinToss = coinFlip();
		int boardSize = 0;
		boolean boardSizeCorrect;
		Scanner read = new Scanner(System.in); 
		System.out.println("Hello! Welcome to TicTacToe! What is your name? ");
		name = read.nextLine();
		System.out.println("Hello " + name + "!");
		System.out.println("What size board would you like to play with? ");
		boardSizeCorrect = read.hasNextInt();
		// while loop
		while(read.hasNextLine()) {
			if(read.hasNextInt()) {
				boardSize = read.nextInt();
				break;
			}
			else {
				System.out.println("This is not an int, please enter new move.");
				read.nextLine();
			}
		}

		
		char[][] board = createBoard(boardSize);
		System.out.println("The result of the coin toss is: " + coinToss);
		if (coinToss == "heads") {
			System.out.println("The AI plays first.");
			System.out.println("The AI has made its first move: ");
			getAIMove(board);
			displayBoard(board);
			while (checkForWinner(board) == ' ') {
				getUserMove(board);
				displayBoard(board);
				if ((checkForWinner(board) == 'x')) {
					System.out.println("Congratulations! You have won the game!");
					break;
				} else {
					if ((checkForWinner(board)) == 'o') {
						System.out.println("GAME OVER!");
						System.out.println("You lost! Try again!");
						break;
					} else {
						if ((checkForWinner(board)) == ' ') {
							System.out.println("The AI has made its move: ");
							getAIMove(board);
							displayBoard(board);
							if ((checkForWinner(board) == 'x')) {
								System.out.println("Congratulations! You have won the game!");
								break;
							} else {
								if ((checkForWinner(board)) == 'o') {
									System.out.println("GAME OVER!");
									System.out.println("You lost! Try again!");
									break;
								} else {
									if (gameTie(board) == false) {
										continue;
									} else {
										if (gameTie(board) == true) {
											System.out.println("It's a tie!");
											System.out.println("Try again!");
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			if (coinToss == "tails") {
				System.out.println("You play first.");
				getUserMove(board);
				displayBoard(board);
				while (checkForWinner(board) == ' ') {
					System.out.println("The AI has made its move");
					getAIMove(board);
					displayBoard(board);
					if ((checkForWinner(board) == 'x')) {
						System.out.println("Congratulations! You have won the game!");
						break;
					} else {
						if ((checkForWinner(board)) == 'o') {
							System.out.println("GAME OVER!");
							System.out.println("You lost! Try again!");
							break;
						} else {
							if ((checkForWinner(board)) == ' ') {
								getUserMove(board);
								displayBoard(board);
								if ((checkForWinner(board) == 'x')) {
									System.out.println("Congratulations! You have won the game!");
									break;
								} else {
									if ((checkForWinner(board)) == 'o') {
										System.out.println("GAME OVER!");
										System.out.println("You lost! Try again!");
										break;
									} else {
										if (gameTie(board) == false) {
											continue;
										} else {
											if (gameTie(board) == true) {
												System.out.println("It's a tie!");
												System.out.println("Try again!");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}


