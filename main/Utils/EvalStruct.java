package Utils;

import Utils.LocationManipulation.Location;
import Utils.LocationManipulation.LocationSet;
import player.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alvaro on 11/9/14.
 */
public class EvalStruct
{

    public double getEval()
    {
        return eval;
    }

    public void setEval(double eval)
    {
        this.eval = eval;
    }

    public Square[][] getBoardState()
    {
        return boardState;
    }

    public void setBoardState(Square[][] boardState)
    {
        this.boardState = boardState;
    }

    public Square[][] getBestMove()
    {
        return bestMove;
    }

    public void setBestMove(Square[][] bestMove)
    {
        this.bestMove = bestMove;
    }

    public ArrayList<EvalStruct> getOtherEvals()
    {
        return otherEvals;
    }

    public void setOtherEvals(ArrayList<EvalStruct> otherEvals)
    {
        this.otherEvals = otherEvals;
    }

    public char getPlayer()
    {
        return player;
    }

    public void setPlayer(char player)
    {
        this.player = player;
    }

    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    private double eval;
    private Square[][] boardState;
    private Square[][] bestMove;
    private ArrayList<EvalStruct> otherEvals = new ArrayList<EvalStruct>();
    private char player;
    private int depth;
    private ArrayList<Location[]> moveList = new ArrayList<Location[]>();
    private Location[] moveCoords;


    Player p;


    /**
     * @return all legal moves
     */
    public LocationSet[] legalMoves(Square[][] board, char c)
    {

        Location[] canMove = p.getPieces(board, c);
        LocationSet[] destinations = new LocationSet[canMove.length];

        //all squares and anywhere they could dream of landing
        for (int x = 0; x < canMove.length; x++)
        {
            destinations[x] = new LocationSet(canMove[x], null);
            for (int d = 0; d < 4; d++)
            {
                for (int r = 0; r < 2; r++)
                {
                    switch (d) //gets all the possible moves for each one and stores them in the destinations array.
                    {
                        case 0:
                            Location temp = new Location(canMove[x].getX() + r + 1, canMove[x].getY() + r + 1);
                            if (p.canJump(destinations[x].getStart(), temp, board, c))
                            {
                                destinations[x].addEnd(temp);

                            }
                            break;
                        case 1:
                            Location tem = new Location(canMove[x].getX() + r + 1, canMove[x].getY() - r - 1);
                            if (p.canJump(destinations[x].getStart(), tem, board, c))
                            {
                                destinations[x].addEnd(tem);
                            }
                            break;
                        case 2:
                            Location te = new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1);
                            if (p.canJump(destinations[x].getStart(), te, board, c))
                            {
                                destinations[x].addEnd(te);
                            }
                            break;
                        case 3:
                            Location t = new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1);
                            if ((p.canJump(destinations[x].getStart(), t, board, c)))
                            {
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
    public EvalStruct(int deep, Square[][] board, char c, boolean even)
    {
        // System.out.println("Making evaluation structure. Depth: " + deep);
        p = new Player(c);
        this.depth = deep;
        this.boardState = new Square[8][8];
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                boardState[x][y] = board[x][y];
            }
        }
        this.player = c;


        if (depth > 0)
        {
            LocationSet[] legalMoves = legalMoves(boardState, player);


            for (int x = 0; x < legalMoves.length; x++)
            {
                for (int y = 0; y < legalMoves[x].destinationNum(); y++)
                {
                    Location[] moves = new Location[2];
                    moves[0] = legalMoves[x].getStart();
                    moves[1] = legalMoves[x].getIndex(y);
                    this.otherEvals.add(new EvalStruct(deep - 1, (p.jumpThings(moves[0], moves[1], boardState)), p.notPlaying(c), even));
                    moveList.add(moves);
                }

            }

        }


        if (this.depth == 0)
        {
            eval = evaluate(boardState, c, even);
            this.bestMove = boardState;

        } else
        {
            if (otherEvals == null || otherEvals.size() == 0)
            {
                eval = evaluate(boardState, c, even);
                this.bestMove = boardState;

            } else
            {
                eval = -1 * otherEvals.get(0).getEval();
                //  System.out.println("Eval is: " + eval);
                this.bestMove = otherEvals.get(0).boardState;
                this.moveCoords = moveList.get(0);
                for (int x = 1; x < otherEvals.size(); x++)
                {
                    if (-1 * otherEvals.get(x).eval < -1 * eval)
                    {
                        eval = -1 * otherEvals.get(x).getEval();
                        bestMove = otherEvals.get(x).boardState;
                        // System.out.println("Eval is: " + eval);
                        this.moveCoords = moveList.get(x);

                    }
                }
            }
        }

    }


    public Location[] getMoveCoords()
    {
        return moveCoords;
    }


    /**
     * @param board board state to be evaluated.
     * @param c     character of player
     * @returns evaluation of a board state...
     */
    public double evaluate(Square[][] board, char c, boolean even)
    {
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                board[x][y] = board[x][y];//this fixes strange issues for some reason. Programming is strange.
            }
        }
        double score = 0;
        /*
        so this function evaluates a positoin. It starts with material, counting a king as 3 normal pieces, as well as calculating the forward-ness of each of the pieces.
         */

        score += p.pieces(c, board);

        if (score == 0)
        {
            score = -100;
        }

        score -= p.pieces(p.notPlaying(c), board);

        if (p.pieces(p.notPlaying(c), board) == 0)
        {
            score = 100;
        }

        Location[] myPieces = p.getPieces(board, c);

        if (p.getDirection(c) == 1)
        {

            for (int x = 0; x < myPieces.length; x++)
            {
                if (!board[myPieces[x].getX()][myPieces[x].getY()].isKing())
                {
                    //score += (double) myPieces[x].getY() / 8;
                } else
                {
                    score += 3;
                }
            }
        } else
        {
            for (int x = 0; x < myPieces.length; x++)
            {
                if (!board[myPieces[x].getX()][myPieces[x].getY()].isKing())
                {
                    //score += (double) myPieces[x].getY() / 8;
                } else
                {
                    score += 3;
                }
            }
        }


        Location[] notMyPieces = p.getPieces(board, p.notPlaying(c));

        if (p.getDirection(c) == 1)
        {

            for (int x = 0; x < notMyPieces.length; x++)
            {
                if (!board[notMyPieces[x].getX()][notMyPieces[x].getY()].isKing())
                {
                    //score -= (double) notMyPieces[x].getY() / 8;
                } else
                {
                    score -= 3;
                }
            }
        } else
        {
            for (int x = 0; x < notMyPieces.length; x++)
            {
                if (!board[notMyPieces[x].getX()][notMyPieces[x].getY()].isKing())
                {
                    //score -= (double) notMyPieces[x].getY() / 8;
                } else
                {
                    score -= 3;
                }
            }
        }


        if(even == true)
        {
            return 0- score;
        }
        return score;
    }
}