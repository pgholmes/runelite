package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Requirements
{

    @SerializedName("Points")
    @Expose
    private String points;
    @SerializedName("RequiredSkills")
    @Expose
    private List<RequiredSkill> requiredSkills = null;
    @SerializedName("Quests")
    @Expose
    private List<Quest> quests = null;
    @SerializedName("Coins")
    @Expose
    private String coins;
    @SerializedName("Items")
    @Expose
    private List<Item> items = null;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<RequiredSkill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<RequiredSkill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public String getCoins() {
        return coins;
    }
    public void setCoins(String coins) {
        this.coins = coins;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}