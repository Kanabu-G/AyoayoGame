package com.ayoayo;

import java.util.Arrays;

/**
 * Main class to run and test the Ayoayo game.
 */
public class Main {
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
        
        // Test the playGame method 
        System.out.println("Making first move - Player 1, Pit 3:");
        int[] result = game.playGame(1, 3);
        System.out.println(Arrays.toString(result));
        System.out.println();
        
        System.out.println("Making second move - Player 1, Pit 1:");
        game.playGame(1, 1);
        
        System.out.println("Making third move - Player 2, Pit 3:");
        game.playGame(2, 3);
        
        System.out.println("Making fourth move - Player 2, Pit 4:");
        game.playGame(2, 4);
        
        System.out.println("Making fifth move - Player 1, Pit 2:");
        game.playGame(1, 2);
        
        System.out.println("Making sixth move - Player 2, Pit 2:");
        game.playGame(2, 2);
        
        System.out.println("Making seventh move - Player 1, Pit 1:");
        game.playGame(1, 1);
        
        // Print the final board state
        System.out.println("\nFinal board state:");
        game.printBoard();
        
        // Check if there's a winner
        System.out.println("\nWinner: " + game.returnWinner());
    }
}