package org.jomaveger.sudoku;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.jomaveger.sudoku.view.*;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		SwingUtilities.invokeLater(() -> {
			new Sudoku();
			;
		});
	}
}
