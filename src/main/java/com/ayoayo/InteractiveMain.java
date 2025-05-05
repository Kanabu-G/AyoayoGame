package com.ayoayo;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Interactive version of the Ayoayo game where users can input their moves.
 */
public class InteractiveMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create a new game
        Ayoayo game = new Ayoayo();
        
        // Get player names
        System.out.print("Enter name for Player 1: ");
        String player1Name = scanner.nextLine();
        
        System.out.print("Enter name for Player 2: ");
        String player2Name = scanner.nextLine();
        
        // Create players
        Player player1 = game.createPlayer(player1Name);
        Player player2 = game.createPlayer(player2Name);
        
        int currentPlayer = 1;
        boolean gameEnded = false;
        
        // Display game info
        System.out.println("\n===== AYOAYO GAME =====");
        System.out.println("Player 1: " + player1Name + " (bottom row)");
        System.out.println("Player 2: " + player2Name + " (top row)");
        System.out.println("Play moves counter-clockwise (to the right for each player)");
        System.out.println("==============================\n");
        
        // Game loop
        while (!gameEnded) {
            // Print the current board state
            System.out.println("\nCurrent board state:");
            game.printBoard();
            
            // Get the current player's move
            System.out.printf("\nPlayer %d (%s)'s turn. Enter pit number (1-6): ", 
                    currentPlayer, currentPlayer == 1 ? player1Name : player2Name);
            
            // Validate input
            int pitIndex;
            try {
                pitIndex = Integer.parseInt(scanner.nextLine());
                if (pitIndex < 1 || pitIndex > 6) {
                    System.out.println("Invalid pit index. Please enter a number between 1 and 6.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            
            // Make the move
            int[] result = game.playGame(currentPlayer, pitIndex);
            
            // Check if the game has ended
            String winnerStatus = game.returnWinner();
            if (winnerStatus.equals("Game has not ended")) {
                // Check if the player should take another turn
                if (game.shouldTakeAnotherTurn()) {
                    // Don't switch players - the current player gets another turn
                    System.out.println("Player " + currentPlayer + " takes another turn!");
                } else {
                    // Switch to the other player
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                }
            } else {
                gameEnded = true;
            }
        }
        
        // Print the final board state
        System.out.println("\nFinal board state:");
        game.printBoard();
        
        // Print the winner
        System.out.println("\n" + game.returnWinner());
        
        // Close the scanner
        scanner.close();
    }
}