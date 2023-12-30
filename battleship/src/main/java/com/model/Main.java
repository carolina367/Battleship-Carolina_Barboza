package com.main;

import com.model.Game;
import com.model.Board;
import com.model.BoardViewType;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the game
        Game game = new Game();
        Board board = new Board();

        // Game mode selection
        System.out.println("Select Game Mode: \n1. Single Player\n2. Multiplayer");
        int gameModeSelection = scanner.nextInt();
        Game.GameMode gameMode = (gameModeSelection == 1) ? Game.GameMode.SINGLE_PLAYER : Game.GameMode.MULTIPLAYER;

        // Ship placement choice
        System.out.println("Choose Ship Placement: \n1. Manual\n2. Automatic");
        int shipPlacementChoice = scanner.nextInt();

        if (shipPlacementChoice == 1) {
            // Manual ship placement
            board.manualShipPlacement(scanner);
        } else {
            // Automatic ship placement
            board.placeAllShipsRandomly();
        }

        // Start the game
        game.startGame(gameMode);

        // Check if all ships are placed
        if (board.areAllShipsPlaced()) {
            System.out.println("All ships placed successfully.");
        } else {
            System.out.println("Error in placing all ships.");
        }

        if (game.isGameStarted()) {
            System.out.println("Game started in mode: " + game.getGameMode());
        } else {
            System.out.println("Game failed to start.");
        }

        // Display the player's view
        System.out.println("Player's View:");
        board.displayBoard(BoardViewType.PLAYER_VIEW);

        // Display the enemy's view
        System.out.println("Enemy's View:");
        board.displayBoard(BoardViewType.ENEMY_VIEW);

        scanner.close();
    }
}
