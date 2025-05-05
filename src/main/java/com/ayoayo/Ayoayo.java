package com.ayoayo;

/**
 * Game logic for Ayoayo,.
 * This is a two-player game where each player tries to collect as many seeds
 * as possible in their store.
 */
public class Ayoayo {
    // Constants
    private static final int PITS_PER_PLAYER = 6;
    private static final int INITIAL_SEEDS_PER_PIT = 4;
    
    // Game state
    private Player player1;
    private Player player2;
    private int[] board; // Stores the number of seeds in each pit and store
    
    // Track whether the player should take another turn after their move
    private boolean lastPlayerTakesAnotherTurn = false;
    
    /**
     * Creates a new Ayoayo game with an initialized board.
     */
    public Ayoayo() {
        // board array layout to represent counter-clockwise movement:
        // [0-5]: player1's pits (left to right)
        // [6]: player1's store
        // [7-12]: player2's pits (right to left)
        // [13]: player2's store
        // 
        // Visual representation:
        // player2:   [12][11][10][9][8][7]
        // stores:    [13]                [6]
        // player1:   [0][1][2][3][4][5]
        //
        // Counter-clockwise movement (always to the right for each player)
        // Direction: 0->1->2->3->4->5->6->7->8->9->10->11->12->13->0->...
        
        board = new int[2 * PITS_PER_PLAYER + 2];
        initializeBoard();
    }
    
    /**
     * Initializes the board with the starting number of seeds in each pit.
     */
    private void initializeBoard() {
        // Place 4 seeds in each pit
        for (int i = 0; i < board.length; i++) {
            // Skip stores (indices 6 and 13)
            if (i == PITS_PER_PLAYER || i == 2 * PITS_PER_PLAYER + 1) {
                board[i] = 0;
            } else {
                board[i] = INITIAL_SEEDS_PER_PIT;
            }
        }
    }
    
    /**
     * Creates a new player with the given name.
     * 
     * @param name The name of the player
     * @return The created Player object
     */
    public Player createPlayer(String name) {
        if (player1 == null) {
            player1 = new Player(name);
            return player1;
        } else if (player2 == null) {
            player2 = new Player(name);
            return player2;
        } else {
            throw new IllegalStateException("Both players have already been created");
        }
    }
    
