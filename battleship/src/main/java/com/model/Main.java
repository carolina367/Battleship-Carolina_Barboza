package com.main;

import com.model.Game;
import com.model.Board;
import com.model.BoardViewType;
import com.model.Board.BombingResult;
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

        // Bombing phase
        System.out.println("Bombing phase begins. Enter coordinates to bomb (format: row column), or 'exit' to stop:");
        while (true) {
            System.out.print("Enter coordinates: ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            int row = Integer.parseInt(input);
            int column = scanner.nextInt();

            // Validate before bombing
            if (!board.isBombingValid(row, column)) {
                continue; // Skip this iteration and prompt for new input
            }

            BombingResult result = board.bombAt(row, column);
            if (result.isHit()) {
                System.out.println("Hit at " + row + ", " + column);
                if (result.getSunkShip() != null) {
                    System.out.println("You've sunk a " + result.getSunkShip());
                }
            } else {
                System.out.println("Miss at " + row + ", " + column);
            }

            // Display the updated board
            System.out.println("Player's View:");
            board.displayBoard(BoardViewType.PLAYER_VIEW);

            System.out.println("Enemy's View:");
            board.displayBoard(BoardViewType.ENEMY_VIEW);

            if (board.areAllShipsSunk()) {
                System.out.println("All enemy ships have been sunk! You win!");
                break; // Break out of the game loop
            }
        }

        scanner.close();
    }
}