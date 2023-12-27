package com.model;

public class Game {
    private String gameMode;
    private boolean isGameStarted;

    public Game() {
        this.isGameStarted = false;
    }

    public void startGame(String gameMode) {
        this.gameMode = gameMode;
        this.isGameStarted = true;
    }

    public String getGameMode() {
        return gameMode;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }
}
