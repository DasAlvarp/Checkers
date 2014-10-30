package checkersMain;

import Utils.Location;

import java.util.Random;

public class AutoBoard extends Board {

	Random randy = new Random();

	private int radius, dx, dy, initX, initY;

	private int[][] winsBy = new int[2][12];

	private int[] playGame()/**
	 * @returns an int array of winner, and then
	 *          difference in pieces.
	 */
	{
		setBoard();

		int state = 0;

		while (state == 0) {
			Location loc1 = new Location(0, 0);
			Location loc2 = new Location(0, 0);

			int counter = 0;

			do {
				counter++;

				if (counter == 2000)// if this tries really hard but can't find
									// a move, returns -1. It's a big number,
									// shouldn't happen too much..
				{
					return new int[] { -1, 0 };
				}

				do {
					initX = randy.nextInt(8);
					initY = randy.nextInt(4) * 2 + ((initX + 1) % 2); // sets
																		// the
																		// initial
																		// x and
																		// y of
																		// the
																		// location.
																		// Makes
																		// sure
																		// one's
																		// odd,
																		// and
																		// other
																		// is
																		// even.
																		// THere
																		// are
																		// better
																		// ways,
																		// but
																		// this
																		// works
																		// nicely.
				} while ((initX + initY) % 2 != 1
						|| theBoard[initX][initY].getPiece() != playingChar);

				do {
					radius = randy.nextInt(4) + 1;// sets radius until it's
													// legal for x, then applies
													// for x
					dx = randy.nextInt(2) * 2 - 1;
				} while (Math.abs(dx * radius + initX) > 7
						|| (dx * radius + initX) < 0);

				do {// same with y. Uses same radius. NOte that there will
					// always be some y that fits in the radius.
					dy = randy.nextInt(2) * 2 - 1;
				} while (Math.abs(dy * radius + initY) > 7
						|| dy * radius + initY < 0);

				loc1.setX(initX);
				loc1.setY(initY);

				loc2.setX(initX + dx * radius);
				loc2.setY(initY + dy * radius);
				// properly setting locations

			} while (!canJump(loc1, loc2));// while jump is legal.

			allTheJumps(loc1, loc2);// jumps
			maintenence(); // increments turns, etc
			state = runs();// was used in debugging, and it's nice to keep
							// around.

		}
		return new int[] { state, pieces(notPlaying(playingChar)) };
	}

	public int[] repeat(int x) /**
	 * @returns an int array of: p1 wins, draws, p2
	 *          wins, total score difference, in that order.
	 */
	{

		// emptying winsBy for histogram and stuff...
		for (int q = 0; q < 2; q++) {
			for (int y = 0; y < 12; y++) {
				winsBy[q][y] = 0;
			}
		}
		montyThisSeemsStrange = true;// get rid of more annoying text.

		int[] results = { 0, 0, 0, 0 };

		for (int i = 0; i < x; i++) {
			int[] r = playGame();

			if (r[0] == 1) {
				results[0]++;
				results[3] += r[1];
				winsBy[0][r[1]]++;
			} else if (r[0] == -1)// p1 wins, etc..
			{
				results[1]++;
			} else {
				results[0]++;
				results[3] += r[1];
				winsBy[1][r[1]]++;
				results[2]++;
				results[3] -= r[1];
			}
			System.out.println("On trial # " + (i + 1) + "results so far: "
					+ results[0] + "/" + results[1] + "/" + results[2] + " +- "
					+ results[3]);

		}
		return results;
	}

	public int[][] getWinsBy() {
		return winsBy;
	}
}
