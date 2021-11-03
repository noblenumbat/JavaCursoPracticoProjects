package org.jomaveger.tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jomaveger.game.Minimax;

public class GameAI extends JPanel implements IGame {

	private TTTBoard board;
	private GameState currentState;
	private TTTPiece currentPlayer;
	private JLabel statusBar;

	public GameAI() {

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				int rowSelected = mouseY / CELL_SIZE;
				int colSelected = mouseX / CELL_SIZE;

				if (currentState == GameState.PLAYING) {
					if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
							&& board.getLegalMoves().contains(getPositionFromRowAndCol(rowSelected, colSelected))) {
						board = board.move(getPositionFromRowAndCol(rowSelected, colSelected));
						updateGame(currentPlayer);
						currentPlayer = board.getTurn();
						Integer computerMove = Minimax.findBestMove(board, 9);
						if (computerMove != null) {
							board = board.move(computerMove);
							updateGame(currentPlayer);
							currentPlayer = board.getTurn();	
						}
					}
				} else {
					initGame();
				}
				
				repaint();
			}
		});

		statusBar = new JLabel("         ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.LIGHT_GRAY);

		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.PAGE_END);
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		initGame();
	}

	public void initGame() {
		board = new TTTBoard();
		currentState = GameState.PLAYING;
		currentPlayer = board.getTurn();
	}

	public void updateGame(TTTPiece currentPlayer) {
		if (board.isWin()) {
			currentState = (currentPlayer == TTTPiece.X) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
		} else if (board.isDraw()) {
			currentState = GameState.DRAW;
		}
	}

	private Integer getPositionFromRowAndCol(int row, int col) {
		if (row == 0 && col == 0) {
			return 0;
		} else if (row == 0 && col == 1) {
			return 1;
		} else if (row == 0 && col == 2) {
			return 2;
		} else if (row == 1 && col == 0) {
			return 3;
		} else if (row == 1 && col == 1) {
			return 4;
		} else if (row == 1 && col == 2) {
			return 5;
		} else if (row == 2 && col == 0) {
			return 6;
		} else if (row == 2 && col == 1) {
			return 7;
		} else if (row == 2 && col == 2) {
			return 8;
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);

		board.paint(g);

		if (currentState == GameState.PLAYING) {
			statusBar.setForeground(Color.BLACK);
			if (currentPlayer == TTTPiece.X) {
				statusBar.setText("X's Turn");
			} else {
				statusBar.setText("O's Turn");
			}
		} else if (currentState == GameState.DRAW) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("It's a Draw! Click to play again.");
		} else if (currentState == GameState.CROSS_WON) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'X' Won! Click to play again.");
		} else if (currentState == GameState.NOUGHT_WON) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'O' Won! Click to play again.");
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(TITLE);
				frame.setContentPane(new GameAI());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
