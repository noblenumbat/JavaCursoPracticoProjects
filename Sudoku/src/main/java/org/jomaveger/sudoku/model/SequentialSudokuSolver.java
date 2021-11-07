package org.jomaveger.sudoku.model;

import org.jomaveger.functional.control.Option;
import org.jomaveger.functional.tuples.Tuple2;

public final class SequentialSudokuSolver {

	private int[][] grid;
	
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 9;

	public SequentialSudokuSolver(int[][] grid) {
		this.grid = grid;
	}

	public int[][] getGrid() {
		return grid;
	}

	public boolean successfulSolve() {
		Option<Tuple2<Integer, Integer>> oCoords = SudokuHelper.findUnassignedCell(grid);

		if (!oCoords.isSome()) {
			return true;
		}

		int r = oCoords.get()._1;
		int c = oCoords.get()._2;

		for (int testVal = MIN_VALUE; testVal <= MAX_VALUE; testVal++) {
			if (SudokuHelper.safeToPlace(grid, r, c, testVal)) {
				grid[r][c] = testVal;
				if (successfulSolve()) {
					return true;
				}
			}
		}
		grid[r][c] = 0;

		return false;
	}

	public static int[][] solveSudoku(int[][] grid) {
		int[][] g = SudokuHelper.copy(grid);
		final SequentialSudokuSolver newSolver = new SequentialSudokuSolver(g);
		if (newSolver.successfulSolve()) {
			return SudokuHelper.copy(newSolver.getGrid());
		}

		return null;
	}
}
