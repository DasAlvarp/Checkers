package player;

import Utils.Square;
import Utils.Location;

public class Player
{
	private char player;
	private String playerName;






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



    //returns location array of all pieces on the board.
	public Location[] getPieces(Square[][] board, char p)
    {
        Location[] pieces = new Location[12];
        int count = 0;
        for(int x = 0; x < 8; x++)
        {
            for(int y = 0; y < 8; y++)
            {
                if(board[x][y].getPiece() == p)
                {
                    pieces[count] = new Location(x, y);
                    count++;
                }
            }
        }


        Location[] trRet = new Location[count];

        for(int x = 0; x < count; x++)
        {
            trRet[x] = pieces[x];
        }
        return trRet;

    }

    public char notPlaying(char s) {
        if (s == '@')
            return '$';
        else
            return '@';
    }



    private int direction(char c)/**
     * @returns direction (forward or backward) that
     *          a piece moves by default.
     */
    {
        if (c == '@')
            return 1;
        else
            return -1;
    }

    protected int pieces(char c, Square[][] theBoard)/**
     * @returns the number of pieces of a given
     *          character left.
     */
    {
        int count = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (theBoard[x][y].getPiece() == c) {
                    count++;
                }
            }
        }
        return count;
    }


    protected int getDirection(char c)
    {
        return direction(c);
    }


    protected boolean canJump(Location first, Location second, Square[][] theBoard, char playingChar)/**
     * @returns whether
     *          jumping from first to second is kosher.
     */
    {
        int dirX = first.getX() - second.getX();
        int dirY = first.getY() - second.getY();
        dirX = dirX / Math.abs(dirX);
        dirY = dirY / Math.abs(dirY);

        if(second.getX() > 7 || second.getY() > 7 || second.getX() < 0 || second.getY() < 0 )
        {
            return false;
        }

        if (theBoard[first.getX()][first.getY()].isKing()
                || -1 * dirY == direction(playingChar)) {
            if (Math.abs(first.getX() - second.getX()) < 4
                    && Math.abs(first.getX() - second.getX()) == Math.abs(first.getY() - second.getY())) {

                for (int x = 1; x < Math.abs(second.getX() - first.getX()); x++) {
                    if (theBoard[first.getX() - dirX * x][first.getY() - dirY * x].getPiece() != notPlaying(playingChar)) {
                        return false;
                    }
                }
                if (theBoard[second.getX()][second.getY()].getPiece() == ' ') {
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





    protected Square[][] jumpThings(Location from, Location to, Square[][] board)// jumps over one,
    // deletes middle, and
    // teleports
    {
        Square[][] theBoard = teleport(from, to, board);
        return deleteBetween(from, to, theBoard);
    }



}
