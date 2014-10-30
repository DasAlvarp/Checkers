package player;
import checkersMain.Square;
import java.util.Random;

import checkersMain.Location;

/**
 * Created by alvaro on 10/24/14.
 */
public class sPlayerDumRobit extends Player
{
    Random randy = new Random();


    public Square[][] move(Square[][] Board, char c)
    {
        int radius = 0;
        int dx = 0;
        int dy = 0;

        Square[][] bEdit = Board;

        Location[] canMove = getPieces(Board);
        Location[] destinations =  new Location[canMove.length*8];

        int tot = 0;
        for(int x = 0; x < canMove.length; x++)
        {
            for(int d = 0; d < 4; d++)
            {
                for(int r = 0; r < 2; r++)
                {

                    switch (d)
                    {
                        case 0:
                            destinations[tot] = new Location(canMove[x].getX() + r + 1, canMove[x].getY() + r + 1);
                            break;
                        case 1:
                            destinations[tot] = new Location(canMove[x].getX() + r + 1, canMove[x].getY() - r - 1);
                            break;
                        case 3:
                            destinations[tot] = new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1);
                            break;
                        case 4:
                            destinations[tot] = new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1);
                            break;
                    }
                    tot++;
                }
            }
        }

        Boolean hasMoved = false;
        while (hasMoved)
        {
            int starts = randy.nextInt(canMove.length);
            int index = randy.nextInt(destinations.length);

            if(canJump(canMove[starts], destinations[index], bEdit, c);
            {

            }
        }
        return bEdit;
    }
}
