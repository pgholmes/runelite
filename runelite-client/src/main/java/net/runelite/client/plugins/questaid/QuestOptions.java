package net.runelite.client.plugins.questaid;

public enum QuestOptions
{
    FREE("Free", "Free to all Players"),
    MEMBERS("Members", "Member Subscription Required");

    private final String name;
    private final String toolTipText;

    QuestOptions(String name, String toolTipText)
    {
        this.name = name;
        this.toolTipText = toolTipText;
    }

    public String getName()
    {
        return name;
    }

    public String getToolTipText()
    {
        return toolTipText;
    }
}
