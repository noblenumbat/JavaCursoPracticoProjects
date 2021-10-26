package org.jomaveger.minesweeper.main;

import javax.swing.SwingUtilities;
import org.jomaveger.minesweeper.controller.Game;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			new Game();
        });
	}
}
