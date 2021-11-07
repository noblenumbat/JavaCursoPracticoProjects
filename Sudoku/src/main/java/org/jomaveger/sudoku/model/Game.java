package org.jomaveger.sudoku.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;
import java.util.Random;

import org.apache.log4j.Logger;

public class Game extends Observable {

	private static final Logger log = Logger.getLogger(Game.class);

	public final static int N = 9;

	private int[][] game;
	private int[][] solution;
	private boolean hasSolution;
	private int selectedNumber;

	public Game() {
		newGame();
	}

	public void newGame() {
		game = readBoardFromFile(getRandomFile());
		solution = generateSolution(game);
		setChanged();
		notifyObservers(UpdateAction.NEW_GAME);
	}

	private int[][] readBoardFromFile(File file) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return parseString(sb.toString());
	}

	private int[][] parseString(String str) {
		if (str.length() != N * N)
			throw new SudokuFormatException(String.format("The sudoku board must be %dx%d", N, N));

		int[][] board = new int[N][N];
		for (int i = 0; i < str.length(); i++) {
			int num;
			try {
				num = str.charAt(i) != '.' ? Integer.parseInt(str.charAt(i) + "") : 0;
			} catch (NumberFormatException e) {
				throw new SudokuFormatException("The sudoku board string must contain only numbers "
						+ "from 1 to 9 or points (indicating blank spaces)", e);
			}
			board[i / 9][i % 9] = num;
		}
		return board;
	}

	private File getRandomFile() {
		URL dirURL = getClass().getClassLoader().getResource("sudokus");
		File sudoku = null;
		if (dirURL != null && dirURL.getProtocol().equals("file")) {
			try {
				File[] sudokus = new File(dirURL.toURI()).listFiles();
				Random r = new Random();
				int n = r.nextInt(sudokus.length);
				sudoku = sudokus[n];
			} catch (URISyntaxException e) {
				log.error(e.getMessage());
			}
		}
		return sudoku;
	}

	public void setSelectedNumber(int selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	public int getSelectedNumber() {
		return selectedNumber;
	}

	public void setNumber(int x, int y, int number) {
		game[y][x] = number;
	}

	public int getNumber(int x, int y) {
		return game[y][x];
	}

	private int[][] generateSolution(int[][] game) {

		// Sequential Sudoku Solver
//		int[][] sol = SequentialSudokuSolver.solveSudoku(game);
//		hasSolution = sol != null;
//		return sol;

		// Concurrent Sudoku Solver
		Integer[][] sol = ConcurrentSudokuSolver.solvePuzzle(game);
		hasSolution = sol != null;
		int[][] solu = null;
		if (hasSolution) {
			solu = SudokuHelper.copy(sol);
		}
		return solu;
	}

	public void loadSolution() {
		if (hasSolution) {
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++)
					this.game[x][y] = this.solution[x][y];
			}
			setChanged();
			notifyObservers(UpdateAction.VIEW_SOLUTION);
		} else {
			setChanged();
			notifyObservers(UpdateAction.NO_SOLUTION);
		}
	}
}