    /**
     * Prints the current state of the board.
     */
    public void printBoard() {
        if (player1 == null || player2 == null) {
            System.out.println("Cannot print board: Both players must be created first");
            return;
        }
        
        // Print player2's information first (top row)
        System.out.println("player2:");
        System.out.println("store: " + board[2 * PITS_PER_PLAYER + 1]);
        
        // Print player2's pits (in reverse order to match counter-clockwise view)
        System.out.print("[");
        for (int i = 2 * PITS_PER_PLAYER; i >= PITS_PER_PLAYER + 1; i--) {
            System.out.print(board[i]);
            if (i > PITS_PER_PLAYER + 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        // Print player1's information (bottom row)
        System.out.println("player1:");
        System.out.println("store: " + board[PITS_PER_PLAYER]);
        
        // Print player1's pits (from left to right)
        System.out.print("[");
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            System.out.print(board[i]);
            if (i < PITS_PER_PLAYER - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Checks if the player should take another turn after their last move.
     * 
     * @return true if the player should take another turn, false otherwise
     */
    public boolean shouldTakeAnotherTurn() {
        return lastPlayerTakesAnotherTurn;
    }
    
    /**
     * Determines if the game has ended.
     * 
     * @return true if the game has ended, false otherwise
     */
    private boolean isGameEnded() {
        // Check if all pits on either side are empty
        boolean player1PitsEmpty = true;
        boolean player2PitsEmpty = true;
        
        // Check player1's pits
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            if (board[i] > 0) {
                player1PitsEmpty = false;
                break;
            }
        }
        
        // Check player2's pits
        for (int i = PITS_PER_PLAYER + 1; i < 2 * PITS_PER_PLAYER + 1; i++) {
            if (board[i] > 0) {
                player2PitsEmpty = false;
                break;
            }
        }
        
        return player1PitsEmpty || player2PitsEmpty;
    }
    
    /**
     * Handles the end of the game by moving all remaining seeds to the respective player's store.
     */
    private void handleGameEnd() {
        // Move all seeds from player1's pits to their store
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            board[PITS_PER_PLAYER] += board[i];
            board[i] = 0;
        }
        
        // Move all seeds from player2's pits to their store
        for (int i = PITS_PER_PLAYER + 1; i < 2 * PITS_PER_PLAYER + 1; i++) {
            board[2 * PITS_PER_PLAYER + 1] += board[i];
            board[i] = 0;
        }
    }
    
    /**
     * Returns the winner of the game, if any.
     * 
     * @return A string describing the game result
     */
    public String returnWinner() {
        if (!isGameEnded()) {
            return "Game has not ended";
        }
        
        // If the game has ended, compare the number of seeds in each player's store
        int player1Score = board[PITS_PER_PLAYER];
        int player2Score = board[2 * PITS_PER_PLAYER + 1];
        
        if (player1Score > player2Score) {
            return "Winner is player 1: " + player1.getName();
        } else if (player2Score > player1Score) {
            return "Winner is player 2: " + player2.getName();
        } else {
            return "It's a tie";
        }
    }
    
    /**
     * Makes a move in the game for the specified player and pit.
     * 
     * @param playerIndex The player making the move (1 or 2)
     * @param pitIndex The pit from which to take seeds (1-6)
     * @return An array representing the current state of the board
     */
    public int[] playGame(int playerIndex, int pitIndex) {
        // Reset the take another turn flag
        lastPlayerTakesAnotherTurn = false;
        
        // Validate input parameters
        if (pitIndex <= 0 || pitIndex > PITS_PER_PLAYER) {
            System.out.println("Invalid number for pit index");
            return board.clone();
        }
        
        if (isGameEnded()) {
            System.out.println("Game is ended");
            return board.clone();
        }
        
        // Convert from 1-based indexing (user input) to 0-based indexing (array)
        int actualPitIndex;
        int playerStore;
        int opponentStore;
        
        if (playerIndex == 1) {
            actualPitIndex = pitIndex - 1; // Player 1's pits are 0-5
            playerStore = PITS_PER_PLAYER; // Player 1's store is at index 6
            opponentStore = 2 * PITS_PER_PLAYER + 1; // Player 2's store is at index 13
        } else { // playerIndex == 2
            // Player 2's pits are 7-12 but displayed in reverse
            // So pit 1 is actually at index 12, pit 2 at index 11, etc.
            actualPitIndex = 2 * PITS_PER_PLAYER - (pitIndex - 1);
            playerStore = 2 * PITS_PER_PLAYER + 1; // Player 2's store is at index 13
            opponentStore = PITS_PER_PLAYER; // Player 1's store is at index 6
        }
        
        // Check if the selected pit has seeds
        if (board[actualPitIndex] == 0) {
            System.out.println("Cannot play from an empty pit");
            return board.clone();
        }
        
        // Pick up seeds from the selected pit
        int seedsInHand = board[actualPitIndex];
        board[actualPitIndex] = 0;
        
        // Sow the seeds
        int lastIndex = sowSeeds(actualPitIndex, seedsInHand, playerIndex, playerStore, opponentStore);
        
        // Apply special rules
        boolean takeAnotherTurn = applySpecialRules(lastIndex, playerIndex, playerStore);
        
        // Check if the game has ended
        if (isGameEnded()) {
            handleGameEnd();
            takeAnotherTurn = false;
        }
        
        // If the player gets another turn, indicate it and update the flag
        if (takeAnotherTurn) {
            System.out.println("player " + playerIndex + " take another turn");
            lastPlayerTakesAnotherTurn = true;
        }
        
        // Return a copy of the current board state
        return board.clone();
    }
    
    /**
     * Sows seeds around the board starting from the given index.
     * 
     * @param startIndex The index to start sowing from
     * @param seedsInHand The number of seeds to sow
     * @param playerIndex The player making the move (1 or 2)
     * @param playerStore The index of the player's store
     * @param opponentStore The index of the opponent's store
     * @return The index where the last seed was placed
     */
    private int sowSeeds(int startIndex, int seedsInHand, int playerIndex, int playerStore, int opponentStore) {
        int currentIndex = startIndex;
        
        while (seedsInHand > 0) {
            // Move to the next pit
            currentIndex = (currentIndex + 1) % board.length;
            
            // Skip opponent's store
            if (currentIndex == opponentStore) {
                currentIndex = (currentIndex + 1) % board.length;
            }
            
            // Add a seed to the current pit
            board[currentIndex]++;
            seedsInHand--;
        }
        
        return currentIndex;
    }
    
    /**
     * Applies the special rules of the game.
     * 
     * @param lastIndex The index where the last seed was placed
     * @param playerIndex The player who made the move (1 or 2)
     * @param playerStore The index of the player's store
     * @return true if the player gets another turn, false otherwise
     */
    private boolean applySpecialRules(int lastIndex, int playerIndex, int playerStore) {
        // Special rule 1: If the last seed lands in the player's store, they get another turn
        if (lastIndex == playerStore) {
            return true;
        }
        
        // Special rule 2: If the last seed lands in an empty pit on the player's side,
        // the player captures that seed and all seeds in the opposite pit
        
        // Check if the last seed landed in an empty pit (now containing exactly 1 seed)
        if (board[lastIndex] == 1) {
            // Check if the last seed is on the player's side
            boolean isOnPlayerSide = false;
            
            if (playerIndex == 1 && lastIndex >= 0 && lastIndex < PITS_PER_PLAYER) {
                isOnPlayerSide = true;
            } else if (playerIndex == 2 && lastIndex >= PITS_PER_PLAYER + 1 && lastIndex < 2 * PITS_PER_PLAYER + 1) {
                isOnPlayerSide = true;
            }
            
            if (isOnPlayerSide) {
                // Calculate the index of the opposite pit
                int oppositePitIndex;
                
                // Calculate opposite pit index based on the board layout
                // Player 1's pits: 0-5, Player 2's pits: 7-12
                oppositePitIndex = 2 * PITS_PER_PLAYER - lastIndex;
                
                // Only capture if there are seeds in the opposite pit
                if (board[oppositePitIndex] > 0) {
                    // Add all seeds from both pits to the player's store
                    board[playerStore] += board[lastIndex] + board[oppositePitIndex];
                    
                    // Empty both pits
                    board[lastIndex] = 0;
                    board[oppositePitIndex] = 0;
                }
            }
        }
        
        return false;
    }
}