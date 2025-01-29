package org.cis1200.RabbitCatcher;

import java.io.*;
import java.util.*;
import java.util.Random;

public class RabbitGame {
    private static Grass[][] board;
    private boolean player1Turn = true;
    private boolean gameOver;
    private static Rabbit rabbit;
    Random rand = new Random();

    public RabbitGame() {
        reset();
    }

    public Grass[][] getBoard() {
        return board;
    }

    public boolean playerTurn() {
        return player1Turn;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public static void reset() {
        // Here we are creating the grass field
        board = new Grass[11][11];
        int squaresFilled = 12;
        List<int[]> unfilledSquares = new ArrayList<>();
        // Here we set the rabbit location to the center of the board.
        rabbit = new Rabbit();

        // Initialize the board and list of unfilled squares
        // Since we anyways have to initialize the Grass objects, we might as well
        // make unfilled squares.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Grass(i, j);
                if (i == 0 || i == 10 || j == 0 || j == 10) {
                    board[i][j].setEdge(true);
                }
                if ((i != rabbit.getPositionX() || j != rabbit.getPositionY())
                        && (i != 0 && i != 10 && j != 0 && j != 10)) {
                    unfilledSquares.add(new int[] { i, j });
                }
            }
        }

        board[rabbit.getPositionX()][rabbit.getPositionY()].setBunny(true);

