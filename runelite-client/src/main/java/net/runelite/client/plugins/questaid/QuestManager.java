package net.runelite.client.plugins.questaid;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.questaid.beans.*;
import net.runelite.client.plugins.questaid.cache.ItemCache;
import net.runelite.client.plugins.questaid.cache.QuestCache;
import net.runelite.client.plugins.questaid.cache.SkillCache;
import net.runelite.client.plugins.questaid.helpers.QuestHelpers;
import net.runelite.client.plugins.questaid.ui.UIQuest;
import net.runelite.client.plugins.questaid.ui.UIRequirement;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
class QuestManager
{
    private static final String questFile = "Manifest.json";
    private ImageIcon questPointsIcon;

    private final QuestCache questCache = new QuestCache();
    private final SkillCache skillCache = new SkillCache();
    private final ItemCache itemCache = new ItemCache();
    private final List<Quest> freeQuests = new ArrayList<>();
    private final List<Quest> membersQuests = new ArrayList<>();
    private QuestManifest manifest;

    public QuestManager()
    {
        manifest = questCache.getManifest(questFile);
        if (manifest != null)
        {
            parseManifest();
        }

        try
        {
            questPointsIcon = new ImageIcon(ImageUtil.getResourceStreamFromClass(QuestAidPanel.class, "icon.png"));
        }
        catch (Exception exception)
        {
            log.warn("Couldn't open quest point icon");
        }
    }

    public void setManagers(SkillIconManager iconManager, ItemManager itemManager)
    {
        skillCache.initialize(iconManager);
        itemCache.initialize(itemManager);
    }

    private void parseManifest()
    {
        for (Quest quest : manifest.getQuests())
        {
            if(quest.getAvailability().equalsIgnoreCase(QuestOptions.FREE.getName()))
            {
                freeQuests.add(quest);
            }
            else if (quest.getAvailability().equalsIgnoreCase(QuestOptions.MEMBERS.getName()))
            {
                membersQuests.add(quest);
            }
            else
            {
                log.warn("Could Not Parse Availability for quest: " + quest.getName());
            }

            Collections.sort(freeQuests, Comparator.comparing(Quest::getName));
            Collections.sort(membersQuests, Comparator.comparing(Quest::getName));

        }
    }

    public List<Quest> getFreeQuestList()
    {
        return freeQuests;
    }

    public List<Quest> getMembersQuestList()
    {
        return membersQuests;
    }

