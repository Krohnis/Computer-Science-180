package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Move;

public class Pawn extends Piece
{
	private final static int[] POTENTIAL_XMOVES = { -1, 0, 1 };
	
	//Constructor for 'this'
	public Pawn(final String alliance, final int x, final int y)
	{
		super(alliance, 50, x, y);
		char symbol;
		if ((alliance).toUpperCase().equals("HOTWHEELS")) { 
			symbol = 'p'; 
		} else { 
			symbol = 'P'; 
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
		
		for (int xMove : POTENTIAL_XMOVES) {
			int toX = super.getX() + xMove;
			int toY = super.getY() + pieceDirection;
			if (boundaryCheck(board, toX, toY)) {
				Piece piece = board.getPiece(toX, toY);
				try {
					if (!piece.getAlliance().toUpperCase().equals(super.getAlliance()) && (xMove != 0) && !(piece instanceof Car)) {
						legalMoves.add(new Move(board, this, toX, toY));
					}
				} catch (Exception e) {
					if (xMove == 0) {
						legalMoves.add(new Move(board, this, toX, toY));
					}
				}
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