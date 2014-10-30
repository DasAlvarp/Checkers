package checkersMain;

import java.util.Scanner;

public class Board {

	// all the squares.

	Scanner scanMan = new Scanner(System.in);

	boolean ats = true;// if there are ats or dollars left.
	boolean dollas = true;

	protected int timeSinceJump = 0;// for 5k move without capture draw rule.

	static int RANK = 8;
	static int FILE = 8;
	private String[] playerNames = new String[2];
	protected char playingChar = '$';

	private int playerTurn = 0;

	protected Square[][] theBoard = new Square[FILE][RANK];

	protected boolean isRunning = true;

	protected boolean montyThisSeemsStrange = false;// for the simulation.

	public int runs()/**
	 * @returns if function is running, and if not, the winner,
	 *          etc...
	 */
	{
		if (isRunning && ats && dollas) {
			return 0;
		} else if (!ats) {
			return 1;
		} else if (!dollas) {
			return 2;
		} else {
			return -1;
		}
	}

	public void setBoard()// Fills theBoard with starting coords.
	{
		ats = true;
		dollas = true;
		timeSinceJump = 0;
		// getPlayers();

		for (int r = 0; r < FILE; r++) {
			for (int f = 0; f < RANK; f++) {
				if ((f + r) % 2 == 1)// checking the color of the square. Not
										// sure which color, but I can change it
										// easily.
				{
					if (r < 3)// pretty sure it's the right rank?
					{
						theBoard[f][r] = new Square('@');

					} else if (r > 4)// see r < 3.
					{
						theBoard[f][r] = new Square('$');
					} else// blank squares.
					{
						theBoard[f][r] = new Square();
					}
				} else// blank squares.
				{
					theBoard[f][r] = new Square();
				}

			}
		}
		if (!montyThisSeemsStrange)
			System.out.println("made the board");
	}

	private Square findPiece(Location loc)/**
	 * @returns square at given
	 *          coordinates..
	 */
	{
		/** @returns the square at point c1, c2 */
		return theBoard[loc.getX()][loc.getY()];
	}

	private void incrementTurn()// see name. It increments the turn.
	{
		playerTurn += 1;
		playerTurn %= 2;
		if (playerTurn == 0) {
			playingChar = '$';
		} else {
			playingChar = '@';
		}

		if (!montyThisSeemsStrange) {
			System.out.println("it is " + playerNames[playerTurn]
					+ "'s turn. (" + playingChar + ")");
			System.out.println(playerTurn);
		}
	}

	public char notPlaying(char s) {
		if (s == '@')
			return '$';
		else
			return '@';
		// /nothing else shuold happen. It might, but it shouldn't
	}

	// BOARD MANIPULATION GOES HERE
	private void delete(int one, int two)// replaces coordinates piece on
											// theBoard with ' '
	{
		theBoard[one][two] = new Square();
	}

	private void teleport(Location from, Location to)// Removes a piece from one
														// place to other
	{
		theBoard[to.getX()][to.getY()] = theBoard[from.getX()][from.getY()];
		delete(from.getX(), from.getY());
		timeSinceJump++;
	}

	private void deleteBetween(Location first, Location second)// deletes all
																// the squares
																// in a diagonal
																// between any
																// two square.
	{
		if (Math.abs(first.getX() - second.getX()) != 1) {
			timeSinceJump = 0;
		}
		int dirX = first.getX() - second.getX();
		int dirY = first.getY() - second.getY();
		dirX = dirX / Math.abs(dirX);
		dirY = dirY / Math.abs(dirY);
		for (int x = 1; x < Math.abs(second.getX() - first.getX()); x++) {
			delete(first.getX() - dirX * x, first.getY() - dirY * x);
		}
	}



	protected void allTheJumps(Location from, Location to)// tests for all
															// possible
															// movements.
	{
		if (theBoard[from.getX()][from.getY()].getPiece() == playingChar)// sees
																			// if
																			// player
																			// playing
																			// is
																			// controlling
																			// char
																			// moving.
		{
			if (canJump(from, to))// checks if it's a king or if it's moving in
									// the correct direction.
			{
				jumpThings(from, to);

			} else {
				if (!montyThisSeemsStrange)
					System.out.println("You are very dumb.");// haven't really
																// used other
																// cases yet,
																// will use that
																// later.
			}

			incrementTurn();

		} else {
			if (!montyThisSeemsStrange)
				System.out.println("It's not your turn.");
		}
	}

