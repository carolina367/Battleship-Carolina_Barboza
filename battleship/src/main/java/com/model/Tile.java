package com.model;

public class Tile {
    private Ship ship;
    private boolean isHit;
    private boolean isMissedBomb;

    public Tile() {
        this.ship = null;
        this.isHit = false;
        this.isMissedBomb = false;
    }

    public boolean isOccupied() {
        return ship != null;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void hit() {
        isHit = true;
        if (ship != null) {
            ship.registerHit();
        }
    }

    public boolean isHit() {
        return isHit;
    }

    public void markAsMissedBomb() {
        this.isMissedBomb = true;
    }

    public boolean isMissedBomb() {
        return isMissedBomb;
    }
}
