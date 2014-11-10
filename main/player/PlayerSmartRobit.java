package player;

import Utils.EvalStruct;
import Utils.Square;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {





    @Override
    public Square[][] move(Square[][] board, char c) {//default depth...
        System.out.println("smart moving.");
        Square[][] pureBoard = new Square[8][8];
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                pureBoard[x][y] = board[x][y];
            }
        }
        System.out.println("smart moving.");


        EvalStruct main = new EvalStruct(6, board, c);
        System.out.println("smart moving.");

        //System.out.println(main.getEval());

        System.out.println(main.getMoveCoords()[0] + ", " + main.getMoveCoords()[1]);
        System.out.println("smart moving.");

        return jumpThings(main.getMoveCoords()[0], main.getMoveCoords()[1], pureBoard);
    }
}
