package org.jomaveger.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.jomaveger.functional.control.Option;
import org.jomaveger.functional.tuples.Tuple2;

public class ConcurrentSudokuSolver extends RecursiveAction {

    private static final long serialVersionUID = 1L;
    
    private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 9;

    private static AtomicBoolean solved = new AtomicBoolean(false);
    private static AtomicReference<Integer[][]> finalGrid = new AtomicReference<>(null);
    
    private final int[][] grid;
    private final int depth;
    private final int cutoff;

    public ConcurrentSudokuSolver(int[][] grid, int depth, int cutoff) {
        this.grid = grid;
        this.depth = depth;
        this.cutoff = cutoff;
    }
    
	private static void resetAtomicVariables() {
		solved = new AtomicBoolean(false);
		finalGrid = new AtomicReference<>(null);
	}
    
    @Override
    protected void compute() {
    	if (solved.get()) {
            return;
        }
    	
    	Option<Tuple2<Integer, Integer>> oCoords = SudokuHelper.findUnassignedCell(grid);
    	
    	if (!oCoords.isSome() && solved.compareAndSet(false, true)) {
            finalGrid.compareAndSet(null, SudokuHelper.toTwoDIntegerArray(grid));
            return;
        }
    	
		if (depth > cutoff) {
			SequentialSudokuSolver seqSolver = new SequentialSudokuSolver(grid);
			if (seqSolver.successfulSolve() && solved.compareAndSet(false, true)) {
				finalGrid.compareAndSet(null, SudokuHelper.toTwoDIntegerArray(seqSolver.getGrid()));
			}
			return;
		}
		
		int r = oCoords.get()._1;
		int c = oCoords.get()._2;
		final List<RecursiveAction> actions = new ArrayList<>();

		for (int testVal = MIN_VALUE; testVal <= MAX_VALUE; testVal++) {
			if (SudokuHelper.safeToPlace(grid, r, c, testVal)) {				
				final int[][] tempGrid = SudokuHelper.copy(grid);				
				tempGrid[r][c] = testVal;
				RecursiveAction action = null;
				action = new ConcurrentSudokuSolver(tempGrid, depth + 1, cutoff);				
				actions.add(action);
			}
		}

		invokeAll(actions);

		for (RecursiveAction action : actions) {
			action.join();
		}
	}
    
    public static Integer[][] solvePuzzle(int[][] grid) {
        int[][] g = SudokuHelper.copy(grid);
        final ConcurrentSudokuSolver task = new ConcurrentSudokuSolver(g, 0, 10);
        ForkJoinPool.commonPool().invoke(task);
        task.join();
        if(solved.get()) {
            Integer[][] retVal = finalGrid.get();
            resetAtomicVariables();
            return retVal;
        }
        resetAtomicVariables();
        return null;
    }
}
