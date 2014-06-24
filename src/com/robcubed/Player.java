package com.robcubed;

public class Player {
    boolean weapon;
    boolean alive;
    int score;
    int locationX;
    int locationY;

    public Player(int x, int y) {
        weapon = false;
        alive = true;
        score = 0;
        locationX = x;
        locationY = y;
    }

    public boolean isWeapon() {
        return weapon;
    }

    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void addScore(int i) {
        score = score + i;
    }
}
