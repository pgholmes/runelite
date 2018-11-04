package net.runelite.client.plugins.questaid;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("questaid")
public interface QuestAidConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "questLookupOption",
            name = "Quest Lookup",
            description = "Show Quest Lookup Option for Players"
    )
    default boolean questLookupOption()
    {
        return true;
    }
}
