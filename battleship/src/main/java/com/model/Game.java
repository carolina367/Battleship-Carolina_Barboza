package com.model;

public class Game {
    public enum GameMode {
        SINGLE_PLAYER, MULTIPLAYER
    }

    private GameMode gameMode;
    private boolean isGameStarted;

    public Game() {
        this.isGameStarted = false;
    }

    public void startGame(GameMode mode) {
        this.gameMode = mode;
        this.isGameStarted = true;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }
}
