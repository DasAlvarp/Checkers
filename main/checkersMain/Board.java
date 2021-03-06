package checkersMain;

import Utils.LocationManipulation.Location;
import Utils.Square;
import player.Player;
import player.PlayerDumRobit;

public class Board
{

    // all the squares.


    protected boolean showStuff;
    boolean ats = true;// if there are ats or dollars left.
    boolean dollas = true;

    public int atNum;
    public int dollaNum;

    private int moveMax = 500;

    protected int timeSinceJump = 0;// for 5k move without capture draw rule.

    static int RANK = 8;
    static int FILE = 8;
    protected char playingChar = '$';

    private int playerTurn = 0;

    protected Square[][] theBoard = new Square[FILE][RANK];


    Player p1, p2;

    protected boolean isRunning = true;


    public Board(Player pl1, Player pl2, boolean show)
    {
        p1 = pl1;
        p2 = pl2;


        int dum = 0;
        if(p1.isDum())
        {
            dum++;
        }
        if(p2.isDum())
        {
            dum++;
        }

        moveMax = 100 + 200 * dum;


        showStuff = show;
    }


    public int runs()
    /**
     * @returns if function is running, and if not, the winner,
     *          etc...
     */
    {
        if (timeSinceJump > 500)
        {
            isRunning = false;
        }

        if(isRunning == false)
        {
            if(atNum - dollaNum == 0)
            {
                return -1;
            }
            else if(atNum > dollaNum)
            {
                return 2;
            }
            else
            {
                return 1;
            }
        }

        if (isRunning && ats && dollas)
        {
            return 0;
        } else if (!ats)
        {

            return 1;
        } else if (!dollas)
        {
            return 2;
        } else
        {
            return -1;
        }
    }

    public void setBoard()// Fills theBoard with starting coords.
    {
        ats = true;
        dollas = true;
        timeSinceJump = 0;
        // getPlayers();

        for (int r = 0; r < FILE; r++)
        {
            for (int f = 0; f < RANK; f++)
            {
                if ((f + r) % 2 == 1)// checking the color of the square. Not
                // sure which color, but I can change it
                // easily.
                {
                    if (r < 3)// pretty sure it's the right rank?
                    {
                        theBoard[f][r] = new Square('@');

                    } else if (r > 4)// see r < 3.
                    {
                        theBoard[f][r] = new Square('$');
                    } else// blank squares.
                    {
                        theBoard[f][r] = new Square();
                    }
                } else// blank squares.
                {
                    theBoard[f][r] = new Square();
                }

            }
        }
    }

    private Square findPiece(Location loc)/**
     * @returns square at given
     *          coordinates..
     */
    {
        /** @returns the square at point c1, c2 */
        return theBoard[loc.getX()][loc.getY()];
    }

    private void incrementTurn()// see name. It increments the turn.
    {
        playerTurn += 1;
        playerTurn %= 2;
        if (playerTurn == 0)
        {
            playingChar = '$';
        } else
        {
            playingChar = '@';
        }
    }


    /**
     * fuction to make both sides do their turn.
     */
    public void makeMoves()
    {
        if (showStuff == true)
        {
            System.out.println(toString());
        }
        isRunning = p1.canMove(theBoard, '$');
        theBoard = p1.move(theBoard, '$');
        maintenence();
        incrementTurn();
       // System.out.println(timeSinceJump);
        isRunning = p2.canMove(theBoard, '@');

        if (runs() == 0)
        {
            if (showStuff == true)
            {
                System.out.println(toString());
            }

            theBoard = p2.move(theBoard, '@');
            incrementTurn();
            maintenence();
        }
    }


    /**
     * does general maintentence on stuff.
     */
    public void maintenence()// well, kind of what it sounds like. Kings pieces,
    // checks for draws, etc...
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

        int tempDollas = dollaNum;
        int tempAts = atNum;

        dollaNum = 0;
        atNum = 0;
        boolean at = false;// making sure the game's not over.
        boolean cash = false;
        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {

                if (theBoard[x][y].getPiece() == '@')
                {
                    at = true;
                    atNum++;
                } else if (theBoard[x][y].getPiece() == '$')
                {
                    cash = true;
                    dollaNum++;
                }

            }
        }
        ats = at;
        dollas = cash;

        if (dollaNum != tempDollas || atNum != tempAts)
        {
            timeSinceJump = 0;
        } else
        {


            timeSinceJump++;
        }

    }


    // /SCANNER SECTION! This probably could be another class.



/*
    private boolean stahp()/**
	 * @returns true if somebody says quit. Pretty simple.
	 *          Not used yet.
	 *
	{
		if (scanMan.next().equalsIgnoreCase("quit")) {
			return true;
		}
		return false;
	}


*/
















	/*
     * public void getPlayers()//gets the players' names. { Scanner scanMan =
	 * new Scanner(System.in); System.out.println("What is player 1's name?");
	 * player1 = scanMan.nextLine();
	 * System.out.println("What is player 2's name?"); player2 =
	 * scanMan.nextLine(); scanMan.close(); }
	 */

    // horrible function of painful reading. Read this if you want a headache.
    public String toString()
    {
        String theWholeDamnBoard = "";// temporary initialization. Hopefully I
        // remember to delete that one part of
        // it.
        // this is gonna be a reeeally long string.
		/*
		 * The toString method's going to get called a lot. I'm thinking about a
		 * few ways to create the board. The first is fairly one dimensional,
		 * just printing the board with the squares, and the letters inside
		 * them.
		 * 
		 * the next is creating a square class, which stores what's inside of
		 * it. It would hold 3 strings, each 3 characters, along with
		 * coordinates, which would be parsed into one long string for each
		 * side, then rinse and repeat, allowing for MEGAcheckers.\ actually,
		 * since the bottom and top are the same, I can just repeat those.
		 * 
		 * also, that means more classes. So I like it.
		 */

        // there's a better way to do this.

        // I changed it.


        for (int y = 0; y < FILE; y++)
        {
            for (int x = 0; x < RANK; x++)
            {
                theWholeDamnBoard += "|===|";// top thing.
            }

            theWholeDamnBoard += "\n";

            for (int x = 0; x < RANK; x++)// and now using lots of string.
            // THere's a thing we did in class
            // taht I should have used called
            // stringbuffer. I'll use that when
            // I realize this was dumb, but
            // until then, this sin't cahngeing.
            {
                theWholeDamnBoard += theBoard[x][y];
            }

            theWholeDamnBoard += " " + (y + 1);

            theWholeDamnBoard += "\n";
        }

        for (int y = 0; y < RANK; y++)
        {
            theWholeDamnBoard += "|===|";
        }
        theWholeDamnBoard += "\n";
        for (int y = 0; y < RANK; y++)
        {
            theWholeDamnBoard += String.format("%3d  ", (y + 1));
        }
        theWholeDamnBoard += "  <--put this number in first.\n";

        return theWholeDamnBoard;
    }

}
