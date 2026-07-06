package com.example.app;

import java.util.Scanner;

public class TicTacToe {

    private static final String PLAYER_ONE_SYMBOL = "🐔";
    private static final String PLAYER_TWO_SYMBOL = "🦖";
    private static final int BOARD_SIZE = 3;

    private final String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
    private final Scanner scanner;

    public TicTacToe(Scanner scanner) {
        this.scanner = scanner;
        for (String[] row : board) {
            java.util.Arrays.fill(row, null);
        }
    }

    public void play() {
        System.out.println("Welcome to Tic-Tac-Toe! " + PLAYER_ONE_SYMBOL + " vs " + PLAYER_TWO_SYMBOL);
        System.out.println("Enter a number 1-9 to place your symbol:");
        printPositionGuide();

        String currentSymbol = PLAYER_ONE_SYMBOL;
        int movesPlayed = 0;

        while (true) {
            printBoard();
            System.out.print("Player " + currentSymbol + "'s turn, pick a spot (1-9): ");
            int choice = readValidMove();
            int row = (choice - 1) / BOARD_SIZE;
            int col = (choice - 1) % BOARD_SIZE;
            board[row][col] = currentSymbol;
            movesPlayed++;

            if (hasWon(currentSymbol)) {
                printBoard();
                System.out.println(currentSymbol + " wins! Congratulations!");
                return;
            }

            if (movesPlayed == BOARD_SIZE * BOARD_SIZE) {
                printBoard();
                System.out.println("It's a draw!");
                return;
            }

            currentSymbol = currentSymbol.equals(PLAYER_ONE_SYMBOL) ? PLAYER_TWO_SYMBOL : PLAYER_ONE_SYMBOL;
        }
    }

    private int readValidMove() {
        while (true) {
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number between 1 and 9: ");
                continue;
            }

            if (choice < 1 || choice > 9) {
                System.out.print("Please enter a number between 1 and 9: ");
                continue;
            }

            int row = (choice - 1) / BOARD_SIZE;
            int col = (choice - 1) % BOARD_SIZE;
            if (board[row][col] != null) {
                System.out.print("That spot is taken, pick another (1-9): ");
                continue;
            }

            return choice;
        }
    }

    private boolean hasWon(String symbol) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (symbol.equals(board[i][0]) && symbol.equals(board[i][1]) && symbol.equals(board[i][2])) {
                return true;
            }
            if (symbol.equals(board[0][i]) && symbol.equals(board[1][i]) && symbol.equals(board[2][i])) {
                return true;
            }
        }
        return (symbol.equals(board[0][0]) && symbol.equals(board[1][1]) && symbol.equals(board[2][2]))
                || (symbol.equals(board[0][2]) && symbol.equals(board[1][1]) && symbol.equals(board[2][0]));
    }

    private void printPositionGuide() {
        System.out.println(" 1 | 2 | 3 ");
        System.out.println("---+---+---");
        System.out.println(" 4 | 5 | 6 ");
        System.out.println("---+---+---");
        System.out.println(" 7 | 8 | 9 ");
        System.out.println();
    }

    private void printBoard() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < BOARD_SIZE; c++) {
                String cell = board[r][c] != null ? board[r][c] : " ";
                line.append(" ").append(cell).append(" ");
                if (c < BOARD_SIZE - 1) {
                    line.append("|");
                }
            }
            System.out.println(line);
            if (r < BOARD_SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }
}
