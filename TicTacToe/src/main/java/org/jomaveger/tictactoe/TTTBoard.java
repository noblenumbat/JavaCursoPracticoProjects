package org.jomaveger.tictactoe;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.jomaveger.game.Board;
import org.jomaveger.game.Piece;

public class TTTBoard implements Board<Integer>, Serializable {
	
	private static final int NUM_SQUARES = 9;
	private TTTCell[] position;
	private TTTPiece turn;

	public TTTBoard(TTTCell[] position, TTTPiece turn) {
		this.position = position;
		this.turn = turn;
	}

	public TTTBoard() {
		position = new TTTCell[NUM_SQUARES];
		for (int i = 0; i < NUM_SQUARES; i++) {
			position[i] = new TTTCell(i);
		}
		turn = TTTPiece.X;
	}

	@Override
	public TTTPiece getTurn() {
		return turn;
	}

	@Override
	public TTTBoard move(Integer location) {
		TTTCell[] tempPosition = SerializationUtils.clone(position);
		tempPosition[location].setContent(turn);
		return new TTTBoard(tempPosition, turn.opposite());
	}

	@Override
	public List<Integer> getLegalMoves() {
		List<Integer> legalMoves = new ArrayList<>();
		for (int i = 0; i < NUM_SQUARES; i++) {
			if (position[i].getContent() == TTTPiece.E) {
				legalMoves.add(i);
			}
		}
		return legalMoves;
	}

	@Override
	public boolean isWin() {
		return checkPos(0, 1, 2) || checkPos(3, 4, 5) || checkPos(6, 7, 8) || checkPos(0, 3, 6) || checkPos(1, 4, 7)
				|| checkPos(2, 5, 8) || checkPos(0, 4, 8) || checkPos(2, 4, 6);
	}

	private boolean checkPos(int p0, int p1, int p2) {
		return position[p0].getContent() == position[p1].getContent() 
				&& position[p0].getContent() == position[p2].getContent() 
				&& position[p0].getContent() != TTTPiece.E;
	}

	@Override
	public double evaluate(Piece player) {
		if (isWin() && turn == player) {
			return -1;
		} else if (isWin() && turn != player) {
			return 1;
		} else {
			return 0.0;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				sb.append(position[row * 3 + col].toString());
				if (col != 2) {
					sb.append("|");
				}
			}
			sb.append(System.lineSeparator());
			if (row != 2) {
				sb.append("-----");
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		for (int row = 1; row < IGame.ROWS; ++row) {
			g.fillRoundRect(0, IGame.CELL_SIZE * row - IGame.GRID_WIDHT_HALF, IGame.CANVAS_WIDTH - 1,
					IGame.GRID_WIDTH, IGame.GRID_WIDTH, IGame.GRID_WIDTH);
		}
		for (int col = 1; col < IGame.COLS; ++col) {
			g.fillRoundRect(IGame.CELL_SIZE * col - IGame.GRID_WIDHT_HALF, 0, IGame.GRID_WIDTH,
					IGame.CANVAS_HEIGHT - 1, IGame.GRID_WIDTH, IGame.GRID_WIDTH);
		}

		for (int i = 0; i < NUM_SQUARES; i++) {
			position[i].paint(g);
		}
	}
}