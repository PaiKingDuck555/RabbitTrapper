package org.cis1200.RabbitCatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunMyGame implements Runnable {
    @Override
    public void run() {
        final JFrame frame = new JFrame("RabbitCatcher");
        frame.setLocation(1100, 1100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Main playing area
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem instruction1 = new JMenuItem("Step 1: Press [Reset] to start a new game \n");
        JMenuItem instruction2 = new JMenuItem(
                "Step 2: Click " +
                        "on a gray square to trap the bunny \n "
        );
        JMenuItem instruction3 = new JMenuItem(
                "Step 3: Completely " +
                        "surround the bunny \n "
        );
        JMenuItem instruction4 = new JMenuItem(
                "Step 4: If you want " +
                        "to save the board " +
                        "and take a break press [Save] \n"
        );
        JMenuItem instruction5 = new JMenuItem(
                "Step 5: If you want to see " +
                        "your most recently saved game press [Saved] \n"
        );
        JMenuItem instruction6 = new JMenuItem("GOOD LUCK!");

        // Add items to the popup menu
        popupMenu.add(instruction1);
        popupMenu.add(instruction2);
        popupMenu.add(instruction3);
        popupMenu.add(instruction4);
        popupMenu.add(instruction5);
        popupMenu.add(instruction6);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        final JButton saved = new JButton("Saved");
        saved.addActionListener(e -> board.saved());

        // Add a button to trigger the popup menu
        final JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the popup menu near the button
                popupMenu.show(
                        instructionsButton,
                        instructionsButton.getWidth() / 2,
                        instructionsButton.getHeight()
                );
            }
        });

        control_panel.add(reset);
        control_panel.add(save);
        control_panel.add(saved);
        control_panel.add(instructionsButton);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();
    }
}
