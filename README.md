# Ayoayo Game

A text-based implementation of Ayoayo, a  Nigerian board game (a variant of Mancala).

## Overview

This application allows players to:
- Play a turn-based version of Ayoayo with counter-clockwise seed movement
- Experience special gameplay features like extra turns and seed capturing
- Play interactively or run example games

## Requirements

- Java JDK 17 or later
- Apache NetBeans or any Java IDE

## Documentation

For detailed insights about the development process and implementation challenges, see the [Project Reflection](src/main/java/com/ayoayo/Reflection.txt) document.

## Game Rules

- Players have 6 pits with 4 seeds each and one store
- Play moves counter-clockwise (to the right)
- Special rules include:
  - Extra turn when last seed lands in your store
  - Capturing seeds when last seed lands in an empty pit on your side

## Getting Started

1. Clone the repository
2. Open the project in your preferred IDE
3. Run `InteractiveMain.java` to play an interactive game

## Project Structure

- `Ayoayo.java`: Core game logic
- `Player.java`: Player representation
- `Main.java` & `MainExample2.java`: Test Example game runs
- `InteractiveMain.java`: Interactive console interface

