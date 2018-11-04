package net.runelite.client.plugins.questaid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quest
{

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("File")
    @Expose
    private String file;

    @SerializedName("Availability")
    @Expose
    private String availability;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

}