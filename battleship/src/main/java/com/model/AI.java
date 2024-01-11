package com.model;

import java.util.Random;
import java.util.Scanner;

public class AI implements GameParticipant {
    private String name;
    private Board board;
    private Random random;
    private int[] lastBombingCoordinates = new int[2];

    public AI(String name) {
        this.name = name;
        this.board = new Board();
        this.random = new Random();
    }

    @Override
    public void placeShips(Scanner scanner, boolean automatic) {
        // Since AI always places ships automatically, we ignore the scanner and automatic flag
        board.placeAllShipsRandomly();
    }

    @Override
    public Board.BombingResult bomb(int x, int y) {
        // AI logic for generating bombing coordinates
        int row = random.nextInt(Board.getSize());
        int column = random.nextInt(Board.getSize());
        lastBombingCoordinates[0] = row;
        lastBombingCoordinates[1] = column;

        // Do not bomb here. Just return null as this method is now only for generating coordinates
        return null;
    }


    public int[] getLastBombingCoordinates() {
        return lastBombingCoordinates;
    }

    @Override
    public void displayBoard(BoardViewType viewType) {
        board.displayBoard(viewType);
    }

    @Override
    public boolean areAllShipsSunk() {
        return board.areAllShipsSunk();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}