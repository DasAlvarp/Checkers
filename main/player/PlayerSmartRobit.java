package player;

import Utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Alvarp on 10/30/2014.
 */
public class PlayerSmartRobit extends Player {


    Random randy = new Random();
    Utility use = new Utility();

    public double evaluate(Square[][] board, char c) {
        double score = 0;
        /*
        so this function evaluates a positoin. It starts with material, counting a king as 3 normal pieces, as well as calculating the forward-ness of each of the pieces.
         */

        score += pieces(c, board);

        if (score == 0) {
            return -100;
        }

        score -= pieces(notPlaying(c), board);

        if (pieces(notPlaying(c), board) == 0) {
            return 100;
        }

        Location[] myPieces = getPieces(board, c);

        if (getDirection(c) == 1) {

            for (int x = 0; x < myPieces.length; x++) {
                score += (double) myPieces[x].getY() / 8;
            }
        } else {
            for (int x = 0; x < myPieces.length; x++) {
                score += (double) (8 - myPieces[x].getY()) / 8;
            }
        }


        return score;
    }


    @Override
    public Square[][] move(Square[][] Board, char c) {
        return move(Board, c, 6);
    }


    public Square[][] move(Square[][] board, char c, int depth) {


        if (depth == 0) {
            return board;//base case.
        }
        int radius = 0;
        int dx = 0;
        int dy = 0;

        Square[][] bEdit = kinging(board);

        Location[] canMove = getPieces(board, c);
        System.out.println(canMove.length);
        LocationSet[] destinations = new LocationSet[canMove.length];

//all squares and anywhere they could dream of landing
        for (int x = 0; x < canMove.length; x++) {
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
                        case 3:
                            destinations[x].addEnd(new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1));
                            break;
                        case 4:
                            destinations[x].addEnd(new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1));
                            break;
                    }
                }
            }
        }

//setting up all the LEGAL moves.
        Boolean hasMoved = false;
        LocationSet[] legalMoves = new LocationSet[destinations.length];

        int itNum = 0;

        while (!hasMoved && destinations != null && destinations.length > 0) {
            int starts = randy.nextInt(destinations.length);

            if (destinations[starts].getStart() != null) {
                legalMoves[itNum] = new LocationSet(destinations[starts].getStart());
            }

            while (destinations[starts].destinationNum() > 0) {
                int index = randy.nextInt(destinations[starts].destinationNum());

                if (canJump(destinations[starts].getStart(), destinations[starts].getIndex(index), bEdit, c)) {
                    legalMoves[itNum].addEnd(destinations[starts].getIndex(index));
                }

                destinations[starts].removeIndex(index);

            }

            destinations = use.deleteIndex(destinations, starts);
            itNum++;
        }


        Square[][] testBoard = bEdit;
        double eval = -100;
        double nuEval;

        ArrayList<MoveAndEvaluation> movesWithEval = new ArrayList<MoveAndEvaluation>();
//evaluate board positions with all legal moves, then recurse.
        for (int s = 0; s < legalMoves.length; s++) {
            for (int d = 0; d < legalMoves[s].destinationNum(); d++) {
                System.out.println(depth + ", " + eval);
                testBoard = kinging(jumpThings(legalMoves[s].getStart(), legalMoves[s].getEnd(d), testBoard));
                if(depth != 0)
                    testBoard = move(testBoard, notPlaying(c), depth - 1);
                nuEval = evaluate(testBoard, c);
                movesWithEval.add(new MoveAndEvaluation(legalMoves[s].getStart(), legalMoves[s].getEnd(d), nuEval));
                if (nuEval > eval && depth % 2 == 1) {
                    eval = nuEval;
                } else if (nuEval < eval && depth % 2 == 0) {

                    eval = -nuEval;
                }
            }

        }


        Iterator<MoveAndEvaluation> it = movesWithEval.iterator();
        Location bestBet[] = new Location[2];
        eval = -100;
        while (it.hasNext())
        {
           MoveAndEvaluation temp = it.next();
            if(temp.getEval() > eval)
            {
                eval = temp.getEval();
                bestBet = temp.getDirections();
            }

        }


        return jumpThings(bestBet[0], bestBet[1], bEdit);


    }


    private Square[][] kinging(Square[][] theBoard) {
        for (int x = 0; x < 8; x++)// kinging.
        {
            if (theBoard[x][7].getPiece() == '@') {
                theBoard[x][7].king();
            } else if (theBoard[x][0].getPiece() == '$') {
                theBoard[x][0].king();
            }
        }
        return theBoard;
    }

}
