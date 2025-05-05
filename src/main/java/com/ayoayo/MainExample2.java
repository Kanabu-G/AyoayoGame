package com.ayoayo;

import java.util.Arrays;

/**
 * Main second class to run and test the Ayoayo game.
 */
public class MainExample2 {
    public static void main(String[] args) {
        // Create a new game
        Ayoayo game = new Ayoayo();
        
        // Create two players
        Player player1 = game.createPlayer("Jensen");
        Player player2 = game.createPlayer("Brian");
        
        // Print the initial board state
        System.out.println("Initial board state:");
        game.printBoard();
        System.out.println();
        
        // Execute the moves 
        System.out.println("Making move - Player 1, Pit 1:");
        game.playGame(1, 1);
        
        System.out.println("Making move - Player 1, Pit 2:");
        game.playGame(1, 2);
        
        System.out.println("Making move - Player 1, Pit 3:");
        game.playGame(1, 3);
        
        System.out.println("Making move - Player 1, Pit 4:");
        game.playGame(1, 4);
        
        System.out.println("Making move - Player 1, Pit 5:");
        game.playGame(1, 5);
        
        System.out.println("Making move - Player 1, Pit 6:");
        game.playGame(1, 6);
        
        // Print the final board state
        System.out.println("\nFinal board state:");
        game.printBoard();
        
        // Check if there's a winner
        System.out.println("\nWinner: " + game.returnWinner());
    }
}