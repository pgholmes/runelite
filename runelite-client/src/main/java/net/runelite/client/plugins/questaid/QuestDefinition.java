package net.runelite.client.plugins.questaid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.runelite.client.plugins.questaid.beans.Requirements;
import net.runelite.client.plugins.questaid.beans.Rewards;
import net.runelite.client.plugins.questaid.beans.Unlocks;

import java.util.List;

public class QuestDefinition
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startLocation")
    @Expose
    private String startLocation;
    @SerializedName("Requirements")
    @Expose
    private Requirements requirements;
    @SerializedName("Rewards")
    @Expose
    private Rewards rewards;
    @SerializedName("Unlocks")
    @Expose
    private List<Unlocks> unlocks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public Rewards getRewards() {
        return rewards;
    }

    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }

    public List<Unlocks> getUnlocks() {
        return unlocks;
    }

    public void setUnlocks(List<Unlocks> unlocks) {
        this.unlocks = unlocks;
    }

}