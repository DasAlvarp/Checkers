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

    /**
     *
     * @return all legal moves
     */
    public LocationSet[] legalMoves(Square[][] board) {

        Location[] canMove = p.getPieces(board, player);
        LocationSet[] destinations = new LocationSet[canMove.length];

//all squares and anywhere they could dream of landing
        for (int x = 0; x < canMove.length; x++) {
            destinations[x] = new LocationSet(canMove[x], null);
            for (int d = 0; d < 4; d++) {
                for (int r = 0; r < 2; r++) {
                    switch (d) //gets all the possible moves for each one and stores them in the destinations array.
                    {
                        case 0:
                            Location temp = new Location(canMove[x].getX() + r + 1, canMove[x].getY() + r + 1);
                            if (p.canJump(destinations[x].getStart(), temp, board, player)) {
                                destinations[x].addEnd(temp);

                            }
                            break;
                        case 1:
                            Location tem = new Location(canMove[x].getX() + r + 1, canMove[x].getY() - r - 1);
                            if (p.canJump(destinations[x].getStart(), tem, board, player)) {

                                destinations[x].addEnd(tem);
                            }
                            break;
                        case 3:
                            Location te = new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1);
                            if (p.canJump(destinations[x].getStart(), te, board, player)) {
                                destinations[x].addEnd(te);
                            }
                            break;
                        case 4:
                            Location t = new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1);
                            if ((p.canJump(destinations[x].getStart(), t, board, player))) {
                                destinations[x].addEnd(t);
                            }
                            break;
                    }
                }
            }
        }
        return destinations;
    }


    /**
     * @param deep-depth    of recursion..
     * @param board-board   state to be messed with..
     * @param c-perspective of person to move.
     */
    public EvalStruct(int deep, Square[][] board, char c) {
        System.out.println("Making evaluation structure. Depth: " + deep);
        depth = deep;
        boardState = board;
        player = c;


        if (depth > 0) {
            LocationSet[] legalMoves = legalMoves(boardState);


            for (int x = 0; x < legalMoves.length; x++) {
                for (int y = 0; y < legalMoves[x].destinationNum(); y++) {
                    otherEvals.add(new EvalStruct(deep - 1, p.kinging(p.jumpThings(legalMoves[x].getStart(), legalMoves[x].getIndex(y), boardState)), p.notPlaying(c)));
                }

            }

        }


        if (this.depth == 0) {
            eval = -1 * evaluate(boardState, c);
            bestMove = boardState;

        } else {
            eval = otherEvals.get(0).eval;
            System.out.println("Eval is: " + eval);
            bestMove = otherEvals.get(0).boardState;
            for (int x = 1; x < otherEvals.size(); x++) {
                if (otherEvals.get(x).eval > eval) {
                    eval = otherEvals.get(x).eval;
                    bestMove = otherEvals.get(x).boardState;
                    System.out.println("Eval is: " + eval);
                }
            }
        }

    }


    /**
     * @param board board state to be evaluated.
     * @param c     character of player
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