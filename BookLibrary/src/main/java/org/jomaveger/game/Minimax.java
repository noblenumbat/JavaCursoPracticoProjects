package org.jomaveger.game;

public class Minimax {

	public static <E> double minimax(Board<E> board, boolean maximizing, Piece originalPlayer, int maxDepth) {
		if (board.isWin() || board.isDraw() || maxDepth == 0) {
			return board.evaluate(originalPlayer);
		}

		if (maximizing) {
			double bestEval = Double.NEGATIVE_INFINITY;
			for (E move : board.getLegalMoves()) {
				double result = minimax(board.move(move), false, originalPlayer, maxDepth - 1);
				bestEval = Math.max(result, bestEval);
			}
			return bestEval;
		} else {
			double worstEval = Double.POSITIVE_INFINITY;
			for (E move : board.getLegalMoves()) {
				double result = minimax(board.move(move), true, originalPlayer, maxDepth - 1);
				worstEval = Math.min(result, worstEval);
			}
			return worstEval;
		}
	}

	public static <E> E findBestMove(Board<E> board, int maxDepth) {
		double bestEval = Double.NEGATIVE_INFINITY;
		E bestMove = null;
		for (E move : board.getLegalMoves()) {
			double result = alphabeta(board.move(move), false, board.getTurn(), maxDepth);
			if (result > bestEval) {
				bestEval = result;
				bestMove = move;
			}
		}
		return bestMove;
	}
	
	public static <E> double alphabeta(Board<E> board, boolean maximizing, Piece originalPlayer, int maxDepth) {
		return alphabeta(board, maximizing, originalPlayer, maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
	
	private static <E> double alphabeta(Board<E> board, boolean maximizing, Piece originalPlayer, int maxDepth, double alpha, double beta) {
		if (board.isWin() || board.isDraw() || maxDepth == 0) {
			return board.evaluate(originalPlayer);
		}

		if (maximizing) {
			for (E m : board.getLegalMoves()) {
				alpha = Math.max(alpha, alphabeta(board.move(m), false, originalPlayer, maxDepth - 1, alpha, beta));
				if (beta <= alpha) {
					break;
				}
			}
			return alpha;
		} else { 
			for (E m : board.getLegalMoves()) {
				beta = Math.min(beta, alphabeta(board.move(m), true, originalPlayer, maxDepth - 1, alpha, beta));
				if (beta <= alpha) {
					break;
				}
			}
			return beta;
		}
	}
}
