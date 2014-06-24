package com.robcubed;

public class Movement {

    public static void moveTo(String direction, Player player, Room[][] grid) {
        if (direction.equals("e")) {
            if (player.getLocationY() + 1 > grid.length) {
                System.out.println("If you want to flee, hit \"R\"");
            } else {
                if (grid[player.getLocationX()][player.getLocationY() + 1]
                        .getRoomType().equals(RoomType.wall)) {
                    System.out
                            .println("You can't go this way. There's a wall there. Stupid.");
                } else {
                    player.setLocationY(player.getLocationY() + 1);
                }
            }
        } else if (direction.equals("w")) {
            if (player.getLocationY() - 1 < 0) {
                System.out.println("If you want to flee, hit \"R\"");
            } else {
                if (grid[player.getLocationX()][player.getLocationY() - 1]
                        .getRoomType().equals(RoomType.wall)) {
                    System.out
                            .println("You can't go this way. There's a wall there. Stupid.");
                } else {
                    player.setLocationY(player.getLocationY() - 1);
                }
            }
        } else if (direction.equals("s")) {
            if (player.getLocationX() + 1 > grid.length) {
                System.out.println("If you want to flee, hit \"R\"");
            } else {
                if (grid[player.getLocationX() + 1][player.getLocationY()]
                        .getRoomType().equals(RoomType.wall)) {
                    System.out
                            .println("You can't go this way. There's a wall there. Stupid.");
                } else {
                    player.setLocationX(player.getLocationX() + 1);
                }
            }

        } else if (direction.equals("n")) {
            if (player.getLocationX() - 1 < 0) {
                System.out.println("If you want to flee, hit \"R\"");
            } else {
                if (grid[player.getLocationX() - 1][player.getLocationY()]
                        .getRoomType().equals(RoomType.wall)) {
                    System.out
                            .println("You can't go this way. There's a wall there. Stupid.");
                } else {
                    player.setLocationX(player.getLocationX() - 1);
                }
            }
        }
    }

    public static void displayArea(Room[][] grid, int gridX, int gridY) {

        for (int i = gridX - 1; i < gridX + 2; i++) {
            for (int j = gridY - 1; j < gridY + 2; j++) {
                if (i >= 0 && i <= (grid.length - 1) && j >= 0
                        && j <= (grid.length - 1)) {
                    if (j == gridY && i == gridX) {
                        System.out.print("@");
                    } else {
                        if (grid[i][j].isExplored()
                                || grid[i][j].getRoomType().equals(
                                        RoomType.wall)) {
                            System.out.print(grid[i][j].getRoomType()
                                    .getAsciiMap());
                        } else {
                            System.out.print("?");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    public static void processCommand(String command, Player player,
            Room[][] grid) {
        if (command.equals("n") || command.equals("s") || command.equals("e")
                || command.equals("w")) {
            moveTo(command, player, grid);
        } else if (command.equals("l")) {
            lootRoom(player, grid);
        } else if (command.equals("x")) {
            System.exit(1);
        } else if (command.equals("r")) {
            if (player.getScore() < 10) {
                System.out
                        .println("You flee from the dungeon! After a long run, you reach the nearby town.");
                System.out
                        .println("The locals get you drunk and listen to your made up tales. You survived");
                System.out.println("by being a coward, only scoring "
                        + player.getScore() + " points.");
            } else if (player.getScore() < 50) {
                System.out
                        .println("You flee from the dungeon! After a long run, you reach the nearby town.");
                System.out
                        .println("You're able to entertain the locals with your small adventure into the");
                System.out
                        .println("dungeon. You embelish a little, but you deserve the praise with your "
                                + player.getScore() + " points.");
            } else if (player.getScore() < 150) {
                System.out.println("You stumble home from the dungeon!");
                System.out
                        .println("Bruised and battered, with a five o'clock shadow and a significantly");
                System.out
                        .println("deeper voice than when you left, you are a very manly man. The town");
                System.out.println("is in awe of your manliness. "
                        + player.getScore() + " points.");
            } else {
                System.out
                        .println("You saunter out of the dungeon and stroll to the village.");
                System.out
                        .println("A true legend, you drop a sack of Wumpus heads on the floor of the tavern.");
                System.out
                        .println("Your name will be sung throughout the ages for ridding the world of so many");
                System.out.println("dastardly wumpus. You are legend. "
                        + player.getScore() + " points.");
            }
            System.exit(1);
        } else if (command.equals("?")) {
            System.out.println(" * * * * * HELP * * * * * ");
            System.out.println("To move North, South, East, or West, type N/S/E/W respectively.");
            System.out.println("To loot gold or weapons, type \"L\".");
            System.out.println("If you encounter a Wumpus with no weapon, you are dead.");
            System.out.println("If you fall into a pit, you are dead.");
            System.out.println("To flee the dungeon with your loot, type \"R\".");
            System.out.println("To exit the game completely, type \"X\".");
        } else {
            System.out.println("I don't understand that command. Type \"?\" for help.");
        }
    }

    private static void lootRoom(Player player, Room[][] grid) {
        if (grid[player.getLocationX()][player.getLocationY()].getRoomType()
                .equals(RoomType.gold)) {
            player.addScore(5);
            System.out.println("You picked up some gold! Have a few points.");
            grid[player.getLocationX()][player.getLocationY()]
                    .setRoomType(RoomType.empty);
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);
        } else if (grid[player.getLocationX()][player.getLocationY()]
                .getRoomType().equals(RoomType.weapon)) {
            player.setWeapon(true);
            player.addScore(5);
            System.out.println("You've picked up a weapon!");
            grid[player.getLocationX()][player.getLocationY()]
                    .setRoomType(RoomType.empty);
            grid[player.getLocationX()][player.getLocationY()]
                    .setExplored(true);

            // let's change the remaining weapons to gold...
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    if (grid[i][j].getRoomType().equals(RoomType.weapon)) {
                        grid[i][j] = new Room(RoomType.gold, false);
                    }
                }
            }
        } else {
            System.out.println("There's nothing to loot here.");
        }
    }
}
