package Utils;

import Utils.LocationManipulation.Location;
import Utils.LocationManipulation.LocationSet;
import player.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alvaro on 11/9/14.
 */
public class EvalStruct {
    public double eval;
    public Square[][] boardState;
    public Square[][] bestMove;
    public ArrayList<EvalStruct> otherEvals = new ArrayList<EvalStruct>();
    public char player;
    public int depth;

    Random randy = new Random();
    Utility use = new Utility();
    Player p = new Player();


    public LocationSet[] legalMoves() {

        Location[] canMove = p.getPieces(boardState, player);
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
        LocationSet[] legalMoves = new LocationSet[destinations.length];

        int itNum = 0;

        while (destinations != null && destinations.length > 0) {
            int starts = randy.nextInt(destinations.length);

            if (destinations[starts].getStart() != null) {
                legalMoves[itNum] = new LocationSet(destinations[starts].getStart());
            }

            while (destinations[starts].destinationNum() > 0) {
                int index = randy.nextInt(destinations[starts].destinationNum());

                if (p.canJump(destinations[starts].getStart(), destinations[starts].getIndex(index), boardState, player)) {
                    legalMoves[itNum].addEnd(destinations[starts].getIndex(index));
                }

                destinations[starts].removeIndex(index);

            }

            destinations = use.deleteIndex(destinations, starts);
            itNum++;
        }


        return legalMoves;

    }



    /**
     *
     * @param deep-depth of stuffs.
     * @param board-board state to be messed with..
     * @param c-perspective of person to move.
     */
    public EvalStruct(int deep, Square[][] board, char c) {
        System.out.println("Making evaluation structure. Depth: " + deep);
        this.depth = deep;
        this.boardState = board;
        this.player = c;


        if (this.depth > 0) {
            LocationSet[] legalMoves = legalMoves();


            for (int x = 0; x < legalMoves.length; x++) {
                for (int y = 0; y < legalMoves[x].destinationNum(); y++) {
                    this.otherEvals.add(new EvalStruct(deep - 1, p.kinging(p.jumpThings(legalMoves[x].getStart(), legalMoves[x].getIndex(y), board)), c));
                }

            }

        }


        if (this.depth == 0) {
            eval = evaluate(boardState, c);
            this.bestMove = this.boardState;

        }
        else
        {
            this.eval = otherEvals.get(0).eval;
            this.bestMove = otherEvals.get(0).boardState;
            for (int x = 1; x < otherEvals.size(); x++) {
                if (otherEvals.get(x).eval > this.eval) {
                   this.eval = otherEvals.get(x).eval;
                   this.bestMove = otherEvals.get(x).boardState;
                }
            }
        }

    }



    /**
     * @param board board state to be evaluated.
     * @param c character of player
     * @returns evaluation of a board state...
     */
    public double evaluate(Square[][] board, char c) {
        double score = 0;
        /*
        so this function evaluates a positoin. It starts with material, counting a king as 3 normal pieces, as well as calculating the forward-ness of each of the pieces.
         */

        score += p.pieces(c, board);

        if (score == 0) {
            return -100;
        }

        score -= p.pieces(p.notPlaying(c), board);

        if (p.pieces(p.notPlaying(c), board) == 0) {
            return 100;
        }

        Location[] myPieces = p.getPieces(board, c);

        if (p.getDirection(c) == 1) {

            for (int x = 0; x < myPieces.length; x++) {
                score += (double) myPieces[x].getY() / 8;
            }
        } else {
            for (int x = 0; x < myPieces.length; x++) {
                score += (double) (8 - myPieces[x].getY()) / 8;
            }
        }


        Location[] notMyPieces = p.getPieces(board, p.notPlaying(c));

        if (p.getDirection(c) == 1) {

            for (int x = 0; x < notMyPieces.length; x++) {
                score -= (double) notMyPieces[x].getY() / 8;
            }
        } else {
            for (int x = 0; x < notMyPieces.length; x++) {
                score -= (double) (8 - notMyPieces[x].getY()) / 8;
            }
        }


        return score;
    }
}