package org.jomaveger.sudoku.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jomaveger.sudoku.model.Game;

public class ButtonController implements ActionListener {

	private final Game game;

	public ButtonController(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New"))
			game.newGame();
		else if (e.getActionCommand().equals("View Solution"))
			game.loadSolution();
		else
			game.setSelectedNumber(Integer.parseInt(e.getActionCommand()));
	}
}