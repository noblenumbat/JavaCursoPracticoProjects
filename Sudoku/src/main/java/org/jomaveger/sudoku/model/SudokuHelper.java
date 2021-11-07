package org.jomaveger.sudoku.model;

import org.jomaveger.functional.control.Option;
import org.jomaveger.functional.tuples.Tuple2;

public final class SudokuHelper {

	public static int[][] copy(int[][] board) {
		int[][] copy = new int[9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++)
				copy[x][y] = board[x][y];
		}
		return copy;
	}
	
	public static int[][] copy(Integer[][] board) {
		int[][] copy = new int[9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++)
				copy[x][y] = board[x][y];
		}
		return copy;
	}
	
	public static Integer[] toOneDIntegerArray(int[] data) {
        return java.util.Arrays.stream(data).boxed().toArray(Integer[]::new);
    }
	
	public static Integer[][] toTwoDIntegerArray(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(SudokuHelper::toOneDIntegerArray).toArray(Integer[][]::new);
    }
	
	public static Option<Tuple2<Integer, Integer>> findUnassignedCell(int[][] grid) {
        int r, c;
        for (r = 0; r < grid.length; r++) {
            for (c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 0) {
                    return Option.instance(new Tuple2<>(r, c));
                }
            }
        }
        return Option.instance(null);
    }
	
	public static boolean safeToPlace(int[][] grid, int r, int c, int value) {
		return safeInRow(grid, r, value) 
				&& safeInCol(grid, c, value) 
				&& safeInSubgrid(grid, r, c, value);
	}

	public static boolean safeInRow(int[][] grid, int r, int value) {
		for (int c = 0; c < grid[0].length; c++) {
			if (grid[r][c] == value) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean safeInCol(int[][] grid, int c, int value) {
        for (int r = 0; r < grid.length; r++) {
            if (grid[r][c] == value) {
                return false;
            }
        }
        return true;
    }
	
	public static boolean safeInSubgrid(int[][] grid, int r, int c, int value) {
		int nRegions = (int) Math.sqrt(grid.length);
		int sRow = r - r % nRegions;
		int sCol = c - c % nRegions;

		for (int i = sRow; i < sRow + nRegions; i++) {
			for (int j = sCol; j < sCol + nRegions; j++) {
				if (grid[i][j] == value) {
					return false;
				}
			}
		}
		return true;
	}
}
