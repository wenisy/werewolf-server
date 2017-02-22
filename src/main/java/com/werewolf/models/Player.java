package com.werewolf.models;


public class Player {

    private int sitId;
    private boolean ready;
    private boolean alive;
    private boolean campaign;
    private boolean sheriff;
    private Role role;
    private boolean[] skillsStatus;


    public int getSitId() {
        return sitId;
    }

    public void setSitId(int sitId) {
        this.sitId = sitId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isCampaign() {
        return campaign;
    }

    public void setCampaign(boolean campaign) {
        this.campaign = campaign;
    }

    public boolean isSheriff() {
        return sheriff;
    }

    public void setSheriff(boolean sheriff) {
        this.sheriff = sheriff;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean[] getSkillsStatus(){
        return skillsStatus;
    }

    public void setSkillsStatus(){
        switch(role.getName()){
            case "VILLAGER" :{
                if(this.isCampaign()){
                    skillsStatus = new boolean[]{false};
                }else{
                    skillsStatus = new boolean[]{true};
                }
            }break;
            case "WEREWOLF" :{
                if(this.isCampaign()){
                    skillsStatus = new boolean[]{false};
                }else{
                    skillsStatus = new boolean[]{true};
                }
            }break;
            case "PROPHET" :{
                if(this.isCampaign()){
                    skillsStatus = new boolean[]{false};
                }else{
                    skillsStatus = new boolean[]{true};
                }
            }break;
            case "WITCH" :{
                Witch witch = (Witch)role;
                if(this.isCampaign()){
                    skillsStatus = new boolean[]{false, witch.hasPoison(), witch.hasAntidote()};
                }else{
                    skillsStatus = new boolean[]{true, witch.hasPoison(), witch.hasAntidote()};
                }
            }break;
            case "HUNTER" :{
                Hunter hunter = (Hunter)role;
                if(this.isCampaign()){
                    skillsStatus = new boolean[]{false, hunter.hasSkill()};
                }else{
                    skillsStatus = new boolean[]{true, hunter.hasSkill()};
                }
            }break;
            default:
                break;
        }
    }

}
