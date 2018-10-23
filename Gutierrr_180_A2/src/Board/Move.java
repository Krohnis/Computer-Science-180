package Board;

import java.util.ArrayList;

import A2.Translator;
import Pieces.Piece;

public class Move 
{
	private final Board gameBoard;
	private final Piece piece;
	private final Piece capturePiece;
	private final int X, Y, pX, pY;
	
	//Constructor for 'this'
	public Move(final Board board, Piece p, int x, int y)
	{
		X = x;
		Y = y;
		pX = p.getX();
		pY = p.getY();
		gameBoard = board;
		capturePiece = gameBoard.getPiece(x, y);
		piece = p;
	}
	
	//Executes 'this', changes the current board
	public void makeMove()
	{
		piece.setX(X);
		piece.setY(Y);
		gameBoard.setPiece(piece, piece.getX(), piece.getY());
		gameBoard.releaseTile(pX, pY);
		if (capturePiece != null) {
			gameBoard.getTeams(capturePiece.getAlliance()).remove(capturePiece);
		}
	}
	
	//Execute 'this', undo the previous move
	public void undoMove()
	{
		piece.setX(pX);
		piece.setY(pY);
		gameBoard.setPiece(piece, piece.getX(), piece.getY());
		gameBoard.setPiece(capturePiece, X, Y);
		if (capturePiece != null) {
			ArrayList<Piece> otherTeam = gameBoard.getTeams(capturePiece.getAlliance());
			otherTeam.add(otherTeam.size() - 1, capturePiece);
		}
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
	public Piece getPiece()
	{
		return piece;
	}
	
	//toString method that returns 'this' as readable string
	public String toString()
	{
		Translator translator = new Translator();
		String output = translator.output(piece, X, Y);
		return output;
	}
}
