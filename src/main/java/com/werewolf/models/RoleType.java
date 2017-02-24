package com.werewolf.models;

import static com.werewolf.models.Category.GODS;
import static com.werewolf.models.Category.MORTALS;
import static com.werewolf.models.Category.WOLVES;
import static com.werewolf.models.Faction.*;

public enum RoleType {
    HUNTER("hunter", "猎人", GOOD_GUYS, GODS),
    PROPHET("prophet", "预言家", GOOD_GUYS, GODS),
    WITCH("witch", "女巫", GOOD_GUYS, GODS),
    VILLAGER("villager", "村民", GOOD_GUYS, MORTALS),
    WEREWOLF("werewolf", "狼人", BAD_GUYS, WOLVES);

    private String type;
    private String displayName;
    private Faction faction;
    private Category category;

    RoleType(String type, String displayName, Faction faction, Category category) {
        this.type = type;
        this.displayName = displayName;
        this.faction = faction;
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Faction getFaction() {
        return faction;
    }

    public Category getCategory() {
        return category;
    }
}
