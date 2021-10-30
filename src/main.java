import java.util.Scanner;

public class main {
	
	/* Global constants */
	public static final int MIN_SIZE = 7; //minimum size of the board
	public static final int MAX_SIZE = 20; //maximum size of the board
	public static final int MINE_PROBABILITY = 10; //percentage probability that a square is generated as a mine
	
	
	public static void main(String[] args) {
		System.out.println("Command line minesweeper project");
		System.out.println("Please input the size of the board; minimum " + MIN_SIZE + " and maximum " + MAX_SIZE);
		Scanner sc = new Scanner(System.in);
		
		int boardSize;
		while(true) {
			try {
				boardSize = sc.nextInt();
				if (boardSize < MIN_SIZE || boardSize > MAX_SIZE) {
					throw new Exception("out of bounds");
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.println("Input was not valid; try again.");
			}
		}
		
		/* Create Minesweeper board */
		Board board = new Board(boardSize);
		
		while(true) {
			try {
				System.out.print(board.toString());
				System.out.println("Input x coordinate");
				int x = sc.nextInt();
				System.out.println("Input y coordinate");
				int y = sc.nextInt();
				board.reveal(x, y);
				if (board.gameOver) {
					System.out.print(board.toString());
					System.out.println("Game over!");
					break;
				}
			} catch(Exception e) {
				System.out.println("Input was not valid, try again.");
			}
		}
		
		
	}
}
