package net.runelite.client.plugins.questaid.cache;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.questaid.QuestAidPlugin;
import net.runelite.client.plugins.questaid.QuestDefinition;
import net.runelite.client.plugins.questaid.QuestManifest;
import net.runelite.client.plugins.questaid.beans.Quest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@Slf4j
public class QuestCache
{
    private final HashMap<String, QuestDefinition> cachedQuests = new HashMap<>();
    private HashMap<String, String> manifestMap =  null;

    public QuestManifest getManifest(String filename)
    {
        try
        {
            InputStream manifestDataFile = QuestAidPlugin.class.getResourceAsStream(filename);
            QuestManifest manifest = new Gson().fromJson(new InputStreamReader(manifestDataFile), QuestManifest.class);

            if (!manifest.getQuests().isEmpty())
            {
                manifestMap = new HashMap<>();
                for (Quest quest : manifest.getQuests())
                {
                    manifestMap.put(quest.getName(), quest.getFile());
                }
            }

            return manifest;
        }
        catch (NullPointerException e)
        {
            log.error("Could not load manifest file:" + filename);
            return null;
        }

    }

    public QuestDefinition getQuest(String name)
    {
        try
        {
            if (manifestMap != null && !manifestMap.isEmpty())
            {
                if(cachedQuests.containsKey(name))
                {
                    return cachedQuests.get(name);
                }
                else
                {
                    if(cacheDefinition(name))
                    {
                        return cachedQuests.get(name);
                    }
                }
            }
        }
        catch (NullPointerException exception)
        {
            log.debug("Error in getQuest: " + exception.getMessage());
            return null;
        }

        return null;
    }

    private boolean cacheDefinition(String name)
    {
        if (name.isEmpty() || !manifestMap.containsKey(name))
        {
            return false;
        }

        try
        {
            InputStream definitionFile = QuestAidPlugin.class.getResourceAsStream("json/" + manifestMap.get(name));
            QuestDefinition questDefinition = new Gson().fromJson(new InputStreamReader(definitionFile), QuestDefinition.class);
            cachedQuests.put(name, questDefinition);
            return true;
        }
        catch (NullPointerException e)
        {
            return false;
        }
    }

    public boolean questExists(String questName)
    {
        return manifestMap.containsKey(questName);
    }

}
