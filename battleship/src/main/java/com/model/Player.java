package com.model;

import java.util.Scanner;

public class Player implements GameParticipant {
    private String name;
    private Board board;

    @Override
    public Board getBoard() {
        return this.board;
    }

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

    @Override
    public void placeShips(Scanner scanner, boolean automatic) {
        if (automatic) {
            board.placeAllShipsRandomly();
        } else {
            board.manualShipPlacement(scanner);
        }
    }

    @Override
    public Board.BombingResult bomb(int x, int y) {
        return board.bombAt(x, y);
    }

    @Override
    public void displayBoard(BoardViewType viewType) {
        board.displayBoard(viewType);
    }

    @Override
    public boolean areAllShipsSunk() {
        return board.areAllShipsSunk();
    }

    public String getName() {
        return name;
    }
}