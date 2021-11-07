package org.jomaveger.conecta4;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jomaveger.game.Minimax;
import org.jomaveger.lang.exceptions.ExceptionUtils;
import org.jomaveger.util.IResourcesLoader;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class C4UI implements Runnable {
	
	public static final String TITLE = "Connect-4 => Developed by JMVG";
	
	public static final int NUM_OF_ROWS = 6;
	public static final int NUM_OF_COLUMNS = 7;
	public static final int CHECKERS_IN_A_ROW = 4;
	public static final int DEFAULT_CONNECT_4_WIDTH = 570;
    public static final int DEFAULT_CONNECT_4_HEIGHT = 550;
    public static final String CONNECT_4_BOARD_IMG_PATH = "Board4.png";
    public static final String BLACK_CHECKER_IMG_PATH = "Black.png";
    public static final String RED_CHECKER_IMG_PATH = "Red.png";
	
	private static JButton[] buttons;
	private static C4Board board;
	private static C4Piece currentPlayer;
	private static GameState currentState;
	private static JFrame frameMainWindow;
	private static JPanel panelMain;
	private static JPanel panelBoardNumbers;
	private static JLayeredPane layeredGameBoard;
	private static KeyListener keyListener;
	private static JMenuBar menuBar;
    private static JMenu fileMenu;
    private static JMenuItem newGameItem;
    private static JMenuItem exitItem;
    
    public C4UI() {
    }

	@Override
	public void run() {
		
		createNewGame();
	}
	
	private static void createNewGame() {
		configureGuiStyle();
		
		buttons = new JButton[NUM_OF_COLUMNS];
        for (int i = 0; i < NUM_OF_COLUMNS; i++) {
            buttons[i] = new JButton(i + 1 + "");
            buttons[i].setFocusable(false);
        }
        
        setAllButtonsEnabled(true);
        
        if (frameMainWindow != null) frameMainWindow.dispose();
        frameMainWindow = new JFrame(TITLE);
        centerWindow(frameMainWindow, DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT);
        
        Component compMainWindowContents = createContentComponents();
        
        frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);
        
        frameMainWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        if (frameMainWindow.getKeyListeners().length == 0) {
            frameMainWindow.addKeyListener(getGameKeyListener());
        }

        frameMainWindow.setFocusable(true);
        frameMainWindow.pack();
        
        addMenu();
        
        initGame();
        
        frameMainWindow.setVisible(true);
	}

	private static void initGame() {
		board = new C4Board();
        currentPlayer = board.getTurn();
        currentState = GameState.PLAYING;	
	}

	private static void addMenu() {
		menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newGameItem = new JMenuItem("New Game");
        exitItem = new JMenuItem("Exit");
        newGameItem.addActionListener(e -> createNewGame());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        frameMainWindow.setJMenuBar(menuBar);		
	}

	private static KeyListener getGameKeyListener() {
		keyListener = new KeyListener() {
	        
			@Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            
	            String keyText = KeyEvent.getKeyText(e.getKeyCode());

	            for (int i = 0; i < NUM_OF_COLUMNS; i++) {
	                if (keyText.equals(i + 1 + "")) {
	                	play(i);
	                    break;
	                }
	            }
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    };
	    
	    return keyListener;
	}

	private static Component createContentComponents() {

        panelBoardNumbers = new JPanel();
        panelBoardNumbers.setLayout(new GridLayout(1, NUM_OF_COLUMNS, NUM_OF_ROWS, 4));
        panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));

        for (JButton button : buttons) {
            panelBoardNumbers.add(button);
        }

        layeredGameBoard = createLayeredBoard();
        
        panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameBoard, BorderLayout.CENTER);

        frameMainWindow.setResizable(false);
        return panelMain;
    }
	
	private static JLayeredPane createLayeredBoard() {
        layeredGameBoard = new JLayeredPane();

        ImageIcon imageBoard = null;
        layeredGameBoard.setPreferredSize(new Dimension(DEFAULT_CONNECT_4_WIDTH, DEFAULT_CONNECT_4_HEIGHT));
        layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));
        URL imgBoard = null;
		try {
			imgBoard = IResourcesLoader.getResourceFileURL(CONNECT_4_BOARD_IMG_PATH);
		} catch (IOException e1) {
			System.out.println(ExceptionUtils.getExpandedMessage(e1));
		}
        imageBoard = new ImageIcon(imgBoard);

        JLabel imageBoardLabel = new JLabel(imageBoard);

        imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, 0, 1);

        return layeredGameBoard;
    }
	
	private static void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
        int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
        frame.setLocation(x, y);
    }

	private static void setAllButtonsEnabled(boolean b) {
		if (b) {

            for (int i = 0; i < buttons.length; i++) {
                JButton button = buttons[i];
                int column = i;

                if (button.getActionListeners().length == 0) {
                    button.addActionListener(e -> {
                    	
                    	play(column);
                    });
                }
            }

        } else {

            for (JButton button : buttons) {
                for (ActionListener actionListener : button.getActionListeners()) {
                    button.removeActionListener(actionListener);
                }
            }
        }	
	}

	private static void play(int column) {
		board = board.move(column);
		
		placeChecker(currentPlayer, NUM_OF_ROWS - board.getColumnCount(column), column);
		
		boolean isOver = updateGame(currentPlayer);
		
		if (isOver)
			gameOver();
		
		currentPlayer = board.getTurn();
		
		Integer computerMove = Minimax.findBestMove(board, 7);
		
		if (computerMove != null) {
			board = board.move(computerMove);
			placeChecker(currentPlayer, NUM_OF_ROWS - board.getColumnCount(computerMove), computerMove);
			isOver = updateGame(currentPlayer);
			if (isOver) gameOver();
			currentPlayer = board.getTurn();	
		}
		
        frameMainWindow.requestFocusInWindow();
        frameMainWindow.repaint();
	}

	private static void gameOver() {
		int choice = 0;
        if (currentState == GameState.BLACK_WON) {
        	choice = JOptionPane.showConfirmDialog(null,
                        "You win! Start a new game?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
        } else if (currentState == GameState.RED_WON) {
        	choice = JOptionPane.showConfirmDialog(null,
                        "Computer AI wins! Start a new game?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
        } else if (currentState == GameState.DRAW) {
            choice = JOptionPane.showConfirmDialog(null,
                    "It's a draw! Start a new game?",
                    "Game Over", JOptionPane.YES_NO_OPTION);
        }

        setAllButtonsEnabled(false);

        for (KeyListener keyListener : frameMainWindow.getKeyListeners()) {
            frameMainWindow.removeKeyListener(keyListener);
        }

        if (choice == JOptionPane.YES_OPTION) {
            createNewGame();
        }		
	}

	private static boolean updateGame(C4Piece currentPlayer) {
		boolean isOver = false;
		
		if (board.isWin()) {
			currentState = (currentPlayer == C4Piece.B) ? GameState.BLACK_WON : GameState.RED_WON;
		} else if (board.isDraw()) {
			currentState = GameState.DRAW;
		}
		if (currentState != GameState.PLAYING) {
			isOver = true;
		}
		
		return isOver;
	}

	private static void placeChecker(C4Piece currentPlayer, int row, int column) {
		URL imgChecker = null;
		if (currentPlayer == C4Piece.B) {
			try {
				imgChecker = IResourcesLoader.getResourceFileURL(BLACK_CHECKER_IMG_PATH);
			} catch (IOException e1) {
				System.out.println(ExceptionUtils.getExpandedMessage(e1));
			}
		} else if (currentPlayer == C4Piece.R) {
			try {
				imgChecker = IResourcesLoader.getResourceFileURL(RED_CHECKER_IMG_PATH);
			} catch (IOException e1) {
				System.out.println(ExceptionUtils.getExpandedMessage(e1));
			}
		}
		
		int xOffset = 75 * column;
        int yOffset = 75 * row;
        ImageIcon checkerIcon = new ImageIcon(imgChecker);

        JLabel checkerLabel = new JLabel(checkerIcon);
        checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
        layeredGameBoard.add(checkerLabel, 0, 0);
	}

	private static void configureGuiStyle() {
        try {
        	boolean found = false;
        	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    found = true;
                    break;
                }
            }
        	if (!found) {
        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
        	}
        } catch (Exception e) {
        	System.out.println(ExceptionUtils.getExpandedMessage(e));
        }
    }
	
	public static void main(String[] args) {
		C4UI c4ui = new C4UI(); 
		javax.swing.SwingUtilities.invokeLater(c4ui);
	}
}
