package com.model;

import java.util.Scanner;

public class Game {
    public enum GameMode {
        SINGLE_PLAYER, MULTIPLAYER
    }

    private GameMode gameMode;
    private GameParticipant player1;
    private GameParticipant player2;
    private boolean isGameStarted;

    public GameParticipant getPlayer1() {
        return player1;
    }

    public GameParticipant getPlayer2() {
        return player2;
    }

    public Game() {
        this.isGameStarted = false;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    // Overloaded method for testing purposes
    public void startGame(GameMode mode) {
        this.gameMode = mode;

        // Setup players or AI without requiring Scanner input
        this.player1 = new Player("Player 1");
        player1.placeShips(null, true); // Assuming automatic ship placement for testing

        if (mode == GameMode.SINGLE_PLAYER) {
            this.player2 = new AI("Computer");
            player2.placeShips(null, true);
        } else {
            this.player2 = new Player("Player 2");
            player2.placeShips(null, true);
        }

        this.isGameStarted = true;
        // playGame method logic can be adjusted or omitted as needed for testing
    }

    public void startGame(GameMode mode, Scanner scanner) {
        this.gameMode = mode;

        // Player 1 setup
        this.player1 = new Player("Player 1");
        setupPlayerShips(player1, scanner);

        // Player 2 or AI setup
        if (mode == GameMode.SINGLE_PLAYER) {
            this.player2 = new AI("Computer");
            player2.placeShips(scanner, true); // AI always places ships automatically
        } else {
            this.player2 = new Player("Player 2");
            setupPlayerShips(player2, scanner);
        }

        this.isGameStarted = true;
        playGame(scanner); // Start the game after setup
    }

    private void setupPlayerShips(GameParticipant player, Scanner scanner) {
        System.out.println("Choose Ship Placement for " + player.getName() + ": \n1. Manual\n2. Automatic");
        boolean isAutomatic = scanner.nextInt() == 2;
        player.placeShips(scanner, isAutomatic);
        System.out.println("All ships placed for " + player.getName() + ".");
    }

    private void playGame(Scanner scanner) {
        while (!player1.areAllShipsSunk() && !player2.areAllShipsSunk()) {
            // Player 1's turn
            if (playTurn(player1, player2, scanner)) break;

            // Player 2's turn
            if (playTurn(player2, player1, scanner)) break;
        }

        // Determine winner
        if (player1.areAllShipsSunk()) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println(player1.getName() + " wins!");
        }
    }

    private boolean playTurn(GameParticipant currentPlayer, GameParticipant opponent, Scanner scanner) {
        System.out.println("----------------------- " + currentPlayer.getName() + "'s TURN -----------------------");
        System.out.println("Your board:");
        currentPlayer.displayBoard(BoardViewType.PLAYER_VIEW);
        System.out.println("Enemy's Board:");
        opponent.displayBoard(BoardViewType.ENEMY_VIEW);

        if (currentPlayer instanceof AI) {
            // AI's turn, generate bombing coordinates automatically
            currentPlayer.bomb(-1, -1); // Just to generate coordinates
            int[] aiBombCoordinates = ((AI) currentPlayer).getLastBombingCoordinates();

            // Bomb the opponent's board using AI's generated coordinates
            Board.BombingResult result = opponent.getBoard().bombAt(aiBombCoordinates[0], aiBombCoordinates[1]);
            System.out.println(result.isHit() ? "Hit at " + aiBombCoordinates[0] + ", " + aiBombCoordinates[1] : "Miss at " + aiBombCoordinates[0] + ", " + aiBombCoordinates[1]);
            if (result.getSunkShip() != null) {
                System.out.println(currentPlayer.getName() + " has sunk a " + result.getSunkShip());
            }
        } else {
            // Human player's turn, prompt for input
            boolean validBombing = false;
            while (!validBombing) {
                System.out.println(currentPlayer.getName() + "'s turn. Enter coordinates to bomb (row column), or 'exit' to end turn:");
                String input = scanner.next();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println(currentPlayer.getName() + " has ended their turn.");
                    return true; // End turn
                }

                try {
                    int row = Integer.parseInt(input);
                    int column = scanner.nextInt();

                    if (opponent.getBoard().isBombingValid(row, column)) {
                        Board.BombingResult result = opponent.getBoard().bombAt(row, column);
                        System.out.println(result.isHit() ? "Hit at " + row + ", " + column : "Miss at " + row + ", " + column);
                        if (result.getSunkShip() != null) {
                            System.out.println(currentPlayer.getName() + " has sunk a " + result.getSunkShip());
                        }
                        validBombing = true; // Valid bombing attempt, proceed
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter row and column numbers.");
                    scanner.nextLine(); // Clear the buffer
                }
            }
        }

        // Display updated enemy view after the shot
        System.out.println(opponent.getName() + "'s board after bombing:");
        opponent.displayBoard(BoardViewType.ENEMY_VIEW);

        System.out.println("----------------------- END OF " + currentPlayer.getName() + "'s TURN -----------------------");
        return opponent.areAllShipsSunk();
    }

}
