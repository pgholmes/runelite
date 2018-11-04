package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item
{

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("QuestObtainable")
    @Expose
    private String questObtainable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuestObtainable() {
        return questObtainable;
    }

    public void setQuestObtainable(String questObtainable) {
        this.questObtainable = questObtainable;
    }

}