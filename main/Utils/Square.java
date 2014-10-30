/**
 * @author Alvaro Gudiswitz
 * 
 */
package Utils;

public class  Square {

	String mid;
	char piece; // this way, I could also make chess pieces go here if I wanted
				// to.
	boolean k = false;

	// how to make a square.
	public Square(char p, boolean kin)/** @returns square that does stuff. */
	// kings not implemented yet.
	{
		k = kin;
		piece = p;
		mid = String.format("%1s %1c %1s", "|", p, "|");
	}

	public Square(char p)/** @returns square with piece 'p' */
	{
		this(p, false);
	}

	public Square()/** @returns square with ' ' as piece. */
	{
		this(' ');// overloading, bwahahahahahaha!
	}

	public void king()// kings piece.kk+ is
	{
		k = true;
		if (piece == '@')
			mid = String.format("%1s %1c %1s", "|", '#', "|");
		else if (piece == '$')
			mid = String.format("%1s %1c %1s", "|", '&', "|");

	}

	// Getters and setters. Did not type this.

	public boolean isKing() {
		return k;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}

	public String toString() {
		return mid;
	}

}
