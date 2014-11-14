package player;

import Utils.LocationManipulation.Location;
import Utils.Square;

import java.util.Scanner;

public class PlayerHuman extends Player {
    Scanner scanMan = new Scanner(System.in);
    public PlayerHuman(char c)
    {
        super(c);
    }


    private Location parsingInts() {
        //	/** @returns array of four text inputs. */
        return new Location(scanMan.nextInt() - 1, scanMan.nextInt() - 1);
    }

    @Override
    public Square[][] move(Square[][] theBoard, char c) {
        Location l1 = parsingInts();
        while (theBoard[l1.getX()][l1.getY()].getPiece() != c) {
            System.out.println("That's not one of your pieces. Give the coords of your pieces.");
            l1 = parsingInts();
        }
        Location l2 = parsingInts();
        while (!canJump(l1, l2,theBoard, c)) {
            System.out.println("That's not a legal move. Give a legal move.");
            l2 = parsingInts();
        }

        return jumpThings(l1, l2, theBoard);


    }


}
