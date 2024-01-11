package com.main;

import com.model.Game;
import com.model.Game.GameMode;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        System.out.println("Select Game Mode: \n1. Single Player\n2. Multiplayer");
        int gameModeSelection = scanner.nextInt();
        GameMode gameMode = (gameModeSelection == 1) ? GameMode.SINGLE_PLAYER : GameMode.MULTIPLAYER;

        // Start and play the game
        game.startGame(gameMode, scanner);

        scanner.close();
    }
}