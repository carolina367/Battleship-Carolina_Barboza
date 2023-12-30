package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Board {
    private Tile[][] grid;
    private final int size = 10; // 10x10 board
    private List<Ship> ships;

    public Board() {
        grid = new Tile[size][size];
        ships = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Tile();
            }
        }
    }


    private boolean isPlacementValid(int length, int startX, int startY, boolean horizontal) {
        if (horizontal) {
            if (startY + length > size) return false;
        } else {
            if (startX + length > size) return false;
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
                    if (adjX >= 0 && adjX < size && adjY >= 0 && adjY < size && grid[adjX][adjY].isOccupied()) {
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

        // Place the ship on the board
        for (int i = 0; i < length; i++) {
            int x = horizontal ? startX : startX + i;
            int y = horizontal ? startY + i : startY;
            grid[x][y].setShip(newShip);  // Set the ship in the Tile
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
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
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
                displayBoard(BoardViewType.PLAYER_VIEW);  // Display player's view of the board
                System.out.println("Placing " + type);
                System.out.println("Enter starting X coordinate (0-9):");
                int startX = scanner.nextInt();
                System.out.println("Enter starting Y coordinate (0-9):");
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
        // Print column labels
        System.out.print("  ");
        for (int j = 0; j < grid[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        // Print row labels and board state
        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " "); // Row label
            for (int j = 0; j < grid[i].length; j++) {
                Tile tile = grid[i][j];
                if (viewType == BoardViewType.PLAYER_VIEW) {
                    // Player's view logic
                    if (tile.isOccupied() && !tile.isHit()) {
                        System.out.print(getSymbolForShip(tile.getShip())); // Show all player's ships
                    } else if (tile.isHit() && tile.isOccupied()) {
                        System.out.print('X'); // Hit ship
                    } else {
                        System.out.print(tile.isMissedBomb() ? 'O' : '.'); // Missed bomb or water
                    }
                } else {
                    // Enemy's view logic
                    if (tile.isHit() && tile.isOccupied()) {
                        System.out.print('X'); // Hit ship
                    } else {
                        System.out.print(tile.isMissedBomb() || tile.isHit() ? 'O' : '.'); // Missed bomb, hit, or water
                    }
                }
            }
            System.out.println();
        }
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
