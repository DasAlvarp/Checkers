package player;

import Utils.LocationManipulation.Location;
import Utils.LocationManipulation.LocationSet;
import Utils.Square;

public class Player
{
    private char player;
    private String playerName;


    public Player(char c)
    {
        player = c;
    }


    public char getPlayer()
    {
        return player;
    }

    public void setPlayer(char player)
    {
        this.player = player;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }


    /**
     * @return all legal moves
     */
    public LocationSet[] legalMoves(Square[][] board, char c)
    {

        Location[] canMove = getPieces(board, c);
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
                            if (canJump(destinations[x].getStart(), temp, board, c))
                            {
                                destinations[x].addEnd(temp);

                            }
                            break;
                        case 1:
                            Location tem = new Location(canMove[x].getX() + r + 1, canMove[x].getY() - r - 1);
                            if (canJump(destinations[x].getStart(), tem, board, c))
                            {
                                destinations[x].addEnd(tem);
                            }
                            break;
                        case 2:
                            Location te = new Location(canMove[x].getX() - r - 1, canMove[x].getY() + r + 1);
                            if (canJump(destinations[x].getStart(), te, board, c))
                            {
                                destinations[x].addEnd(te);
                            }
                            break;
                        case 3:
                            Location t = new Location(canMove[x].getX() - r - 1, canMove[x].getY() - r - 1);
                            if ((canJump(destinations[x].getStart(), t, board, c)))
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


    public boolean canMove(Square[][] board, char c)
    {
        LocationSet[] legalMoves = legalMoves(board, c);
        for (int x = 0; x < legalMoves.length; x++)
        {


            for (int y = 0; y < legalMoves[x].destinationNum(); y++)
            {
                if (legalMoves[x].getIndex(y) != null)
                {
                    return true;
                }
            }
        }
        System.out.println("nope.");
        return false;

    }


    //returns location array of all pieces on the board.
    public Location[] getPieces(Square[][] board, char p)
    {
        Location[] pieces = new Location[12];
        int count = 0;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (board[x][y].getPiece() == p)
                {
                    pieces[count] = new Location(x, y);
                    count++;
                }
            }
        }


        Location[] trRet = new Location[count];

        for (int x = 0; x < count; x++)
        {
            trRet[x] = pieces[x];
        }
        return trRet;

    }

    /**
     * @param s player playing
     * @return player not playing.
     */
    public char notPlaying(char s)
    {
        if (s == '@')
        {
            return '$';
        } else
        {
            return '@';
        }
    }


    private int direction(char c)/**
     * @returns direction (forward or backward) that
     *          a piece moves by default.
     */
    {
        if (c == '@')
        {
            return 1;
        } else
        {
            return -1;
        }
    }

    public int pieces(char c, Square[][] theBoard)/**
     * @returns the number of pieces of a given
     *          character left.
     */
    {
        int count = 0;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                if (theBoard[x][y].getPiece() == c)
                {
                    count++;
                }
            }
        }
        return count;
    }


    public int getDirection(char c)
    {
        return direction(c);
    }


    public boolean canJump(Location first, Location second, Square[][] theBoard, char playingChar)/**
     * @returns whether
     *          jumping from first to second is kosher.
     */
    {
        int dirX = second.getX() - first.getX();
        int dirY = second.getY() - first.getY();
        dirX = dirX / Math.abs(dirX);
        dirY = dirY / Math.abs(dirY);

        if (second.getX() > 7 || second.getY() > 7 || second.getX() < 0 || second.getY() < 0)
        {
            return false;
        }

        if (theBoard[first.getX()][first.getY()].isKing() || dirY == direction(playingChar))
        {
            if (Math.abs(first.getX() - second.getX()) < 4 && Math.abs(first.getX() - second.getX()) == Math.abs(first.getY() - second.getY()))
            {

                for (int x = 1; x < Math.abs(second.getX() - first.getX()); x++)
                {
                    if (theBoard[first.getX() + dirX * x][first.getY() + dirY * x].getPiece() != notPlaying(playingChar))
                    {
                        return false;
                    }
                }
                if (theBoard[second.getX()][second.getY()].getPiece() == ' ')
                {
                    return true;
                }
            }
        }
        return false;
    }

    public Square[][] move(Square[][] board, char c)
    {
        return board; //you'd better override this method.
    }


    private Square[][] delete(int one, int two, Square[][] board)// replaces coordinates piece on
    // theBoard with ' '
    {
        Square[][] theBoard = board;
        theBoard[one][two] = new Square();
        return theBoard;
    }


    /**
     * @param theBoard to be edited
     * @return board with properly king'd pieces.
     */
    public Square[][] kinging(Square[][] theBoard)
    {
        for (int x = 0; x < 8; x++)// kinging.
        {
            if (theBoard[x][7].getPiece() == '@')
            {
                theBoard[x][7].king();
            } else if (theBoard[x][0].getPiece() == '$')
            {
                theBoard[x][0].king();
            }
        }
        return theBoard;
    }

    private Square[][] teleport(Location from, Location to, Square[][] board)// Removes a piece from one
    // place to other
    {
        Square[][] theBoard = board;
        theBoard[to.getX()][to.getY()] = theBoard[from.getX()][from.getY()];
        theBoard = delete(from.getX(), from.getY(), theBoard);
        return theBoard;
    }

    private Square[][] deleteBetween(Location first, Location second, Square[][] board)// deletes all
    // the squares
    // in a diagonal
    // between any
    // two square.
    {
        Square[][] theBoard = board;
        int dirX = first.getX() - second.getX();
        int dirY = first.getY() - second.getY();
        dirX = dirX / Math.abs(dirX);
        dirY = dirY / Math.abs(dirY);
        for (int x = 1; x < Math.abs(second.getX() - first.getX()); x++)
        {
            theBoard = delete(first.getX() - dirX * x, first.getY() - dirY * x, theBoard);
        }
        return theBoard;
    }


    public Square[][] jumpThings(Location from, Location to, Square[][] board)// jumps over one,
    // deletes middle, and
    // teleports
    {
        board = teleport(from, to, board);
        return deleteBetween(from, to, board);
    }


}