        Random rand = new Random();
        // This is where we go through the list and add a random square
        for (int i = 0; i < squaresFilled; i++) {
            // Select a random square from the list of unfilled squares
            int index = rand.nextInt(unfilledSquares.size());
            int[] square = unfilledSquares.remove(index);
            // Within our mini array, we select the x,y values
            int x = square[0];
            int y = square[1];

            board[x][y].setFilled(); // Set the square as filled
        }
    }

    public static void save(String fileName) {
        try {
            BufferedWriter save = new BufferedWriter(new FileWriter(fileName, false));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j].getFilled()) {
                        save.write("1 ");
                    } else if (board[i][j].getEdge()) {
                        save.write("2 ");
                    } else if (board[i][j].getBunny()) {
                        save.write("3 ");
                    } else {
                        save.write("0 ");
                    }
                }
                save.newLine();
            }
            save.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("There is an IOException with save");
        }
    }

    public static void saved(String fileName) {
        Grass[][] newBoard = new Grass[board.length][board[0].length];
        try {
            BufferedReader saved = new BufferedReader(new FileReader(fileName));
            for (int i = 0; i < newBoard.length; i++) {
                String line = saved.readLine();
                String[] cells = line.trim().split(" ");
                for (int j = 0; j < newBoard[i].length; j++) {
                    if (cells[j].equals("1")) {
                        newBoard[i][j] = new Grass(i, j);
                        newBoard[i][j].setFilled();
                    } else if (cells[j].equals("2")) {
                        newBoard[i][j] = new Grass(i, j);
                        newBoard[i][j].setEdge(true);
                    } else if (cells[j].equals("3")) {
                        newBoard[i][j] = new Grass(i, j);
                        newBoard[i][j].setBunny(true);
                        rabbit.setPositionX(i);
                        rabbit.setPositionY(j);
                    } else {
                        newBoard[i][j] = new Grass(i, j);
                    }
                }
                board = newBoard;

            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("There is an FileNotFoundException with saved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkWinner() {
        if (board[rabbit.getPositionX()][rabbit.getPositionY()].getEdge()) {
            return 0;
            // 0 means that the player has lost and the bunny has won.
        } else if (rabbit.getPositionX() == 0 || rabbit.getPositionY() == 0
                || rabbit.getPositionX() == 10 || rabbit.getPositionY() == 10) {
            throw new IllegalArgumentException(
                    "We are at the edge, " +
                            "how is the first part not checked off?"
            );
        } else if (board[rabbit.getPositionX() + 1][rabbit.getPositionY()].getFilled()
                && board[rabbit.getPositionX()][rabbit.getPositionY() + 1].getFilled()
                && board[rabbit.getPositionX() - 1][rabbit.getPositionY()].getFilled()
                && board[rabbit.getPositionX()][rabbit.getPositionY() - 1].getFilled()) {
            return 1;
        }
        return -1;
    }

    public Grass getCell(int x, int y) {
        return board[x][y];
    }

    public boolean playTurn(int c, int r) {
        // Here i need to make sure that the clicked
        // square isn't filled and there's not bunny in that square
        try {
            if (board[r][c].getBunny() || board[r][c].getFilled() || board[r][c].getEdge()
                    || checkWinner() == 0 || checkWinner() == 1) {
                return false;
            } else {
                board[r][c].setFilled();
                player1Turn = false;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("There is an index out of bounds");
        }
        return true;
    }

    public boolean rabbitMove() {
        int startX = rabbit.getPositionX();
        int startY = rabbit.getPositionY();
        // This is a the list of all paths, we'll only add a list of points to this list
        // if
        // if we get to the edge of our board.
        List<List<int[]>> allPaths = new ArrayList<>();
        // We also have a mirror board that represents all of the points
        // just to make sure that we don't visit
        // the same spot multiple times for multiple iterations.
        boolean[][] visited = new boolean[11][11];

        // Return false if the game is over
        if (checkWinner() == 0 || checkWinner() == 1) {
            return false;
        }

        // Remove rabbit from the current position
        board[startX][startY].setBunny(false);
        // Queue to store paths --> each entry is {currentX, currentY, path}
        // Note we needed to make a helper for this.
        // This is basically how we do the BFS, we store the paths that are adjacent to
        // the current position and then we add to our total lists depending on that.
        Queue<PathNode> queue = new LinkedList<>();
        queue.add(new PathNode(startX, startY, new ArrayList<>()));

        // here we search each spot that is on the queue
        while (!queue.isEmpty()) {
            PathNode current = queue.poll();
            // Here we remove the head and look at everything
            // associated with what we removed
            int currentX = current.x;
            int currentY = current.y;
            List<int[]> path = current.path;
            // Mark the current position as visited
            visited[currentX][currentY] = true;
            if (currentX == 0 || currentX == 10 || currentY == 0 || currentY == 10) {
                allPaths.add(new ArrayList<>(path));
            }
            // Explore all possible moves: {dx, dy}
            int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
            for (int[] dir : directions) {
                int newX = currentX + dir[0];
                int newY = currentY + dir[1];

                // Check if the move is within bounds, not visited, and not filled
                // Not here we add the visited so that our rabbit doesn't consider going
                // backwards
                // and always wants to go towards the edge
                if (newX >= 0 && newX < 11 && newY >= 0 && newY < 11 &&
                        !visited[newX][newY] && !board[newX][newY].getFilled()) {

                    // Add the new position to the path and enqueue it
                    List<int[]> newPath = new ArrayList<>(path);
                    newPath.add(new int[] { newX, newY });

                    // Here we are adding the possible next paths.
                    // We have the current position of the path and a arraylist
                    // of the past paths.
                    queue.add(new PathNode(newX, newY, newPath));
                }
            }
        }

        // Choose the best path
        List<int[]> bestPath = null;
        int minLength = 200;
        for (List<int[]> path : allPaths) {
            if (path.size() < minLength) {
                minLength = path.size();
                bestPath = path;
            }
        }

        // If no valid path is found, we'll just use up down, right, left method.
        if (bestPath == null) {
            if (!(board[rabbit.getPositionX() + 1][rabbit.getPositionY()].getFilled())) {
                rabbit.setPositionX(rabbit.getPositionX() + 1);
                player1Turn = true;
            } else if (!(board[rabbit.getPositionX() - 1][rabbit.getPositionY()].getFilled())) {
                rabbit.setPositionX(rabbit.getPositionX() - 1);
                player1Turn = true;
            } else if (!(board[rabbit.getPositionX()][rabbit.getPositionY() + 1].getFilled())) {
                rabbit.setPositionY(rabbit.getPositionY() + 1);
                player1Turn = true;
            } else {
                rabbit.setPositionY(rabbit.getPositionY() - 1);
                player1Turn = true;
            }
            board[rabbit.getPositionX()][rabbit.getPositionY()].setBunny(true);
            return true;
        }

        // Move the rabbit along the first step of the best path
        int[] nextMove = bestPath.get(0);
        rabbit.setPositionX(nextMove[0]);
        rabbit.setPositionY(nextMove[1]);
        board[nextMove[0]][nextMove[1]].setBunny(true);

        // Set the turn to the player
        player1Turn = true;
        return true;
    }

    // Also note that we are adding paths with a list of int arrays
    // where the first value steps off of the bunny's starting value and thus the
    // next step is the 0th element of the shortest path.

    // Helper class to store a path node in the BFS
    class PathNode {
        // x and y are
        int x, y;
        List<int[]> path;

        PathNode(int x, int y, List<int[]> path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }

}
