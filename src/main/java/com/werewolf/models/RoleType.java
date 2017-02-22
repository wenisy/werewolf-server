package com.werewolf.models;

public enum RoleType {
    HUNTER("Hunter", "猎人"), PROPHET("Prophet", "预言家"), VILLAGER("Villager", "村民"), WEREWOLF("Werewolf", "狼人"), WITCH("Witch", "女巫");

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
