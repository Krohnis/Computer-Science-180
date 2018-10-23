package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Move;

public abstract class Piece 
{
	private int X, Y, value;
	private char symbol;
	private String alliance;
	private boolean isHot, isNot;
	
	//Constructor for 'this'
	public Piece(final String ally, final int v, final int x, final int y)
	{
		X = x;
		Y = y;
		value = v;
		alliance = ally;
		if ((alliance).toUpperCase().equals("HOTWHEELS")) { 
			isHot = true; isNot = false; 
		} else { 
			isHot = false; isNot = true; 
		}
	}
	
	//Abstract for generating the moves list of 'this'
	public abstract ArrayList<Move> getMovesList(Board board, boolean isEmpty); 
	
	//Setter methods
	public void setX(int x) 
	{ 
		X = x; 
	}
	public void setY(int y) 
	{ 
		Y = y; 
	}
	public void setSymbol(char s)
	{
		symbol = s;
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
	public int getValue()
	{
		return value;
	}
	public String getAlliance()
	{
		return alliance;
	}
	public char getSymbol()
	{
		return symbol;
	}
	
	//Boolean checks for comparisons with 'this'
	public boolean isHot()
	{
		return isHot;
	}
	public boolean isNot()
	{
		return isNot;
	}
}
