package org.jomaveger.tictactoe;

import java.awt.*;
import java.io.Serializable;

public class TTTCell implements Serializable {

	private TTTPiece content;
	private int row;
	private int col;
	
	public TTTCell(int position) {
		setRowAndCol(position);
		clear();
	}
	
	private void setRowAndCol(int position) {
		if (position == 0) {
			this.row = 0;
			this.col = 0;
		} else if (position == 1) {
			this.row = 0;
			this.col = 1;
		} else if (position == 2) {
			this.row = 0;
			this.col = 2;
		} else if (position == 3) {
			this.row = 1;
			this.col = 0;
		} else if (position == 4) {
			this.row = 1;
			this.col = 1;
		} else if (position == 5) {
			this.row = 1;
			this.col = 2;
		} else if (position == 6) {
			this.row = 2;
			this.col = 0;
		} else if (position == 7) {
			this.row = 2;
			this.col = 1;
		} else if (position == 8) {
			this.row = 2;
			this.col = 2;
		} else {
			throw new IllegalStateException();
		}
	}

	public void clear() {
		content = TTTPiece.E;
	}
	
	public void setContent(TTTPiece content) {
		this.content = content;
	}
	
	public TTTPiece getContent() {
		return this.content;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(IGame.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); 
	
		int x1 = col * IGame.CELL_SIZE + IGame.CELL_PADDING;
		int y1 = row * IGame.CELL_SIZE + IGame.CELL_PADDING;
		if (content == TTTPiece.X) {
			g2d.setColor(Color.RED);
			int x2 = (col + 1) * IGame.CELL_SIZE - IGame.CELL_PADDING;
			int y2 = (row + 1) * IGame.CELL_SIZE - IGame.CELL_PADDING;
			g2d.drawLine(x1, y1, x2, y2);
			g2d.drawLine(x2, y1, x1, y2);
		} else if (content == TTTPiece.O) {
			g2d.setColor(Color.BLUE);
			g2d.drawOval(x1, y1, IGame.SYMBOL_SIZE, IGame.SYMBOL_SIZE);
		}
	}
}
