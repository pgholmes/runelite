package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardSkill
{

    @SerializedName("Skill")
    @Expose
    private String skill;
    @SerializedName("Experience")
    @Expose
    private String experience;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

}