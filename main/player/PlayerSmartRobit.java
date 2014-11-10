package player;

import Utils.EvalStruct;
import Utils.Square;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {





    @Override
    public Square[][] move(Square[][] board, char c) {//default depth...
        Square[][] thBoard = board;
        EvalStruct main = new EvalStruct(1, board, c);
        //System.out.println(main.getEval());

        System.out.println(main.getMoveCoords()[0] + ", " + main.getMoveCoords()[1]);
        return jumpThings(main.getMoveCoords()[0], main.getMoveCoords()[1], thBoard);
    }
}
