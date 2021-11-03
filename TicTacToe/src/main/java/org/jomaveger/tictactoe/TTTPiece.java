package org.jomaveger.tictactoe;

import org.jomaveger.game.Piece;

public enum TTTPiece implements Piece {
	X, O, E;

	@Override
	public TTTPiece opposite() {
		switch (this) {
		case X:
			return TTTPiece.O;
		case O:
			return TTTPiece.X;
		default:
			return TTTPiece.E;
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case X:
			return "X";
		case O:
			return "O";
		default:
			return " ";
		}
	}
}