package com.robcubed;

import java.util.Random;

public class Map {
    private Room[][] grid;
    private int entranceX = 0;
    private int entranceY = 0;

    public Room[][] getGrid() {
        return grid;
    }

    public Map(int mapSize) {
        int fullMapSize = mapSize + 2;
        grid = new Room[fullMapSize][fullMapSize];

        for (int i = 0; i < fullMapSize; i++) {
            for (int j = 0; j < fullMapSize; j++) {
                if (i == 0 || i == fullMapSize - 1 || j == 0
                        || j == fullMapSize - 1) {
                    grid[i][j] = new Room(RoomType.wall, false);
                } else {
                    // System.out.println("Genning a new room:");
                    grid[i][j] = new Room(RoomType.randomRoom(mapSize), false);
                }
            }
        }

        placeEntrance();

        // display full map:
        for (int i = 0; i < fullMapSize; i++) {
            for (int j = 0; j < fullMapSize; j++) {
                System.out.print(grid[i][j].getRoomType().getAsciiMap());
            }
            System.out.println();
        }
    }

    public void placeEntrance() {
        boolean valid = false;

        while (valid == false) {
            double r1 = Math.random(); // X or Y wall
            double r2 = Math.random(); // 0 or Last
            Random rn = new Random();
            int row = rn.nextInt(grid.length - 2) + 1;
            if (r1 > 0.5) {
                if (r2 > 0.5) {
                    if (checkEntranceRoom(1, row)) {
                        grid[0][row] = new Room(RoomType.entrance, true);
                        setEntranceX(0);
                        setEntranceY(row);
                        valid = true;
                    }
                } else {
                    if (checkEntranceRoom(grid.length - 2, row)) {
                        grid[grid.length - 1][row] = new Room(
                                RoomType.entrance, true);
                        setEntranceX(grid.length - 1);
                        setEntranceY(row);
                        valid = true;
                    }
                }
            } else {
                if (r2 > 0.5) {
                    if (checkEntranceRoom(row, 1)) {
                        grid[row][0] = new Room(RoomType.entrance, true);
                        setEntranceX(row);
                        setEntranceY(0);
                        valid = true;
                    }
                } else {
                    if (checkEntranceRoom(row, grid.length - 2)) {
                        grid[row][grid.length - 1] = new Room(
                                RoomType.entrance, true);
                        setEntranceX(row);
                        setEntranceY(grid.length - 1);
                        valid = true;
                    }
                }
            }

        }
    }

    private boolean checkEntranceRoom(int gridX, int gridY) {
        // System.out.println(gridX + " " + gridY);
        RoomType roomType = grid[gridX][gridY].getRoomType();
        if (roomType.equals(RoomType.gold) || roomType.equals(RoomType.empty)
                || roomType.equals(RoomType.weapon)) {
            // System.out.println(roomType + " success!");
            return true;
        } else {
            // System.out.println(roomType + " fail!");
            return false;
        }
    }

    public int getEntranceX() {
        return entranceX;
    }

    public void setEntranceX(int entranceX) {
        this.entranceX = entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public void setEntranceY(int entranceY) {
        this.entranceY = entranceY;
    }

    public void displayRoomInfo(Player player) {
        RoomType roomType = grid[player.getLocationX()][player.getLocationY()]
                .getRoomType();
        if (roomType.equals(RoomType.entrance)) {
            System.out
                    .println("This is the entrance. You leaving already? Head inside!");
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);
        } else if (roomType.equals(RoomType.wumpus)) {
            if (player.weapon) {
                System.out
                        .println("There's a wumpus! You charge him and slay him with your weapon.");
                player.addScore(10);
                grid[player.getLocationX()][player.getLocationY()]
                        .setRoomType(RoomType.empty);
                grid[player.getLocationX()][player.getLocationY()]
                        .setExplored(true);
            } else {
                System.out.println("Chomp. A wumpus eats your ass.");
                System.out.println("**** GAME OVER ****");
                System.out.println("You scored " + player.getScore()
                        + " points!");
                grid[player.getLocationX()][player.getLocationY()]
                        .setExplored(true);
                System.exit(1);
            }
        } else if (roomType.equals(RoomType.pit)) {
            System.out
                    .println("You place your foot through the door, only to find there is no floor... falling for infinity, you are.");
            System.out.println("**** GAME OVER ****");
            System.out.println("You scored " + player.getScore() + " points!");
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);
            System.exit(1);
        } else if (roomType.equals(RoomType.gold)) {
            System.out
                    .println("Oooh, delicious delicious gold! Loot it by typing \"L\".");
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);
        } else if (roomType.equals(RoomType.weapon)) {
            System.out.println("You found a weapon. Type \"L\" to pick it up!");
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);
        } else if (roomType.equals(RoomType.empty)) {
            System.out.println("There's nothing here.");
            if (!grid[player.getLocationX()][player.getLocationY()]
                    .isExplored()) {
                player.addScore(1);
                grid[player.getLocationX()][player.getLocationY()]
                        .setExplored(true);
            }
        }
    }
}
