package net.runelite.client.plugins.questaid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.runelite.client.plugins.questaid.beans.Quest;

import java.util.List;

public class QuestManifest {

    @SerializedName("Quests")
    @Expose
    private List<Quest> quests = null;

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

}
