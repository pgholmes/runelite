package net.runelite.client.plugins.questaid.cache;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Slf4j
public class SkillCache
{
    private SkillIconManager skillIconManager;
    private final HashMap<String, BufferedImage> cachedSkills = new HashMap<>();

    public void initialize(SkillIconManager skillIconManager)
    {
        this.skillIconManager = skillIconManager;
    }

    private boolean cacheSkillIcon(Skill skill)
    {
        BufferedImage skillIcon = skillIconManager.getSkillImage(skill);
        if ( skill != null)
        {
            cachedSkills.put(skill.getName(), skillIcon);
        }

        return skillIcon != null;
    }

    public BufferedImage getSkillIcon(String name)
    {
       if (cachedSkills.containsKey(name))
       {
           return cachedSkills.get(name);
       }
       else
       {
           for (Skill skill : Skill.values())
           {
               if (name.trim().equalsIgnoreCase(skill.getName().trim()))
               {
                   if (cacheSkillIcon(skill))
                   {
                       return cachedSkills.get(name);
                   }
               }
           }
       }
       return null;
    }
}
