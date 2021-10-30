import java.util.Random;

/**
 * Represents a minesweeper board.
 * @author Jerry
 *
 */
public class Board {
	
	private Square[][] squares; //array of references to Squares representing the board
	private int boardSize; //separate integer to avoid having to access array length.
	private Random rng = new Random();
	private boolean gameOver = false;
	
	/**
	 * Creates and populates a Board object with the given board size. 
	 * @param boardSize
	 */
	public Board(int boardSize) {
		squares = new Square[boardSize][boardSize];
		this.boardSize = boardSize;
		populate();
	}
	
	/**
	 * Equivalent to clicking on the minesweeper square at x and y.
	 * Recursively reveals all contiguous squares until a square with a nonzero number of neighboring mines is reached.
	 * If the square selected is a mine, all squares are revealed and game over is declared.
	 * 
	 * @param x the x-position to reveal
	 * @param y the y-position to reveal
	 */
	public void reveal(int x, int y) {
		//mines may have nonzero neighbors, so this must be checked first
		//game over if a mine is revealed
		if (squares[x][y].isMine) {
			for(int i = 0; i < boardSize; i++) {
				for(int j = 0; j < boardSize; j++) {
					squares[i][j].isRevealed = true;
					gameOver = true;
				}
			}
			return; //return to avoid triggering unnecessary computations
		}
		
		if (squares[x][y].mineNeighbors != 0) {
			squares[x][y].isRevealed = true;
			return;
		} else {
			squares[x][y].isRevealed = true;
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					//must be within bounds; must not double-trigger current square
					if (i >= 0 && j >= 0 && i < boardSize && j < boardSize && i != x && j != y) {
						//if already revealed, don't recursively call
						if (!squares[i][j].isRevealed) {
							reveal(i,j);
						}
						
					}
				}
			}
		}
	}

	/**
	 * Returns a String representation of the board
	 * @return the board's state as a String
	 */
	public String toString() {
		String string  = "";
		//reverse i, j so that it is row by row
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				string += squares[i][j].toString();
			}
			string += "\n";
		}
		return string;
	}
	
	/**
	 * Populates an empty squares array with Square objects
	 * @author Jerry
	 */
	private void populate() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				Square mineSquare = new Square();
				if (rng.nextInt(100) < main.MINE_PROBABILITY) {
					mineSquare.isMine = true;
				}
				squares[i][j] = mineSquare;
			}
		}
		
		neighboursPass();
	}
	
	/**
	 * Does a pass of all the squares to fill in the number of neighbours that are mines
	 */
	private void neighboursPass() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				int neighboringMines = 0;
				
				for (int x = i - 1; x <= i + 1; x++) {
					for (int y = j - 1; y <= j + 1; y++) {
						//x,y must be within bounds; must not double-count current square
						if (x >= 0 && y >= 0 && x < boardSize && y < boardSize && x != i && y != j) {
							if (squares[x][y].isMine) {
								neighboringMines++;
							}
						}
 					}
				}
				
				squares[i][j].mineNeighbors = neighboringMines;
			}
		}
	}
	
	/**
	 * Represents a square on the minesweeper board.
	 * @author Jerry
	 *
	 */
	private class Square {
		boolean isMine = false;
		SquareMarking mark = SquareMarking.NONE; //default square marking is none
		int mineNeighbors; //number of neighbours adjacent to or immediately diagonal to the square that are mines
		boolean isRevealed = false;
		
		/**
		 * Returns a two-character String representing the square
		 */
		public String toString() {
			if (!isRevealed) {
				if(mark == SquareMarking.NONE) {
					return "□ ";
				} else if (mark == SquareMarking.QUESTION) {
					return "? ";
				} else {
					return "⚐ ";
				}
			} else {
				if (isMine) {
					return "💣 ";
				}
				if (mineNeighbors == 0) {
					return ". ";
				} else {
					return mineNeighbors + " ";
				}
			}
		
		}
	}
	
	/**
	 * Represents the three ways a minesweeper square could be marked
	 * (blank, question mark, flagged)
	 * @author Jerry
	 *
	 */
	enum SquareMarking {
		NONE, QUESTION, FLAG
	}
}
