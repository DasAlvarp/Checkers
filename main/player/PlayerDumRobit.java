package player;

import Utils.LocationManipulation.Location;
import Utils.LocationManipulation.LocationSet;
import Utils.Square;
import Utils.Utility;

import java.util.Random;

/**
 * Created by alvaro on 10/24/14.
 */
public class PlayerDumRobit extends Player

{
    Random randy = new Random();
    Utility use = new Utility();

    @Override
    public Square[][] move(Square[][] Board, char c) {
        int radius = 0;
        int dx = 0;
        int dy = 0;

        Square[][] bEdit = Board;

        Location[] canMove = getPieces(Board, c);
        System.out.println(canMove.length);
        LocationSet[] destinations = new LocationSet[canMove.length];


        for (int x = 0; x < canMove.length; x++)
        {
            destinations[x] = new LocationSet(canMove[x], null);
            for (int d = 0; d < 4; d++) {
                for (int r = 0; r < 2; r++) {

                    switch (d) //gets all the possible moves for each one and stores them in the destinations array.
                    {
                        case 0:
                            destinations[x].addEnd(new Location(canMove[x].getX() + r + 1, canMove[x].getY() + r + 1));
                            break;
                        case 1:
                            destinations[x].addEnd(new Location(canMove[x].getX() + r + 1, canMove[x].getY() - r - 1));
                            break;
                        case 2:
                            destinations[x].addEnd(new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1));
                            break;
                        case 3:
                            destinations[x].addEnd(new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1));
                            break;
                    }
                }
            }
        }

        Boolean hasMoved = false;
        while (destinations != null ) {
            int starts = 0;
            if(destinations.length > 1)
                starts = randy.nextInt(destinations.length);

            while (destinations[starts].destinationNum() > 0)
            {
                int index = randy.nextInt(destinations[starts].destinationNum());

                if(canJump(destinations[starts].getStart(), destinations[starts].getIndex(index), bEdit, c))
                {
                    return jumpThings(destinations[starts].getStart(), destinations[starts].getIndex(index), bEdit);
                }
                else
                {
                    destinations[starts].removeIndex(index);
                }
            }

            destinations = use.deleteIndex(destinations, starts);
        }



        System.out.println("this shouldn't ever happen, unless the game is drawn.");
        return bEdit;
    }
}
