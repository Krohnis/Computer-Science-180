package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Move;

public class Car extends Piece
{
	private final static int[] POTENTIAL_YMOVES = { -1, 0, 1 };
	
	//Constructor for 'this'
	public Car(final String alliance, final int x, final int y)
	{
		super(alliance, 0, x, y);
		char symbol;
		if ((alliance).toUpperCase().equals("HOTWHEELS")) { 
			symbol = 'c'; 
		} else { 
			symbol = 'C'; 
		}
		super.setSymbol(symbol);
	}
	
	//Generates the moves list of 'this' piece
	public ArrayList<Move> getMovesList(Board board, boolean isEmpty)
	{
		ArrayList<Move> legalMoves = new ArrayList();
		int pieceDirection;
		
		if (super.getAlliance().toUpperCase().equals("HOTWHEELS")) {
			pieceDirection = 1;
		} else {
			pieceDirection = -1;
		}
		
		for (int yMove : POTENTIAL_YMOVES) {
			int toX = super.getX() + 1;
			int toY = super.getY() + yMove;
			if (boundaryCheck(board, toX, toY)) {
				Piece piece = board.getPiece(toX, toY) ;
				try {
					if ((isEmpty || (piece == null)) && (board.getTile(toX, toY).getAlliance().equals(super.getAlliance()))) {
						legalMoves.add(new Move(board, this, toX, toY));
					} 
				} catch (Exception e) {  }
			}
		}
		return legalMoves;
	}
	
	//Checks if the move is within the boundaries of the 'board'
	public boolean boundaryCheck(Board board, int x, int y)
	{
		if ((x <= (board.getWidth() - 1) && x >= 0) && (y <= (board.getHeight() - 1) && y >= 0)) {
			return true;
		} else {
			return false;
		}
	}
}