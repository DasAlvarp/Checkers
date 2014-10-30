package player;

import Utils.Location;
import Utils.Square;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {
    public double evaluate(Square[][] board, char c)
    {
        double score = 0;
        /*
        so this function evaluates a positoin. It starts with material, counting a king as 3 normal pieces, as well as calculating the forward-ness of each of the pieces.
         */

        score += pieces(c, board);

        if(score == 0)
        {
            return -100;
        }

        score -= pieces(notPlaying(c), board);

        if(pieces(notPlaying(c), board) == 0)
        {
            return 100;
        }

        Location[] myPieces = getPieces(board, c);

        if(getDirection(c) == 1) {

            for (int x = 0; x < myPieces.length; x++) {
                score += (double)myPieces[x].getY() / 8;
            }
        }
        else
        {
            for (int x = 0; x < myPieces.length; x++) {
                score += (double)(8 - myPieces[x].getY()) / 8;
            }
        }







        return score;
    }
}
