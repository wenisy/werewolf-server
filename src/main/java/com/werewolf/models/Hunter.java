package com.werewolf.models;

import java.util.HashMap;
import java.util.Map;

import static com.werewolf.models.RoleType.HUNTER;

public class Hunter extends Role{

    private boolean skillStatus = true;

    public Hunter() {
        super();
        this.actionMap.put("revenge", (Player target) -> revenge(target));
        this.roleType = HUNTER;
    }

    public boolean hasSkill() {
        return skillStatus;
    }

    public void setSkillStatus(boolean skillStatus) {
        this.skillStatus = skillStatus;
    }

    private Map<String, Object> revenge(Player target) {
        Map<String, Object> actionResult = new HashMap<>();

        if(hasSkill()){
            actionResult.put("kill", target);
        }
        return actionResult;
    }

}
