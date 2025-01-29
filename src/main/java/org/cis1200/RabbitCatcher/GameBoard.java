package org.cis1200.RabbitCatcher;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JPanel {
    private RabbitGame rabbitGame; // model for the game
    private JLabel status;
    private boolean playerTurn;

    public static final int BOARD_WIDTH = 1100;
    public static final int BOARD_HEIGHT = 1100;

    public GameBoard(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.status = status;

        rabbitGame = new RabbitGame();

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                rabbitGame.playTurn(p.x / 100, p.y / 100);
                playerTurn = rabbitGame.playerTurn();
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
                if (!playerTurn) {
                    rabbitGame.rabbitMove();
                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }
            }
        });

        this.status = status;
    }

    public void updateStatus() {
        if (playerTurn) {
            status.setText("It's Your Turn");
        } else {
            status.setText("Rabbit Processing...");
        }
        int win = rabbitGame.checkWinner();
        if (win == 0) {
            status.setText("Rabbit has Escaped! YOU LOSE!");
        } else if (win == 1) {
            status.setText("You've caught the Rabbit! Enjoy your Rabbit Stew! YOU WIN!");
        }

    }

    public void reset() {
        RabbitGame.reset();
        status.setText("It's Your Turn");
        repaint();
        playerTurn = true;

        requestFocusInWindow();
    }

    public void save() {
        RabbitGame.save("saveFile");
        status.setText("Game has been saved");
    }

    public void saved() {
        RabbitGame.saved("saveFile");
        repaint();
        playerTurn = true;
        status.setText("It's Your Turn");
        requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        int unitWidth = BOARD_WIDTH / 11;
        int unitHeight = BOARD_HEIGHT / 11;

        // Here we are drawing the board.
        g.drawLine(unitWidth, 0, unitWidth, BOARD_HEIGHT);
        g.drawLine(2 * unitWidth, 0, 2 * unitWidth, BOARD_HEIGHT);
        g.drawLine(3 * unitWidth, 0, 3 * unitWidth, BOARD_HEIGHT);
        g.drawLine(4 * unitWidth, 0, 4 * unitWidth, BOARD_HEIGHT);
        g.drawLine(5 * unitWidth, 0, 5 * unitWidth, BOARD_HEIGHT);
        g.drawLine(6 * unitWidth, 0, 6 * unitWidth, BOARD_HEIGHT);
        g.drawLine(7 * unitWidth, 0, 7 * unitWidth, BOARD_HEIGHT);
        g.drawLine(8 * unitWidth, 0, 8 * unitWidth, BOARD_HEIGHT);
        g.drawLine(9 * unitWidth, 0, 9 * unitWidth, BOARD_HEIGHT);
        g.drawLine(10 * unitWidth, 0, 10 * unitWidth, BOARD_HEIGHT);
        g.drawLine(unitWidth * 2, 0, unitWidth * 2, BOARD_HEIGHT);
        // Now we do the vertical lines on the board.
        g.drawLine(0, unitHeight, BOARD_WIDTH, unitHeight);
        g.drawLine(0, unitHeight * 2, BOARD_WIDTH, unitHeight * 2);
        g.drawLine(0, unitHeight * 3, BOARD_WIDTH, unitHeight * 3);
        g.drawLine(0, unitHeight * 4, BOARD_WIDTH, unitHeight * 4);
        g.drawLine(0, unitHeight * 5, BOARD_WIDTH, unitHeight * 5);
        g.drawLine(0, unitHeight * 6, BOARD_WIDTH, unitHeight * 6);
        g.drawLine(0, unitHeight * 7, BOARD_WIDTH, unitHeight * 7);
        g.drawLine(0, unitHeight * 8, BOARD_WIDTH, unitHeight * 8);
        g.drawLine(0, unitHeight * 9, BOARD_WIDTH, unitHeight * 9);
        g.drawLine(0, unitHeight * 10, BOARD_WIDTH, unitHeight * 10);
        rabbitGame.getRabbit().draw(g);
        // Fill in the appropriate areas with certain colors
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                // Here we are calling on the 2D array we made in RabbitGame to check
                // which squares are filled and are edges
                Grass state = rabbitGame.getCell(i, j);
                if (state.getFilled()) {
                    g.setColor(Color.green);
                    g.fillRect(100 * j, 100 * i, 100, 100);
                } else if (state.getEdge()) {
                    g.setColor(Color.RED);
                    g.fillRect(100 * j, 100 * i, 100, 100);
                }
            }
        }
    }
}
