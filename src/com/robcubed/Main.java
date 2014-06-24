package com.robcubed;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean isRunning = true;

        int c = 0;

        while (c < 10 || c > 20) {
            System.out.print("Choose the size of your cave (10-20): ");
            while(!in.hasNextInt()) {
                System.out.print("That's not a number! Please try again: ");
                in.next();
            }
            c = in.nextInt();
            if (c < 10 || c > 20) {
                System.out.println("Error: Your input must be between 10 and 20.");
            }
        }
        // choose cave size:
        System.out.println();
        Map map = new Map(c);
        Player player = new Player(map.getEntranceX(), map.getEntranceY());
        System.out.println();
        System.out.println();
        System.out.println("Welcome to the cave! Type N to go North, S to go South, E to go East, and W to go West.");
        while (isRunning) {
            Movement.displayArea(map.getGrid(), player.getLocationX(), player.getLocationY());
            map.displayRoomInfo(player);
            System.out.print("[" + player.getScore() + " points earned] ");
            if (!player.weapon) {
                System.out.println("You are weak and have no weapon.");
            } else {
                System.out.println("You're armed and dangerous.");
            }
            System.out.print("Enter Direction (? for help) > ");
            String command = in.next().toLowerCase();
            System.out.println();
            Movement.processCommand(command, player, map.getGrid());
            System.out.println();
            System.out.println();
        }
        in.close();

    }
}
