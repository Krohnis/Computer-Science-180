package Pieces;

import java.util.ArrayList;

import Board.Board;
import Board.Move;

public class Knight extends Piece
{
	private final static int[] POTENTIAL_XMOVES = { -2, -1, 1, 2 };
	private final static int[] POTENTIAL_YMOVES = { -2, -1, 1, 2 };
	
	//Constructor for 'this'
	public Knight(final String alliance, final int x, final int y)
	{
		super(alliance, 200, x, y);
		char symbol;
		if ((alliance).toUpperCase().equals("HOTWHEELS")) { 
			symbol = 'n'; 
		} else { 
			symbol = 'N'; 
		}
		super.setSymbol(symbol);
	}
	
	//Generates the moves list of 'this' piece
	public ArrayList<Move> getMovesList(Board board, boolean isEmpty)
	{
		long startT = System.currentTimeMillis();
		ArrayList<Move> legalMoves = new ArrayList();
		int pieceDirection;
		
		if (super.getAlliance().toUpperCase().equals("HOTWHEELS")) {
			pieceDirection = 1;
		} else {
			pieceDirection = -1;
		}
		
		for (int xMove : POTENTIAL_XMOVES) {
			for (int yMove : POTENTIAL_YMOVES) {
				int toX = super.getX() + xMove;
				int toY = super.getY() + yMove;
				if ((Math.abs(xMove) != Math.abs(yMove)) && boundaryCheck(board, toX, toY)) {
					Piece piece = board.getPiece(toX, toY);
					try {
						if (!piece.getAlliance().toUpperCase().equals(super.getAlliance()) && !(piece instanceof Car)) {
							legalMoves.add(new Move(board, this, toX, toY));
						}
					} catch (Exception e) {
						if (((toY - this.getY()) > 0)&& (this.getAlliance().equals("HOTWHEELS"))) {
							legalMoves.add(new Move(board, this, toX, toY));
						}
						else if (((toY - this.getY()) < 0) && (this.getAlliance().equals("NOTWHEELS"))) {
							legalMoves.add(new Move(board, this, toX, toY));
						}
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
