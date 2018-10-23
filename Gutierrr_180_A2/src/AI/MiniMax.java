package AI;

import java.util.ArrayList;
import java.util.Collections;

import Board.Tile;
import Board.Board;
import Board.Move;
import Pieces.Car;
import Pieces.Piece;

public class MiniMax 
{
	private final int MAX_DEPTH = 30;
	private int CURRENT_MAX_DEPTH = 1;
	private final int OVERTIME_FLAG = Integer.MIN_VALUE + 1;
	private final long TIME_LIMIT = 5000;
	private final int[][] HISTORY = new int[2][10000];
	private long startTime;
	private long endTime;
	
	//Executable for Minimax, start the AI branching
	public Move execute(final Board board)
	{
		CURRENT_MAX_DEPTH = 1;
		ArrayList<Move> movesList = new ArrayList();
		Move bestMove = null;
		Move currentMove = null;
		
		setupTable();
		startTime = System.currentTimeMillis();
		endTime = startTime + TIME_LIMIT;
		
		while (System.currentTimeMillis() < endTime)
		{
			movesList = board.createMovesList("NOTWHEELS");
			currentMove = null;
			int bestScore = Integer.MIN_VALUE;
			int score = 0;
			int depth = 0;
			
			moveOrdering(movesList, 0);
			for (Move move : movesList) {
				move.makeMove();
				score = minMax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				move.undoMove();
				if (score > bestScore) {
					bestScore = score;
					currentMove = move;
				}
				if (System.currentTimeMillis() > endTime || (score == OVERTIME_FLAG)) { break; }
			}
			if (score != OVERTIME_FLAG) {
				bestMove = currentMove;
				//System.out.println("Considering... " + bestMove + "; @Depth: " + CURRENT_MAX_DEPTH + "; @Time: " + (float)(System.currentTimeMillis() - startTime) / 1000);
				HISTORY[0][hashCode(bestMove)] += 1;
			}
			if (CURRENT_MAX_DEPTH < MAX_DEPTH) {
				CURRENT_MAX_DEPTH += 1;
			} else {
				break;
			}
		}
		System.out.println("Execution Time: " + (float)(System.currentTimeMillis() - startTime)/1000);
		return bestMove;
	}
	
	//MinMax Algorithm to find best possible move
	public int minMax(final Board board, int depth, int alpha, int beta, boolean aiTurn)
	{
		if (winner(board, depth) != -1) { return winner(board, depth); } 
		if (depth == CURRENT_MAX_DEPTH) { return evaluate(board, depth); }
		
		ArrayList<Move> movesList;
		Move bestMove = null;
		int score = 0;
		
		if (aiTurn) { movesList = board.createMovesList("NOTWHEELS"); } 
		else { movesList = board.createMovesList("HOTWHEELS"); }
		
		if (aiTurn) {
			moveOrdering(movesList, 0);
			for (Move move : movesList) {
				move.makeMove();
				score = minMax(board, depth + 1, alpha, beta, false);
				move.undoMove();
				if ((System.currentTimeMillis() > endTime) || score == OVERTIME_FLAG) {
					return OVERTIME_FLAG;
				}
				if (score > alpha) {
					alpha = score;
					bestMove = move;
				}
				if (alpha >= beta) {
					break;
				}
			}
			try {
				HISTORY[0][hashCode(bestMove)] += 1;
			} catch (Exception e) {  }
			return alpha;
		}
		else {
			moveOrdering(movesList, 1);
			for (Move move : movesList) {
				move.makeMove();
				score = minMax(board, depth + 1, alpha, beta, true);
				move.undoMove();
				if ((System.currentTimeMillis() > endTime) || score == OVERTIME_FLAG) {
					return OVERTIME_FLAG;
				}
				if (score < beta) {
					beta = score;
					bestMove = move;
				}
				if (alpha >= beta) {
					break;
				}
			}
			try {
				HISTORY[1][hashCode(bestMove)] += 1;
			} catch (Exception e) {  }
			return beta;
		}
	}
	
	//Checks for winner
	public int winner(Board board, int depth)
	{
		if (board.getTile(6, 3).getOccupied() instanceof Car) { return (-50000 + (depth * 100)); }
		else if (board.getTile(6, 4).getOccupied() instanceof Car ) { return 50000 - (depth * 100); }
		return -1;
	}
	