	public void maintenence()// well, kind of what it sounds like. Kings pieces,
								// checks for draws, etc...
	{
		if (timeSinceJump > 500)// 500 move draw rule.
		{
			isRunning = false;
		}
		for (int x = 0; x < 8; x++)// kinging.
		{
			if (theBoard[x][7].getPiece() == '@') {
				theBoard[x][7].king();
			} else if (theBoard[x][0].getPiece() == '$') {
				theBoard[x][0].king();
			}
		}

		boolean at = false;// making sure the game's not over.
		boolean cash = false;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

				if (theBoard[x][y].getPiece() == '@')
					at = true;
				else if (theBoard[x][y].getPiece() == '$')
					cash = true;

				if (cash && at) {
					x = 8;
					y = 8;
				}

			}
		}
		ats = at;
		dollas = cash;
	}





	// /SCANNER SECTION! This probably could be another class.

	private boolean stahp()/**
	 * @returns true if somebody says quit. Pretty simple.
	 *          Not used yet.
	 */
	{
		if (scanMan.next().equalsIgnoreCase("quit")) {
			return true;
		}
		return false;
	}

	private Location parsingInts() {
		/** @returns array of four text inputs. */
		return new Location(scanMan.nextInt() - 1, scanMan.nextInt() - 1);
	}

	protected int pieces(char c)/**
	 * @returns the number of pieces of a given
	 *          character left.
	 */
	{
		int count = 0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (theBoard[x][y].getPiece() == c) {
					count++;
				}
			}
		}
		return count;
	}

	private int[] arrayFuser(int[] a1, int[] a2)// combines 2 int arrays into
												// one. Not used...yet.
	{
		int[] returned = new int[a1.length + a2.length];
		for (int x = 0; x < a1.length; x++) {
			returned[x] = a1[x];
		}

		for (int x = a1.length; x < a1.length + a2.length; x++) {
			returned[x] = a2[x - a1.length];
		}
		return returned;
	}

	public void usersDoThings()// what the user gets. Basically, text input.
	{
		Location from = parsingInts();
		Location to = parsingInts();
		allTheJumps(from, to); // I'm gonna have to make this a bit more
								// complicated.
	}

	/*
	 * public void getPlayers()//gets the players' names. { Scanner scanMan =
	 * new Scanner(System.in); System.out.println("What is player 1's name?");
	 * player1 = scanMan.nextLine();
	 * System.out.println("What is player 2's name?"); player2 =
	 * scanMan.nextLine(); scanMan.close(); }
	 */

	// horrible function of painful reading. Read this if you want a headache.
	public String toString() {
		String theWholeDamnBoard = "";// temporary initialization. Hopefully I
										// remember to delete that one part of
										// it.
		// this is gonna be a reeeally long string.
		/*
		 * The toString method's going to get called a lot. I'm thinking about a
		 * few ways to create the board. The first is fairly one dimensional,
		 * just printing the board with the squares, and the letters inside
		 * them.
		 * 
		 * the next is creating a square class, which stores what's inside of
		 * it. It would hold 3 strings, each 3 characters, along with
		 * coordinates, which would be parsed into one long string for each
		 * side, then rinse and repeat, allowing for MEGAcheckers.\ actually,
		 * since the bottom and top are the same, I can just repeat those.
		 * 
		 * also, that means more classes. So I like it.
		 */

		// there's a better way to do this.

		// I changed it.

		for (int y = 0; y < FILE; y++) {
			for (int x = 0; x < RANK; x++) {
				theWholeDamnBoard += "|===|";// top thing.
			}

			theWholeDamnBoard += "\n";

			for (int x = 0; x < RANK; x++)// and now using lots of string.
											// THere's a thing we did in class
											// taht I should have used called
											// stringbuffer. I'll use that when
											// I realize this was dumb, but
											// until then, this sin't cahngeing.
			{
				theWholeDamnBoard += theBoard[x][y];
			}

			theWholeDamnBoard += " " + (y + 1);

			theWholeDamnBoard += "\n";
		}

		for (int y = 0; y < RANK; y++) {
			theWholeDamnBoard += "|===|";
		}
		theWholeDamnBoard += "\n";
		for (int y = 0; y < RANK; y++)
			theWholeDamnBoard += String.format("%3d  ", (y + 1));
		theWholeDamnBoard += "  <--put this number in first.\n";

		return theWholeDamnBoard;
	}

}
