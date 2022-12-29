package org.cis120.tetris;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.LinkedList;
import javax.swing.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class Board extends JPanel {

    // the state of the game logic

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."

    private int[][] board;
    private int blockWidth;
    private int blockHeight;
    private int rowClear;
    private LinkedList<Integer> clearRows = new LinkedList<Integer>();
    private PieceSequence pieces = new PieceSequence();
    private Piece current;
    private PropertyChangeSupport next = new PropertyChangeSupport(this);

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 600;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 5;

    public Board(JLabel status, int xBlocks, int yBlocks) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        board = new int[yBlocks][xBlocks];
        try {
            this.readSavedFile("files/save.csv");
        } catch (NullPointerException e) {
        }

        blockWidth = COURT_WIDTH / xBlocks;
        blockHeight = COURT_HEIGHT / yBlocks;

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && current.isMoving()
                        && current.notLeftBlocked(board)) {
                    current.setPx(current.getPx() - 30);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && current.isMoving()
                        && current.notRightBlocked(board)) {
                    current.setPx(current.getPx() + 30);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && current.notBottomBlocked(board)
                        && current.isMoving()) {
                    current.setPy(current.getPy() + 30);
                    current.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && current.isMoving()) {
                    current.setVy(0);
                    current.drop(board);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                	current.rotate(board);
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    current.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    current.setVy(30);
                } 
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        playing = true;
        status.setText("Running...");

        try {
            this.readSavedFile("files/save.csv");
        } catch (NullPointerException e) {
            this.status.setText("No saved data");
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = 0;
                }
            }
        }
        pieces = new PieceSequence();
        int oldSize = pieces.getSize();
        current = pieces.curr();
        next.firePropertyChange("numPieces", oldSize, pieces.getSize());

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public void startOver() {
        this.writeLinesToFile(this.boardToStrings(new int[20][10]), "files/save.csv", false);
        this.reset();
    }
    
    public void pausePlay() {
    	String currentStatus = status.getText();
    	if (currentStatus.equals("Running...")) {
    		playing = false;
    		status.setText("Paused");
    	} else {
    		status.setText("Running...");
    		playing = true;
    		requestFocusInWindow();
    	}
    }

    public PieceSequence getNextPieces() {
        return this.pieces;
    }

    public boolean isFull(int[] row) {
        int counter = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] != 0) {
                counter++;
            }
        }
        return counter == row.length;
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            current.move();
            Position[] coordinates = current.getCoordinates();
            if (!current.notBottomBlocked(board)) {
                current.setVy(0);
                current.setting();
            }
            if (current.isMoving() && current.notBottomBlocked(board)) {
                current.setVy(30);
            }
            
            if (!current.isMoving()) {
                for (int i = 0; i < coordinates.length; i++) {
                    board[coordinates[i].getY() / 30][coordinates[i].getX() / 30] = current.getId();
                }
            }
            for (int i = 0; i < board.length; i++) {
                if (isFull(board[i])) {
                    rowClear = i;
                    clearRows.add(i);
                }
            }
            if (!current.isMoving() && isFull(board[rowClear])) {
//                this.clearLine(rowClear);
            	for (int i = 0; i < clearRows.size(); i++) {
            		this.clearLine(clearRows.get(i));
            	}
            	clearRows = new LinkedList<Integer>();
            }
            if (!current.isMoving()) {
                this.writeLinesToFile(this.boardToStrings(this.board), "files/save.csv", false);
                current = pieces.curr();
                int oldSize = pieces.getSize();
                pieces.generate();
                next.firePropertyChange("numPieces", oldSize, pieces.getSize());
            }
            if (current.isMoving() && !current.notOverlapping(board)) {
//            	current.tryAdjust(board);
            	if (!current.notOverlapping(board)) {
	            	playing = false;
	                status.setText("Game Over");
	                this.writeLinesToFile(
	                        this.boardToStrings(new int[20][10]), "files/save.csv", false
	                );
            	}
            }
            
            // update the display
            repaint();

        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        next.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        next.removePropertyChangeListener(listener);
    }

    public void clearLine(int row) {
        int col = board[0].length;
        int[][] copy = new int[row][col];
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        for (int j = 0; j < board[0].length; j++) {
            board[0][j] = 0;
        }
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (i == 0) {
                    board[i][j] = 0;
                } else {
                    board[i + 1][j] = copy[i][j];
                }
            }
        }
    }

    public void readSavedFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 20; i++) {
                String[] line = reader.readLine().split(", ");
                int[] lineAsInt = new int[line.length];
                for (int j = 0; j < line.length; j++) {
                    lineAsInt[j] = Integer.parseInt(line[j]);
                }
                board[i] = lineAsInt;
            }
            reader.close();
        } catch (IOException e) {
            status.setText("No saved data");
            board = new int[20][10];
        }
    }

    public void writeLinesToFile(List<String> lines, String filePath, boolean append) {
        File file = Paths.get(filePath).toFile();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
            for (String s : lines) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            this.status.setText("No file to save to");
        }
    }

    public List<String> boardToStrings(int[][] board) {
        LinkedList<String> ret = new LinkedList<String>();
        for (int i = 0; i < board.length; i++) {
            String line = "";
            for (int j = 0; j < board[0].length - 1; j++) {
                line += board[i][j] + ", ";
            }
            line += board[i][board[0].length - 1];
            ret.add(line);
        }
        return ret;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 2) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 3) {
                    g.setColor(Color.GREEN);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 4) {
                    g.setColor(Color.RED);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 5) {
                    g.setColor(Color.MAGENTA);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 6) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                if (board[i][j] == 7) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
            }
        }
        current.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}