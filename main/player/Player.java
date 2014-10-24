package player;

import checkersMain.Square;

public abstract class Player {
	private char player;
	private String playerName;
	
	
	
	
	
	
	public char getPlayer() {
		return player;
	}
	public void setPlayer(char player) {
		this.player = player;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
	
	
	public abstract Square[] move(Square[] board);
}
