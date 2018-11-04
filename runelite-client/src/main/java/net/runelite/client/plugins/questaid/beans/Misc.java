package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Misc
{

    @SerializedName("Description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}