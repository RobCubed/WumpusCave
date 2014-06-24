package com.robcubed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public enum RoomType {
    entrance(0, 0, '^'),
    wall(0, 0, '#'),
    wumpus(10, .15, 'X'),
    pit(0, .05, 'O'),
    gold(5, .15, '$'),
    weapon(5, .15, 'W'),
    empty(1, .50, '.');

    private int points;
    private double percentageGen;
    private char asciiMap;
    private int roomsGenned;
    private static List<RoomType> roomGenList = new ArrayList<RoomType>(Arrays.asList(RoomType.values()));
    private double percentage = 0;

    RoomType(int points, double gen, char asciiMap) {
        this.points = points;
        this.percentageGen = gen;
        this.asciiMap = asciiMap;
    }

    int getPoints() {
        return points;
    }

    double getPercentageGen() {
        return percentageGen;
    }

    char getAsciiMap() {
        return asciiMap;
    }

    public static RoomType randomRoom(int fullMapSize) {
        int x = 0;
        while (x == 0) {
            Iterator<RoomType> it = roomGenList.iterator();
            while (it.hasNext()) {
                RoomType room = it.next();
                if (room.getPercentageGen() == 0) {
                    it.remove();
                } else if (room.roomsGenned >= fullMapSize * fullMapSize * room.getPercentageGen()) {
                    it.remove();
                }
            }

            Double r = Math.random();

            // Add up remaining % - if less than 100%, we'll refactor the percentages used when generating a room
            double remainingPercentage = 0;
            for (RoomType room: roomGenList) {
                remainingPercentage = room.getPercentageGen() + remainingPercentage;
            }

            double currentPercentage = 0;
            for (RoomType room : roomGenList) {
                room.percentage = (1 / remainingPercentage) * room.getPercentageGen();
                currentPercentage = currentPercentage + room.percentage;
                if (r < currentPercentage) {
                    room.roomsGenned = room.roomsGenned + 1;
                    //System.out.println(room + " " + r + " "  + room.roomsGenned + " " + room.percentage);
                    return room;
                }
            }
        }

        return null;
    }
}
