package org.cis1200.RabbitCatcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RabbitCatcherTest {
    @Test
    public void createRabbit() {
        Rabbit rabbit = new Rabbit();
        assertEquals(rabbit.getPositionX(), 5);
        assertEquals(rabbit.getPositionX(), 5);
    }

    @Test
    public void switchInTurn() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        boolean turn = game.playerTurn();
        assertTrue(turn);
        board[5][6].unfill();
        game.playTurn(6, 5);
        turn = game.playerTurn();
        assertFalse(turn);
        game.rabbitMove();
        turn = game.playerTurn();
        assertTrue(turn);
    }

    @Test
    public void createBoard() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        assertEquals(board.length, board[0].length);
        assertEquals(11, board.length);
    }

    @Test
    public void numberOfInitialBlocks() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getFilled()) {
                    count++;
                }
            }
        }
        assertEquals(count, 12);
    }

    @Test
    public void coveredOnAllSides() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        int z = 0;
        int t = 10;
        for (int i = 0; i < 11; i++) {
            board[z][i].setFilled();
            board[t][i].setFilled();
            board[i][z].setFilled();
            board[i][t].setFilled();
        }
        assertTrue(game.rabbitMove());
    }

    @Test
    public void coveredCompletelyOnAllSides() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny())) {
                    board[i][j].setFilled();
                }
            }
        }
        assertFalse(game.rabbitMove());
    }

    @Test
    public void oneBunnyMove() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        board[5][6].unfill();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny()) && !(i == 5 && j == 6)) {
                    board[i][j].setFilled();
                }
            }
        }
        assertTrue(game.rabbitMove());
    }

    @Test
    public void playerMoveInvalid() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny()) && !(i == 5 && j == 6)) {
                    board[i][j].setFilled();
                }
            }
        }
        assertFalse(game.playTurn(3, 7));
    }

    @Test
    public void playerMoveValid() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        board[5][6].unfill();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny()) && !(i == 5 && j == 6)) {
                    board[i][j].setFilled();
                }
            }
        }
        assertTrue(game.playTurn(6, 5));
    }

    @Test
    public void bestBunnyMove1() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny())) {
                    board[i][j].setFilled();
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            board[5][i].unfill();
            board[i][5].unfill();
        }
        board[5][0].setFilled();
        board[0][5].setFilled();
        board[10][5].setFilled();
        game.rabbitMove();
        int x = game.getRabbit().getPositionX();
        int y = game.getRabbit().getPositionY();
        assertTrue(board[5][6].getBunny());
    }

    @Test
    public void bestBunnyMove2() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny())) {
                    board[i][j].setFilled();
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            board[5][i].unfill();
            board[i][5].unfill();
        }
        board[5][10].setFilled();
        board[0][5].setFilled();
        board[10][5].setFilled();
        game.rabbitMove();
        int x = game.getRabbit().getPositionX();
        int y = game.getRabbit().getPositionY();
        assertTrue(board[5][4].getBunny());
    }

    @Test
    public void bestBunnyMove3() {
        RabbitGame game = new RabbitGame();
        game.reset();
        Grass[][] board = game.getBoard();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (!(board[i][j].getBunny())) {
                    board[i][j].setFilled();
                }
            }
        }
        for (int i = 0; i < 11; i++) {
            board[5][i].unfill();
            board[i][5].unfill();
        }
        board[5][10].setFilled();
        board[5][0].setFilled();
        board[10][5].setFilled();
        game.rabbitMove();
        int x = game.getRabbit().getPositionX();
        int y = game.getRabbit().getPositionY();
        assertTrue(board[4][5].getBunny());
    }

}
