package com.model;

public class Ship {
    public enum ShipType {
        CARRIER, BATTLESHIP, CRUISER, DESTROYER
    }

    private ShipType type;
    private int length;
    private int startX;
    private int startY;
    private boolean horizontal;
    private int hitCount;

    public Ship(ShipType type, int startX, int startY, boolean horizontal) {
        this.type = type;
        this.length = getLengthByType(type);
        this.startX = startX;
        this.startY = startY;
        this.horizontal = horizontal;
        this.hitCount = 0;
    }

    private int getLengthByType(ShipType type) {
        switch (type) {
            case CARRIER: return 5;
            case BATTLESHIP: return 4;
            case CRUISER: return 3;
            case DESTROYER: return 2;
            default: return 0;
        }
    }

    public boolean isSunk() {
        return hitCount == length;
    }

    public void registerHit() {
        hitCount++;
    }

    // Getters for ship properties (type, length, startX, startY, horizontal)
    public ShipType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public int getHitCount() {
        return hitCount;
    }
}