	//Evaluation Algorithm to determine if the move was good
	public int evaluate(Board board, int depth)
	{
		ArrayList<Piece> notWheels = board.getTeams("NOTWHEELS");
		ArrayList<Piece> hotWheels = board.getTeams("HOTWHEELS");
		int eval = 0;
		eval += evaluatePart2(board, notWheels, hotWheels);
		eval -= evaluatePart2(board, hotWheels, notWheels);
		
		return eval;
	}
	
	public int evaluatePart2(Board board, ArrayList<Piece> notWheels, ArrayList<Piece> hotWheels)
	{
		int eval = 0;
		int dist;
		
		for (Piece piece : notWheels)
		{
			Tile tile = board.getTile(piece.getX(), piece.getY());
			dist = 6 - piece.getX();
			if (piece instanceof Car)
			{
				eval += (1 - (dist/6)) * 2500;
			}
			else if (tile.getAlliance() != null)
			{
				if (!tile.getAlliance().equals(piece.getAlliance())) {
					if (piece.getX() > notWheels.indexOf(hotWheels.size()-1)) {
						eval += (1 - (dist/6)) * 1000;
					}
				} 
			}
			eval += piece.getValue();
		}
		return eval;
	}
	
	/*
	//Evaluation Algorithm to determine if the move was good
	public int evaluate(Board board, int depth)
	{
		long sT = System.currentTimeMillis();
		ArrayList<Piece> notWheels = board.getTeams("NOTWHEELS");
		ArrayList<Piece> hotWheels = board.getTeams("HOTWHEELS");
		int eval = 0;
		int dist = 0;
		//Evaluation for notWheels
		for (Piece piece : notWheels) 
		{
			Tile tile = board.getTile(piece.getX(), piece.getY());
			dist = (6 - piece.getX());
			
			if (piece instanceof Car) {
				eval += (1 - (dist/(board.getWidth()-1))) * 2500;
			}
			try {
				if (!(tile).getAlliance().toUpperCase().equals(piece.getAlliance()) && (piece.getX() > notWheels.get(hotWheels.size() - 1).getX())) 
				{
					eval += (1 - (dist/(board.getWidth()-1))) * 1000;
				}
			} catch (Exception e) {  }
			
			eval += piece.getValue();
		}
		//Evaluation for hotWheels
		for (Piece piece : hotWheels) 
		{
			Tile tile = board.getTile(piece.getX(), piece.getY());
			dist = (6 - piece.getX());
			
			if (piece instanceof Car) {
				eval -= (1 - (dist/(board.getWidth()-1))) * 2500;
			}
			try {
				if (!(tile).getAlliance().toUpperCase().equals(piece.getAlliance()) && (piece.getX() > notWheels.get(notWheels.size() - 1).getX())) 
				{
					eval -= (1 - (dist/(board.getWidth()-1))) * 1000;
				}
			} catch (Exception e) {  }
			
			eval -= piece.getValue();
		}
		return eval;
	}
	*/
	
	//Setup the 'History Table' for use
	public void setupTable()
	{
		for (int n = 0; n < 9009; n++) {
			HISTORY[0][n] = 0;
		}
		for (int k = 0; k < 9009; k++) {
			HISTORY[1][k] = 0;
		}
	}
	
	//Hash code function, turns move into hash
	public int hashCode(Move move)
	{
		int[] input = { move.getY(), move.getX(), move.getPiece().getY(), move.getPiece().getX() };
		int mod = 1;
		int index = 0;
		for (int i = 0; i < input.length; i++)
		{
			input[i] = input[i] * mod;
			index += input[i];
			mod *= 10;
		}
		return index;
	}
	
	//Sorts moves based on what was most successful in previous iterations
	public void moveOrdering(ArrayList<Move> list, int team)
	{
		for (int n = 0; n < list.size(); n++) {
			if (HISTORY[team][hashCode(list.get(n))] > HISTORY[team][hashCode(list.get(0))]) {
				Collections.swap(list,  list.indexOf(list.get(n)), list.indexOf(list.get(0)));
				break;
			}
		}
		/*
		for (Move iMove : list) {
			for (Move jMove : list) {
				if (HISTORY[team][hashCode(iMove)] < HISTORY[team][hashCode(jMove)]) {
					Collections.swap(list, list.indexOf(iMove), list.indexOf(jMove));
				}
			}
		}
		*/
	}
}