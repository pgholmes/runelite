package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rewards
{

    @SerializedName("Points")
    @Expose
    private String points;
    @SerializedName("RewardSkills")
    @Expose
    private List<RewardSkill> rewardSkills = null;
    @SerializedName("Coins")
    @Expose
    private String coins;
    @SerializedName("Items")
    @Expose
    private List<Item> items;
    @SerializedName("Misc")
    @Expose
    private List<Misc> misc = null;

    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }

    public List<RewardSkill> getRewardSkills() {
        return rewardSkills;
    }
    public void setRewardSkills(List<RewardSkill> rewardSkills) {
        this.rewardSkills = rewardSkills;
    }

    public String getCoins() {
        return coins;
    }
    public void setCoins(String coins) {
        this.coins = coins;
    }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    public List<Misc> getMisc() {
        return misc;
    }
    public void setMisc(List<Misc> misc) {
        this.misc = misc;
    }

}