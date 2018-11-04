package net.runelite.client.plugins.questaid.helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemHelpers
{
    public static String formatItemName(String itemName)
    {
        return  itemName.trim()
                        .replaceAll("[^a-zA-Z0-9\\s+]","")
                        .replaceAll("[\\s+]", "_")
                        .toUpperCase();
    }
}
