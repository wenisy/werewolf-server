package com.werewolf.models;

public enum RoleType {
    HUNTER("hunter", "猎人"), PROPHET("prophet", "预言家"), VILLAGER("villager", "村民"), WEREWOLF("werewolf", "狼人"), WITCH("witch", "女巫");

    private String type;
    private String displayName;

    RoleType(String type, String displayName) {
        this.type = type;
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }
}