    public UIQuest buildQuestFromSkeleton(String questName, UIQuest uiQuest)
    {
        try
        {
            QuestDefinition questDefinition = questCache.getQuest(questName);
            if (questDefinition == null)
            {
                throw new Exception();
            }

            uiQuest.setQuestName(questDefinition.getName());
            uiQuest.setStartLocation(questDefinition.getStartLocation());

            boolean hasRequirements = false;
            if (questDefinition.getRequirements().getQuests() != null)
            {
                hasRequirements = !questDefinition.getRequirements().getQuests().isEmpty();
                for (Quest quest : questDefinition.getRequirements().getQuests())
                {
                    uiQuest.addRequirement(quest.getName(), "completion required", questPointsIcon, null);
                }
            }

            if(Integer.parseInt(questDefinition.getRequirements().getPoints()) > 0)
            {
                if (!hasRequirements) { hasRequirements = true; }
                uiQuest.addRequirement(questDefinition.getRequirements().getPoints(), "quest points", questPointsIcon, UIRequirement.NO_BORDER);
            }

            if (questDefinition.getRequirements().getRequiredSkills() != null)
            {
                if (!hasRequirements) { hasRequirements = !questDefinition.getRequirements().getRequiredSkills().isEmpty(); }
                for (RequiredSkill requiredSkill : questDefinition.getRequirements().getRequiredSkills())
                {
                    BufferedImage image = skillCache.getSkillIcon(requiredSkill.getSkill());
                    uiQuest.addRequirement(requiredSkill.getSkill(), QuestHelpers.formatReqSkillSupportText(requiredSkill), new ImageIcon(image), UIRequirement.GREEN_BORDER);
                }
            }

            if (!questDefinition.getRequirements().getCoins().trim().isEmpty()
                    && Integer.parseInt( questDefinition.getRequirements().getCoins()) != 0)
            {
                if (!hasRequirements) { hasRequirements = true; }
                ImageIcon image = itemCache.getItemIcon(ItemID.COINS_995, Integer.parseInt(questDefinition.getRequirements().getCoins()), true);
                uiQuest.addRequirement(questDefinition.getRequirements().getCoins(), "coins", image, UIRequirement.NO_BORDER);
            }

            if (questDefinition.getRequirements().getItems() != null)
            {
                if (!hasRequirements) { hasRequirements = !questDefinition.getRequirements().getItems().isEmpty(); }
                for (Item requiredItem : questDefinition.getRequirements().getItems())
                {
                    uiQuest.addRequirement(requiredItem.getName(), QuestHelpers.formatItemSupportText(requiredItem), QuestHelpers.getItemIcon(requiredItem, itemCache), null);
                }
            }

            if (!hasRequirements)
            {
                uiQuest.addRequirement("No Requirements", "Nothing to see here", null, UIRequirement.GREEN_BORDER);
            }

            boolean hasRewards = false;
            if (Integer.parseInt( questDefinition.getRewards().getPoints()) > 0)
            {
                hasRewards = true;
                uiQuest.addReward(questDefinition.getRewards().getPoints(), "quest points", questPointsIcon, UIRequirement.NO_BORDER);
            }

            if (questDefinition.getRewards().getRewardSkills() != null)
            {
                if (!hasRewards) { hasRewards = !questDefinition.getRewards().getRewardSkills().isEmpty(); }
                for (RewardSkill rewardSkill : questDefinition.getRewards().getRewardSkills())
                {
                    BufferedImage image = skillCache.getSkillIcon(rewardSkill.getSkill());
                    uiQuest.addReward(rewardSkill.getSkill(), QuestHelpers.formatRewardSkillSupportText(rewardSkill), image == null ? null : new ImageIcon(image), UIRequirement.NO_BORDER);
                }
            }

            if (!questDefinition.getRewards().getCoins().trim().isEmpty()
                && Integer.parseInt( questDefinition.getRewards().getCoins()) != 0)
            {
                if (!hasRewards) { hasRewards = true; }
                ImageIcon image = itemCache.getItemIcon(ItemID.COINS_995, Integer.parseInt(questDefinition.getRewards().getCoins()), true);
                uiQuest.addReward(questDefinition.getRewards().getCoins(), "coins", image, UIRequirement.NO_BORDER);
            }

            if(questDefinition.getRewards().getItems() != null)
            {
                if (!hasRewards) { hasRewards = !questDefinition.getRewards().getItems().isEmpty(); }
                for (Item rewardItem : questDefinition.getRewards().getItems()) {

                    uiQuest.addReward(rewardItem.getName(), QuestHelpers.formatItemSupportText(rewardItem), QuestHelpers.getItemIcon(rewardItem, itemCache), null);
                }
            }

            if (questDefinition.getRewards().getMisc() != null)
            {
                if (!hasRewards) { hasRewards = !questDefinition.getRewards().getMisc().isEmpty(); }
                for(Misc miscEntry : questDefinition.getRewards().getMisc())
                {
                    uiQuest.addReward(miscEntry.getDescription(), "miscellaneous", null, UIRequirement.NO_BORDER);
                }
            }

            if (!hasRewards)
            {
                uiQuest.addReward("No Rewards", "Nothing to see here", null, UIRequirement.GREEN_BORDER);
            }

            boolean hasUnlocked = false;
            if (questDefinition.getUnlocks() != null)
            {
                hasUnlocked = !questDefinition.getUnlocks().isEmpty();
                for(Unlocks unlocked : questDefinition.getUnlocks())
                {
                    uiQuest.addUnlock(unlocked.getDescription(),
                            "is unlocked",
                            questCache.questExists(unlocked.getDescription()) ? questPointsIcon : null,
                            UIRequirement.NO_BORDER);
                }
            }

            if (!hasUnlocked)
            {
                uiQuest.addUnlock("No Unlocks", "Nothing to see here", null, UIRequirement.NO_BORDER);
            }

            uiQuest.initialize();
        }
        catch (Exception exception)
        {
            uiQuest = QuestHelpers.getErrorUIQuest(uiQuest);
        }
        return uiQuest;
    }
}
