package net.runelite.client.plugins.questaid.cache;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.questaid.helpers.ItemHelpers;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.HashMap;

@Slf4j
public class ItemCache
{
    private ItemManager itemManager;
    private final HashMap<Integer, ImageIcon> cachedItems = new HashMap<>();
    private final HashMap<String, Integer> itemNameToId = new HashMap<>();

    public void initialize(ItemManager itemManager)
    {
        this.itemManager = itemManager;
    }

    private boolean cacheItemIcon(int id)
    {
        BufferedImage itemIcon = itemManager.getImage(id);
        if ( itemIcon != null)
        {
            cachedItems.put(id, new ImageIcon(itemIcon));
        }

        return itemIcon != null;
    }

    public ImageIcon getItemIcon(int id)
    {
        if (cachedItems.containsKey(id))
        {
            return cachedItems.get(id);
        }
        else
        {
            if (cacheItemIcon(id))
            {
                return cachedItems.get(id);
            }
        }
        return null;
    }

    public ImageIcon getItemIcon(String itemName)
    {
        if(itemNameToId.containsKey(itemName))
        {
            return this.getItemIcon(itemNameToId.get(itemName));
        }

        int itemId = getItemIdFromName(itemName);
        if (itemId != -1)
        {
            ImageIcon image = this.getItemIcon(itemId);
            if(image != null)
            {
                itemNameToId.put(itemName, itemId);
            }

            return image;
        }

        return null;
    }

    public ImageIcon getItemIcon(int id, int quantity, boolean stackable)
    {
        try
        {
            return new ImageIcon(itemManager.getImage(id, quantity, stackable));
        }
        catch (Exception exception)
        {
            log.warn("Could not get item id: " + id);
            return null;
        }
    }

    public ImageIcon getItemIcon(String itemName, int quantity, boolean stackable)
    {
        if(itemNameToId.containsKey(itemName))
        {
            return this.getItemIcon(itemNameToId.get(itemName));
        }

        int itemId = getItemIdFromName(itemName);
        if (itemId != -1)
        {
            ImageIcon image = this.getItemIcon(itemId, quantity, stackable);
            if(image != null)
            {
                itemNameToId.put(itemName, itemId);
            }

            return image;
        }

        return null;
    }

    private int getItemIdFromName(String itemName)
    {
        String formattedName = ItemHelpers.formatItemName(itemName);
        int itemId;

        try
        {
            Field field = ItemID.class.getField(formattedName);
            if (field != null)
            {
                ItemID itemIdInstance = new ItemID();

                itemId = Integer.parseInt(field.get(itemIdInstance).toString());
                return itemId;
            }
        }
        catch (Exception exception)
        {
            log.warn("Could not get item icon from name: " + itemName);
        }

        return -1;
    }
}
