package org.jomaveger.game;

import java.util.List;

public interface Board<E> {
	Piece getTurn();

	Board<E> move(E location);

	List<E> getLegalMoves();

	boolean isWin();

	default boolean isDraw() {
		return !isWin() && getLegalMoves().isEmpty();
	}

	double evaluate(Piece player);
}