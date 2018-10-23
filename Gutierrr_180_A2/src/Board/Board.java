package Board;

import java.util.ArrayList;
import java.util.Collection;

import Pieces.Piece;
import Pieces.Car;
import Pieces.Knight;
import Pieces.Bishop;
import Pieces.Rook;
import Pieces.Pawn;

public class Board 
{
	final static private int WIDTH = 7;
	final static private int HEIGHT = 8;
	private ArrayList<Piece> hotWheels = new ArrayList();
	private ArrayList<Piece> notWheels = new ArrayList();
	private Tile[][] gameBoard = new Tile[WIDTH][HEIGHT];
	
	//Constructor for 'this'
	public Board()
	{
		for (int n = 0; n < WIDTH; n++) {
			for (int k = 0; k < HEIGHT; k++) {
				gameBoard[n][k] = new Tile(n, k);
			}
		}
		
		setBoardAlliance();
		createStandardBoard();
		//createTestBoard();
	}
	
	//Generates the standard board for 'this'
	public void createStandardBoard() 
	{
		//Team 'Hot Wheel'
		hotWheels.add(new Pawn("HOTWHEELS", 1, 1));
		hotWheels.add(new Pawn("HOTWHEELS", 2, 2));
		hotWheels.add(new Pawn("HOTWHEELS", 3, 3));
		hotWheels.add(new Pawn("HOTWHEELS", 4, 3));
		hotWheels.add(new Pawn("HOTWHEELS", 5, 3));
		hotWheels.add(new Pawn("HOTWHEELS", 6, 3));
		hotWheels.add(new Knight("HOTWHEELS", 0, 3));
		hotWheels.add(new Knight("HOTWHEELS", 1, 3));
		hotWheels.add(new Bishop("HOTWHEELS", 3, 2));
		hotWheels.add(new Bishop("HOTWHEELS", 4, 2));
		hotWheels.add(new Rook("HOTWHEELS", 0, 2));
		hotWheels.add(new Rook("HOTWHEELS", 1, 2));
		hotWheels.add(new Car("HOTWHEELS", 0, 0));
		
		//Team 'Not Wheel'
		notWheels.add(new Pawn("NOTWHEELS", 1, 6));
		notWheels.add(new Pawn("NOTWHEELS", 2, 5));
		notWheels.add(new Pawn("NOTWHEELS", 3, 4));
		notWheels.add(new Pawn("NOTWHEELS", 4, 4));
		notWheels.add(new Pawn("NOTWHEELS", 5, 4));
		notWheels.add(new Pawn("NOTWHEELS", 6, 4));
		notWheels.add(new Knight("NOTWHEELS", 0, 4));
		notWheels.add(new Knight("NOTWHEELS", 1, 4));
		notWheels.add(new Bishop("NOTWHEELS", 3, 5));
		notWheels.add(new Bishop("NOTWHEELS", 4, 5));
		notWheels.add(new Rook("NOTWHEELS", 0, 5));
		notWheels.add(new Rook("NOTWHEELS", 1, 5));
		notWheels.add(new Car("NOTWHEELS", 0, 7));
		
		for (Piece piece : hotWheels) { this.setPiece(piece, piece.getX(), piece.getY()); }
		for (Piece piece : notWheels) { this.setPiece(piece, piece.getX(), piece.getY()); }
	}
	
	public void createTestBoard()
	{
		//Team 'Hot Wheel'
		hotWheels.add(new Pawn("HOTWHEELS", 6, 3));
		hotWheels.add(new Pawn("HOTWHEELS", 4, 3));
		hotWheels.add(new Car("HOTWHEELS", 3, 3));
		
		//Team 'Not Wheel'
		notWheels.add(new Pawn("NOTWHEELS", 6, 4));
		notWheels.add(new Pawn("NOTWHEELS", 4, 4));
		notWheels.add(new Car("NOTWHEELS", 3, 4));
		
		for (Piece piece : hotWheels) { this.setPiece(piece, piece.getX(), piece.getY()); }
		for (Piece piece : notWheels) { this.setPiece(piece, piece.getX(), piece.getY()); }
	}
	
	//Updates the game board in the console
	public void updateBoard()
	{
		for (int n = (HEIGHT - 1); n >= 0; n--) {
			System.out.print((n + 1) + " ");
			for (int k = 0; k < (WIDTH); k++) {
				Tile tile = gameBoard[k][n];
				try {
					System.out.print(tile.getOccupied().getSymbol() + " ");
				} catch (Exception e) {
					System.out.print(tile.getSymbol() + " ");
				}
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G");
	}
	
	//Generate the teams for the specificed player
	public ArrayList<Move> createMovesList(String t)
	{
		ArrayList<Piece> pieceList = new ArrayList();
		ArrayList<Move> moveList = new ArrayList();
		
		if ((t).toUpperCase().equals("HOTWHEELS")) {
			pieceList = hotWheels;
		} else {
			pieceList = notWheels;
		}
		
		for (Piece piece : pieceList) {
			moveList.addAll(piece.getMovesList(this, false));
		}
		if (moveList.isEmpty()) {
			Piece piece = pieceList.get(pieceList.size()-1);
			moveList.addAll(piece.getMovesList(this, true));
		}
		return moveList;
	}
	
	//Releases the tile from the piece
	public void releaseTile(int x, int y)
	{
		gameBoard[x][y].setOccupied(null);
	}
	
	//Setter methods
	public void setPiece(Piece p, int x, int y)
	{
		gameBoard[x][y].setOccupied(p);
	}
	public void setBoardAlliance() 
	{
		//Sets the track of 'Hotwheels' for 'this'
		gameBoard[0][0].setAlliance("HOTWHEELS"); gameBoard[1][1].setAlliance("HOTWHEELS"); 
		gameBoard[2][2].setAlliance("HOTWHEELS"); gameBoard[3][3].setAlliance("HOTWHEELS"); 
		gameBoard[4][3].setAlliance("HOTWHEELS"); gameBoard[5][3].setAlliance("HOTWHEELS"); 
		gameBoard[6][3].setAlliance("HOTWHEELS");
		
		//Sets the track of 'Notwheels' for 'this'
		gameBoard[0][7].setAlliance("NOTWHEELS"); gameBoard[1][6].setAlliance("NOTWHEELS"); 
		gameBoard[2][5].setAlliance("NOTWHEELS"); gameBoard[3][4].setAlliance("NOTWHEELS"); 
		gameBoard[4][4].setAlliance("NOTWHEELS"); gameBoard[5][4].setAlliance("NOTWHEELS");
		gameBoard[6][4].setAlliance("NOTWHEELS");
	}
	
	//Getter methods
	public int getWidth()
	{
		return WIDTH;
	}
	public int getHeight()
	{
		return HEIGHT;
	}
	public Piece getPiece(int x, int y)
	{
		return gameBoard[x][y].getOccupied();
	}
	public Tile getTile(int x, int y)
	{
		return gameBoard[x][y];
	}
	public ArrayList<Piece> getTeams(String t)
	{
		if ((t).toUpperCase().equals("HOTWHEELS")) {
			return hotWheels;
		} else {
			return notWheels;
		}
	}
}