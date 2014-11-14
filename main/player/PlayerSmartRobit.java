package player;

import Utils.EvalStruct;
import Utils.Square;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player
{


    private int depth;

    public PlayerSmartRobit(char c, int x)
    {
        super(c);
        depth = x;
    }

    public PlayerSmartRobit(char c)
    {
        this(c, 6);
    }


    @Override
    public Square[][] move(Square[][] board, char c)
    {//default depth...
        Square[][] pureBoard = new Square[8][8];
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                pureBoard[x][y] = board[x][y];
            }
        }


        EvalStruct main = new EvalStruct(depth, board, c);

        //System.out.println(main.getEval());

        System.out.println(main.getMoveCoords()[0] + ", " + main.getMoveCoords()[1]);

        return jumpThings(main.getMoveCoords()[0], main.getMoveCoords()[1], pureBoard);
    }
}
