package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Board {
    private Tile[][] grid;
    private static final int SIZE = 10; // 10x10 board
    private List<Ship> ships;

    public static int getSize() {
        return SIZE;
    }

    public Board() {
        grid = new Tile[SIZE][SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Tile();
            }
        }
    }

    public static class BombingResult {
        private boolean hit;
        private Ship.ShipType sunkShip;

        public BombingResult(boolean hit, Ship.ShipType sunkShip) {
            this.hit = hit;
            this.sunkShip = sunkShip;
        }

        public boolean isHit() {
            return hit;
        }

        public Ship.ShipType getSunkShip() {
            return sunkShip;
        }
    }

    public boolean isBombingValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            System.out.println("Coordinates out of bounds. Try again.");
            return false;
        }

        Tile tile = grid[x][y];
        if (tile.isHit() || tile.isMissedBomb()) {
            System.out.println("Tile already bombed. Try again.");
            return false;
        }

        return true;
    }

    public BombingResult bombAt(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return new BombingResult(false, null); // Invalid coordinates
        }

        Tile tile = grid[x][y];
        if (tile.isHit() || tile.isMissedBomb()) {
            return new BombingResult(false, null); // Tile already bombed
        }

        if (tile.isOccupied()) {
            tile.hit(); // Mark the tile as hit
            if (tile.getShip().isSunk()) {
                return new BombingResult(true, tile.getShip().getType()); // Return hit and sunk ship type
            }
            return new BombingResult(true, null); // Return hit but no sunk ship
        } else {
            tile.markAsMissedBomb(); // Mark the tile as a missed bomb
            return new BombingResult(false, null); // Return miss
        }
    }

    private boolean isPlacementValid(int length, int startX, int startY, boolean horizontal) {
        if (horizontal) {
            if (startY + length > SIZE) return false;
        } else {
            if (startX + length > SIZE) return false;
        }

        for (int i = 0; i < length; i++) {
            int x = horizontal ? startX : startX + i;
            int y = horizontal ? startY + i : startY;

            if (grid[x][y].isOccupied()) return false;  // Check if the Tile is occupied

            // Check adjacent Tiles
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int adjX = x + dx;
                    int adjY = y + dy;
                    if (adjX >= 0 && adjX < SIZE && adjY >= 0 && adjY < SIZE && grid[adjX][adjY].isOccupied()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean placeShip(Ship.ShipType type, int startX, int startY, boolean horizontal) {
        Ship newShip = new Ship(type, startX, startY, horizontal);
        int length = newShip.getLength();

        if (!isPlacementValid(length, startX, startY, horizontal)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            int x = horizontal ? startX : startX + i;
            int y = horizontal ? startY + i : startY;
            grid[x][y].setShip(newShip);
        }

        ships.add(newShip);
        return true;
    }

    public boolean areAllShipsPlaced() {
        Map<Ship.ShipType, Integer> requiredShips = new HashMap<>();
        requiredShips.put(Ship.ShipType.CARRIER, 1);
        requiredShips.put(Ship.ShipType.BATTLESHIP, 1);
        requiredShips.put(Ship.ShipType.CRUISER, 2);
        requiredShips.put(Ship.ShipType.DESTROYER, 1);

        Map<Ship.ShipType, Integer> placedShips = new HashMap<>();
        for (Ship ship : ships) {
            placedShips.put(ship.getType(), placedShips.getOrDefault(ship.getType(), 0) + 1);
        }

        return placedShips.equals(requiredShips);
    }

    public void placeAllShipsRandomly() {
        placeShipRandomly(Ship.ShipType.CARRIER);
        placeShipRandomly(Ship.ShipType.BATTLESHIP);
        placeShipRandomly(Ship.ShipType.CRUISER);
        placeShipRandomly(Ship.ShipType.CRUISER);
        placeShipRandomly(Ship.ShipType.DESTROYER);
    }

    public void placeShipRandomly(Ship.ShipType type) {
        Random rand = new Random();
        boolean placed = false;
        while (!placed) {
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);
            boolean horizontal = rand.nextBoolean();
            placed = placeShip(type, x, y, horizontal);
        }
    }

    public void manualShipPlacement(Scanner scanner) {
        Ship.ShipType[] shipsToPlace = {
                Ship.ShipType.CARRIER,
                Ship.ShipType.BATTLESHIP,
                Ship.ShipType.CRUISER,
                Ship.ShipType.CRUISER,
                Ship.ShipType.DESTROYER
        };

        for (Ship.ShipType type : shipsToPlace) {
            boolean placed = false;
            while (!placed) {
                displayBoard(BoardViewType.PLAYER_VIEW);
                System.out.println("Placing " + type);
                System.out.println("Enter starting X coordinate (0-9) [row]:");
                int startX = scanner.nextInt();
                System.out.println("Enter starting Y coordinate (0-9) [column]:");
                int startY = scanner.nextInt();
                System.out.println("Horizontal placement? (true/false):");
                boolean horizontal = scanner.nextBoolean();

                placed = placeShipManually(type, startX, startY, horizontal);
                if (!placed) {
                    System.out.println("Invalid placement. Try again.");
                }
            }
        }
    }

    public boolean placeShipManually(Ship.ShipType type, int startX, int startY, boolean horizontal) {
        return placeShip(type, startX, startY, horizontal);
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public void displayBoard(BoardViewType viewType) {
        // Print column labels with extra spacing
        System.out.print("   "); // Three spaces before starting column labels
        for (int j = 0; j < grid[0].length; j++) {
            System.out.print(j + " "); // One space after each column label
        }
        System.out.println();

        // Print row labels and board state
        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " "); // Row label (assumes single digit, add space for alignment)
            for (int j = 0; j < grid[i].length; j++) {
                Tile tile = grid[i][j];
                char symbol = '.'; // Default symbol for water
                if (viewType == BoardViewType.PLAYER_VIEW) {
                    // Player's view logic
                    if (tile.isOccupied() && !tile.isHit()) {
                        symbol = getSymbolForShip(tile.getShip());
                    } else if (tile.isHit() && tile.isOccupied()) {
                        symbol = 'X';
                    } else if (tile.isMissedBomb()) {
                        symbol = 'O';
                    }
                } else {
                    // Enemy's view logic
                    if (tile.isHit() && tile.isOccupied()) {
                        if (tile.getShip().isSunk()) {
                            symbol = getSymbolForShip(tile.getShip());
                        } else {
                            symbol = 'X';
                        }
                    } else if (tile.isMissedBomb() || tile.isHit()) {
                        symbol = 'O';
                    }
                }
                System.out.print(symbol + " "); // Print each symbol with a space after it
            }
            System.out.println();
        }
    }

    public boolean areAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false; // If any ship is not sunk, return false
            }
        }
        return true; // All ships are sunk
    }

    private char getSymbolForShip(Ship ship) {
        if (ship == null) return '.';
        switch (ship.getType()) {
            case CARRIER: return 'C';
            case BATTLESHIP: return 'B';
            case CRUISER: return 'R';
            case DESTROYER: return 'D';
            default: return '.';
        }
    }
}