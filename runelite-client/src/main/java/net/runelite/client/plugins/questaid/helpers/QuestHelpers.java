package net.runelite.client.plugins.questaid.helpers;

import net.runelite.client.plugins.questaid.beans.Item;
import net.runelite.client.plugins.questaid.beans.RequiredSkill;
import net.runelite.client.plugins.questaid.beans.RewardSkill;
import net.runelite.client.plugins.questaid.cache.ItemCache;
import net.runelite.client.plugins.questaid.ui.UIQuest;
import net.runelite.client.plugins.questaid.ui.UIRequirement;

import javax.swing.*;

public class QuestHelpers
{
    public static String formatReqSkillSupportText(RequiredSkill requiredSkill)
    {
        if (requiredSkill.getBoostable().equalsIgnoreCase("1"))
        {
            return String.format("Level %s (Boostable)", requiredSkill.getLevel());
        }
        else {
            return String.format("Level %s", requiredSkill.getLevel());
        }
    }

    public static String formatItemSupportText(Item item)
    {
        if (item.getQuestObtainable() != null && item.getQuestObtainable().equalsIgnoreCase("1"))
        {
            return String.format("x%s (quest obtainable)", item.getQuantity() );
        }
        else {
            return String.format("x%s ", item.getQuantity());
        }
    }

    public static String formatRewardSkillSupportText(RewardSkill rewardSkill)
    {
        return String.format("%s experience", rewardSkill.getExperience());
    }

    public static UIQuest getErrorUIQuest(UIQuest uiQuest)
    {
        uiQuest.clear();
        uiQuest.setQuestName("Error");
        uiQuest.setStartLocation("I bet you thought you'd see quest help here.");
        uiQuest.addRequirement("Error", "Well this is embarrassing", null, UIRequirement.RED_BORDER);
        uiQuest.initialize();

        return uiQuest;
    }

    public static ImageIcon getItemIcon(Item item, ItemCache itemCache)
    {
        ImageIcon image;
        int itemId;

        if (item.getId().trim().equalsIgnoreCase(""))
        {
            itemId = 0;
        }
        else {
            try {
                itemId = Integer.parseInt(item.getId().trim());
            } catch (Exception exception) {
                return null;
            }
        }

        if (Integer.parseInt(item.getQuantity()) > 1) {
            if (itemId == 0) {
                image = itemCache.getItemIcon(item.getName(), Integer.parseInt(item.getQuantity()), true);
            } else {
                image = itemCache.getItemIcon(itemId, Integer.parseInt(item.getQuantity()), true);
            }
        } else {
            if (itemId == 0) {
                image = itemCache.getItemIcon(item.getName());
            } else {
                image = itemCache.getItemIcon(itemId);
            }
        }
        return image;
    }
}
