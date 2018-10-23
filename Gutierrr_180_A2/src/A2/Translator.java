package A2;

import Board.Board;
import Board.Move;
import Pieces.Piece;

public class Translator 
{
	public Move input(Board board, String input)
	{
		int[] index = new int[4];
		if (input.length() == 4) {
			for (int n = 0; n < input.length(); n++) {
				switch (input.charAt(n)) {
					case '1': index[n] = 0; break;
					case '2': index[n] = 1; break;
					case '3': index[n] = 2; break;
					case '4': index[n] = 3; break;
					case '5': index[n] = 4; break;
					case '6': index[n] = 5; break;
					case '7': index[n] = 6; break;
					case '8': index[n] = 7; break;
					case '9': index[n] = 8; break;
					case 'A': index[n] = 0; break;
					case 'B': index[n] = 1; break;
					case 'C': index[n] = 2; break;
					case 'D': index[n] = 3; break;
					case 'E': index[n] = 4; break;
					case 'F': index[n] = 5; break;
					case 'G': index[n] = 6; break;
					default: break;
				}
			}
			try {
				Piece piece = board.getPiece(index[0], index[1]);
				return (new Move(board, piece, index[2], index[3]));
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	public String output(Piece p, int x, int y) 
	{
		int[] index = { p.getX(), p.getY(), x, y };
		String output = "";
		for (int n = 0; n < index.length; n++) {
			if ((n % 2) == 0) {
					switch(index[n]) {
					case 0: output += "A"; break;
					case 1: output += "B"; break;
					case 2: output += "C"; break;
					case 3: output += "D"; break;
					case 4: output += "E"; break;
					case 5: output += "F"; break;
					case 6: output += "G"; break;
				}
			} 
			else {
					switch(index[n]) {
					case 0: output += "1"; break;
					case 1: output += "2"; break;
					case 2: output += "3"; break;
					case 3: output += "4"; break;
					case 4: output += "5"; break;
					case 5: output += "6"; break;
					case 6: output += "7"; break;
					case 7: output += "8"; break;
				}
			}
		}
		return output;
	}
	
	public String transpose(Piece p, int x, int y) 
	{
		int Y = (7 - y);
		int pY = (7 - p.getY());
		int[] index = { p.getX(), pY, x, Y };
		String output = "";
		for (int n = 0; n < index.length; n++) {
			if ((n % 2) == 0) {
					switch(index[n]) {
					case 0: output += "A"; break;
					case 1: output += "B"; break;
					case 2: output += "C"; break;
					case 3: output += "D"; break;
					case 4: output += "E"; break;
					case 5: output += "F"; break;
					case 6: output += "G"; break;
				}
			} 
			else {
					switch(index[n]) {
					case 0: output += "1"; break;
					case 1: output += "2"; break;
					case 2: output += "3"; break;
					case 3: output += "4"; break;
					case 4: output += "5"; break;
					case 5: output += "6"; break;
					case 6: output += "7"; break;
					case 7: output += "8"; break;
				}
			}
		}
		return output;
	}
}