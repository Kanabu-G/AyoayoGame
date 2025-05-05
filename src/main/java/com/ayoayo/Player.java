package com.ayoayo;

/**
 * Represents a player in the Ayoayo game.
 */
public class Player {
    private String name;
    
    /**
     * Creates a new player with the given name.
     * 
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
    }
    
    /**
     * Gets the name of the player.
     * 
     * @return The player's name
     */
    public String getName() {
        return name;
    }
}