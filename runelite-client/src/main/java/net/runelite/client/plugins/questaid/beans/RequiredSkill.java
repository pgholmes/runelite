package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequiredSkill
{

    @SerializedName("Skill")
    @Expose
    private String skill;
    @SerializedName("Level")
    @Expose
    private String level;
    @SerializedName("Boostable")
    @Expose
    private String boostable;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBoostable() {
        return boostable;
    }

    public void setBoostable(String boostable) {
        this.boostable = boostable;
    }

}