package player;

import Utils.EvalStruct;
import Utils.Square;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {





    @Override
    public Square[][] move(Square[][] Board, char c) {//default depth...
        EvalStruct main = new EvalStruct(1, Board, c);
        //System.out.println(main.getEval());
        return main.getBestMove();
    }
}
