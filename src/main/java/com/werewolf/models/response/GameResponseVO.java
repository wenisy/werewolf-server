package com.werewolf.models.response;

public class GameResponseVO {
    private String roomNum;
    private String roleName;
    private String message;
    private Boolean voice;
    private Boolean daylight;
    private Boolean alive;

    public GameResponseVO() {
        roomNum = "";
        roleName = "";
        message = "";
        voice = false;
        daylight = false;
        alive = true;
    }

    public String getRoleName() {
        return roleName;
    }

    public GameResponseVO setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public Boolean getVoice() {
        return voice;
    }

    public GameResponseVO setVoice(Boolean voice) {
        this.voice = voice;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GameResponseVO setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getDaylight() {
        return daylight;
    }

    public GameResponseVO setDaylight(Boolean daylight) {
        this.daylight = daylight;
        return this;
    }

    public Boolean getAlive() {
        return alive;
    }

    public GameResponseVO setAlive(Boolean alive) {
        this.alive = alive;
        return this;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public GameResponseVO setRoomNum(String roomNum) {
        this.roomNum = roomNum;
        return this;
    }
}
