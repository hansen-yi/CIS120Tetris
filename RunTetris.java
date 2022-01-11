package org.cis120.tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunTetris implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        final JFrame frame = new JFrame("Tetris");
        frame.setLocation(300, 300);

        // JOptionPane.showMessageDialog(frame, "test");
        String instructions = "The goal is to last as long as possible by clearing rows "
                + "by moving pieces. \n"
                + "Rows are cleared when they are filled up. \n"
                + "Use your arrow keys to move the pieces. Space bar drops the piece"
                + " straight down. \n"
                + "Have fun!";
        JOptionPane.showMessageDialog(frame, instructions, "How To Play", 1);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final Board court = new Board(status, 10, 20);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.LINE_END);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Start Over");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.startOver();
            }
        });
        control_panel.add(reset);

        // final JPanel next = new JPanel();
        // frame.add(next, BorderLayout.LINE_END);
        // final JPanel blocks = new NextPieces(court);
        // control_panel.add(blocks);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }
}