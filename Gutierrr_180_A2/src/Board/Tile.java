package Board;

import Pieces.Piece;

public class Tile 
{
	final private int X, Y;
	private String alliance;
	private Piece piece;
	
	public Tile(int x, int y)
	{
		X = x;
		Y = y;
	}
	
	//Setter methods
	public void setAlliance(String ally)
	{
		alliance = ally;
	}
	public void setOccupied(Piece p)
	{
		piece = p;
	}
	
	//Getter methods
	public int getX()
	{
		return X;
	}
	public int getY()
	{
		return Y;
	}
	public char getSymbol()
	{
		if (alliance == null) { 
			return '-'; 
		} else {
			return '=';
		}
	}
	public String getAlliance()
	{
		return alliance;
	}
	public Piece getOccupied()
	{
		return piece;
	}
}