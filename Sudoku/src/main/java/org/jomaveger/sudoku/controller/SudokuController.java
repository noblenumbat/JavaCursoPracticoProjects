package org.jomaveger.sudoku.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import org.jomaveger.sudoku.model.Game;
import org.jomaveger.sudoku.view.Field;
import org.jomaveger.sudoku.view.SudokuPanel;

public class SudokuController implements MouseListener {

	private final Game game;

	public SudokuController(SudokuPanel sudokuPanel, Game game) {
		this.game = game;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JPanel panel = (JPanel) e.getSource();
		Component component = panel.getComponentAt(e.getPoint());
		if (component instanceof Field) {
			Field field = (Field) component;
			int x = field.getFieldX();
			int y = field.getFieldY();

			if (e.getButton() == MouseEvent.BUTTON1
					&& (game.getNumber(x, y) == 0 || field.getForeground().equals(Color.BLUE))) {
				int number = game.getSelectedNumber();
				if (number == -1)
					return;
				game.setNumber(x, y, number);
				field.setNumber(number, true);
			} else if (e.getButton() == MouseEvent.BUTTON3 && !field.getForeground().equals(Color.BLACK)) {
				game.setNumber(x, y, 0);
				field.setNumber(0, false);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}