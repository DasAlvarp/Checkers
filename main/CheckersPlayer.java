import checkersMain.Board;
import player.Player;
import player.PlayerDumRobit;
import player.PlayerHuman;
import player.PlayerSmartRobit;

import java.util.Scanner;

/**
 * @author Alvaro Gudiswitz
 * @version 1.2
 */
public class CheckersPlayer {
	public static void main(String args[]) {
        Player p1;
        Player p2;

        int numReps;
        boolean showThings;
        Scanner scanMan = new Scanner(System.in);
		// this place is totally the wrong place to put some of this stuff, next
		// version probably will include a main loop class to hide crap that's
		// hiding out
		// in this thing.
		boolean runs = true;
		System.out.println("This is supposed to be a game of checkers. Because it's early in the morning, and it doesn't quite work");
		System.out.println("still, you might have to use your imagination. Anyways, to move a piece, type in the original coordinates");
		System.out.println("in some form like '1' enter '2' enter '3' enter '4' enter. SOmething painful along those lines...");
		System.out.println("If you type something in wrong, you are punished becasue it's always your fault, even when it isn't.");
		System.out.println("so...enjoy? ---1");

        System.out.println("I'm going to be gross and manually figure out everything you want.");

        System.out.println("Are you going to be in the game? Y for yes, anything else for no?");
        if(scanMan.next().equalsIgnoreCase("y"))
        {
            System.out.println("Do you have are there two humans playing?");
            if(scanMan.next().equalsIgnoreCase(("y")))
            {
                p1 = new PlayerHuman();
                p2 = new PlayerHuman();
            }
            else
            {
                System.out.println("Do you want to play against an AI?");
                if(scanMan.next().equalsIgnoreCase("Y")) {
                    System.out.println("Do you want to be player 1?");
                    if (scanMan.next().equalsIgnoreCase("Y")) {
                        p1 = new PlayerHuman();
                        p2 = new PlayerSmartRobit();
                    } else {
                        p2 = new PlayerHuman();
                        p1 = new PlayerSmartRobit();
                    }
                }
                else {
                    System.out.println("Do you want to be player 1?");
                    if (scanMan.next().equalsIgnoreCase("Y")) {
                        p1 = new PlayerHuman();
                        p2 = new PlayerSmartRobit();
                    } else {
                        p2 = new PlayerHuman();
                        p1 = new PlayerSmartRobit();
                    }
                }
            }
        }
        else
        {
            System.out.println("Do you want two smart AI's to play?");
            if(scanMan.next().equalsIgnoreCase("y"))
            {
                p1 = new PlayerSmartRobit();
                p2 = new PlayerSmartRobit();
            }
            else
            {
                System.out.println("Do you want one smart AI?");
                if(scanMan.next().equalsIgnoreCase("y"))
                {
                    System.out.println("Do you want it to be player 1?");
                    if(scanMan.next().equalsIgnoreCase("y"))
                    {
                        p1 = new PlayerSmartRobit();
                        p2 = new PlayerDumRobit();
                    }
                    else
                    {
                        p1 = new PlayerDumRobit();
                        p2 = new PlayerSmartRobit();
                    }
                }
                else
                {
                    p1 = new PlayerDumRobit();
                    p2 = new PlayerDumRobit();
                }
            }
        }

        System.out.println("Do you want it to show stuff?");
        if(scanMan.next().equalsIgnoreCase("y"))
        {
            showThings = true;
        }
        else
        {
            showThings = false;
        }

        System.out.println("how many games do you want to play?");
        numReps = scanMan.nextInt() - 1;



		Board checkers = new Board(p1, p2, showThings);

       for(int x = 0; x < numReps; x++)
       {
           checkers.setBoard();
           while (runs == true)
           {

               checkers.makeMoves();
               if(checkers.runs() == 0) {
                   //stub
               }
               else
               {
                   runs = false;
               }
/*			String in = scanMan.nextLine();
			Scanner inScan = new Scanner(in);
			inScan.useDelimiter(" ");
			if (in.equalsIgnoreCase("quit")) {
				runs = false;
				break;
			} else if (inScan.next().equalsIgnoreCase("montyMode")) {
				int[] out = robit.repeat(Integer.parseInt(in.substring(
						"montymode ".length(), in.length())));
				System.out.println(out[0] + "/" + out[1] + "/" + out[2]
						+ " with a difference of " + out[3]);
				System.out.println("player 1 trials       player 2 trials");
				for (int q = 1; q < 12; q++) {
					for (int y = 0; y < 2; y++) {
						System.out.print(robit.getWinsBy()[y][q] + "	");
					}
					System.out.println();

				}
			} else {
				System.out
						.println("you didn't type in any fancy commands, so play checkers now.");
				checkers.setBoard();
				do {
					System.out.println(checkers);
					checkers.maintenence();
				} while (checkers.runs() == 0);

			}

			inScan.close();
*/
           }

           // it's 3 AM and I'm listenign to 3AM. I'm proud of this really good
           // playlist timing.
       }

       }





}
