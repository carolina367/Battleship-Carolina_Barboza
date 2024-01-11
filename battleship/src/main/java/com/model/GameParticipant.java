package com.model;

import java.util.Scanner;

public interface GameParticipant {
    void placeShips(Scanner scanner, boolean automatic);
    Board.BombingResult bomb(int x, int y);
    void displayBoard(BoardViewType viewType);
    boolean areAllShipsSunk();
    String getName();
    Board getBoard();
}