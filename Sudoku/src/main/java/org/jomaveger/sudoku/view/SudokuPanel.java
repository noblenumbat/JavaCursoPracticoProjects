package org.jomaveger.sudoku.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jomaveger.sudoku.controller.SudokuController;
import org.jomaveger.sudoku.model.Game;
import org.jomaveger.sudoku.model.UpdateAction;

public class SudokuPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private final Field[][] fields;
	private final JPanel[][] panels;

	public SudokuPanel() {
		super(new GridLayout(3, 3));

		panels = new JPanel[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				panels[y][x] = new JPanel(new GridLayout(3, 3));
				panels[y][x].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				add(panels[y][x]);
			}
		}

		fields = new Field[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x] = new Field(x, y);
				panels[y / 3][x / 3].add(fields[y][x]);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		switch ((UpdateAction) arg) {
		case NEW_GAME:
			setGame((Game) o);
			break;
		case VIEW_SOLUTION:
			setSolution((Game) o);
			break;
		case NO_SOLUTION:
			warnNoSolution();
			break;
		}
	}

	private void setSolution(Game game) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x].setBackground(Color.WHITE);
				fields[y][x].setNumber(game.getNumber(x, y), false);
				fields[y][x].setEnabled(false);
			}
		}
	}

	public void setGame(Game game) {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				fields[y][x].setBackground(Color.WHITE);
				fields[y][x].setNumber(game.getNumber(x, y), false);
				fields[y][x].setEnabled(true);
			}
		}
	}

	public void setController(SudokuController sudokuController) {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++)
				panels[y][x].addMouseListener(sudokuController);
		}
	}

	private void warnNoSolution() {
		JOptionPane.showMessageDialog(this, "This Sudoku Has No Solution", "Warning", JOptionPane.WARNING_MESSAGE);
	}
}