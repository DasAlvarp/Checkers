package player;

import Utils.*;
import Utils.LocationManipulation.Location;
import Utils.LocationManipulation.LocationSet;
import Utils.LocationManipulation.MoveAndEvaluation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {





    @Override
    public Square[][] move(Square[][] Board, char c) {//default depth...
        EvalStruct main = new EvalStruct(1, Board, c);
        return main.bestMove;
    }


}
