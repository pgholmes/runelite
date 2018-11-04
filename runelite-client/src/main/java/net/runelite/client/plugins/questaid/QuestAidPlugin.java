package net.runelite.client.plugins.questaid;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import java.awt.image.BufferedImage;

@PluginDescriptor(
        name = "Quest Aid",
        description = "Enable the Quest Aid",
        tags = {"panel", "players"},
        loadWhenOutdated = true
)

public class QuestAidPlugin extends Plugin
{
    private static final String QUESTLOOKUP = "Quest Lookup";

    @Inject
    @Nullable
    private Client client;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private Provider<MenuManager> menuManager;

    @Inject
    private SkillIconManager skillIconManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private QuestAidConfig config;

    private NavigationButton navButton;
    private QuestAidPanel questAidPanel;

    @Provides
    QuestAidConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(QuestAidConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        questAidPanel = injector.getInstance(QuestAidPanel.class);

        final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "icon.png");

        navButton = NavigationButton.builder()
                .tooltip("Quest Aid")
                .icon(icon)
                .priority(4)
                .panel(questAidPanel)
                .build();

        clientToolbar.addNavigation(navButton);

        if (config.questLookupOption() && client != null)
        {
            menuManager.get().addPlayerMenuItem(QUESTLOOKUP);
        }
    }

    @Override
    protected void shutDown() throws Exception
    {
        clientToolbar.removeNavigation(navButton);

        if (client != null)
        {
            menuManager.get().removePlayerMenuItem(QUESTLOOKUP);
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (event.getGroup().equals("questaid"))
        {
            if (client != null)
            {
                menuManager.get().removePlayerMenuItem(QUESTLOOKUP);
                if (config.questLookupOption())
                {
                    menuManager.get().addPlayerMenuItem(QUESTLOOKUP);
                }
            }
        }
    }

}
