package A2;

import java.util.ArrayList;
import java.util.Scanner;

import AI.MiniMax;
import Board.Board;
import Board.Move;
import Pieces.Car;

public class Game 
{
	private boolean gameOver, turn;
	private String input;
	private Scanner console;
	private Translator translator;
	private Board gameBoard;
	private MiniMax  minMax = new MiniMax();
	private ArrayList<Move> moveList = new ArrayList();
	
	public Game()
	{
		//Creates global variables that will be used throughout runtime
		console = new Scanner(System.in);
		translator = new Translator();
		gameBoard = new Board();
		//Displays the board in console
		gameBoard.updateBoard();
		
		//Takes an input from the user, determines if they move first or second
		System.out.print("Is the user 'First' or 'Second': ");
		if (console.nextLine().toUpperCase().equals("FIRST")) {
			turn = true;
		} else {
			turn = false;
		}
		
		gameOver = false;
		while (!gameOver) 
		{
			if (turn) {
				moveList = gameBoard.createMovesList("HOTWHEELS");
			} else {
				moveList = gameBoard.createMovesList("NOTWHEELS");
			}
			
			for (Move moveString : moveList) {
				System.out.print(moveString.toString() + ":" + moveString.getPiece().getSymbol() + " ");
			}
			
			if (turn) 
			{
				System.out.print("\nTeam 1's Move: ");
				String input = console.nextLine();
				Move userMove = translator.input(gameBoard, input);
				try {
					for (Move move : moveList) {
						if (userMove.toString().equals(move.toString())) {
							move.makeMove();
							gameBoard.updateBoard();
							turn = !(turn);
							break;
						}
					}
				} catch (Exception e) {
					System.out.println("Erroneous Input, Invalid Move");
				}
			}
			else
			{
				System.out.print("\nTeam 2's Move: \n");
				Move bestMove = minMax.execute(gameBoard);
				System.out.print(bestMove.toString() + " ");
				System.out.println(translator.transpose(bestMove.getPiece(), bestMove.getX(), bestMove.getY()));
				bestMove.makeMove();
				gameBoard.updateBoard();
				turn = !(turn);
			}
			if (gameBoard.getTile(6, 3).getOccupied() instanceof Car || gameBoard.getTile(6, 4).getOccupied() instanceof Car) 
			{ 
				System.out.println("\n\tGAME OVER!");
				gameOver = true;
			}
		}
	}
}
