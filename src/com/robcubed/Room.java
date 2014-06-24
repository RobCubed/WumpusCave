package com.robcubed;

public class Room {
    private RoomType roomType;
    private boolean explored;

    public Room(RoomType roomType, boolean explored) {
        super();
        this.roomType = roomType;
        this.explored = explored;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

}
