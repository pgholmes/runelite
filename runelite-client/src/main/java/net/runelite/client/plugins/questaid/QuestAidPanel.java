package net.runelite.client.plugins.questaid;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.plugins.questaid.beans.Quest;
import net.runelite.client.plugins.questaid.ui.UIBuilder;
import net.runelite.client.plugins.questaid.ui.UIQuest;
import net.runelite.client.plugins.questaid.ui.UIQuestEntry;
import net.runelite.client.plugins.questaid.ui.UISort;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;
import net.runelite.http.api.hiscore.HiscoreClient;
import net.runelite.http.api.hiscore.HiscoreEndpoint;
import net.runelite.http.api.hiscore.HiscoreResult;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static net.runelite.client.ui.ColorScheme.DARK_GRAY_COLOR;

@Slf4j
public class QuestAidPanel extends PluginPanel
{
    private final int MAX_USERNAME_LENGTH = 12;

    @Inject
    @Nullable
    private Client client;

    private final QuestAidConfig config;

    private final QuestManager questManager = new QuestManager();
    private final UIBuilder uiBuilder = new UIBuilder();

    private final IconTextField syncSearchBar;
    private final IconTextField questSearchBar;
    private final MaterialTabGroup tabGroup;
    private final JPanel questListContainer = new JPanel();
    private JPanel questContainer = new JPanel();
    private UISort uiSort;

    private QuestOptions activeTab;
    private boolean loading = false;

    private ArrayList<UIQuestEntry> freeQuests = new ArrayList<>();
    private ArrayList<UIQuestEntry> memberQuests = new ArrayList<>();

    private final HiscoreClient hiscoreClient = new HiscoreClient();
    private HiscoreResult playerSkills;

    @Inject
    public QuestAidPanel(QuestAidConfig config, SkillIconManager iconManager, ItemManager itemManager) {

        super();
        this.config = config;
        questManager.setManagers(iconManager, itemManager);

        setBorder(new EmptyBorder(5, 10, 0, 10));
        setBackground(DARK_GRAY_COLOR);
        setLayout(new GridBagLayout());

        GridBagConstraints c = uiBuilder.buildGridBadConstraints();

        add(uiBuilder.buildUISync(FontManager.getRunescapeSmallFont()), c);
        c.gridy++;

        syncSearchBar = uiBuilder.buildSearchBar(IconTextField.Icon.SEARCH);
        syncSearchBar.addActionListener(e -> getPlayerData(syncSearchBar.getText()));
        add(syncSearchBar, c);
        c.gridy++;

        tabGroup = uiBuilder.buildMaterialTabGroup();
        for (QuestOptions option : QuestOptions.values())
        {
            MaterialTab tab = new MaterialTab(option.getName(), tabGroup, null);
            tab.setBackground(ColorScheme.DARKER_GRAY_COLOR);
            tab.setToolTipText(option.getToolTipText());
            tab.setOnSelectEvent(() ->
            {
                if (loading)
                {
                    return false;
                }

                activeTab = option;

                displayQuests(activeTab);

                return true;
            });
            tabGroup.addTab(tab);
        }

        add(tabGroup, c);
        c.gridy++;

        uiSort = uiBuilder.buildUISort(125, 5);
        uiSort.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                switch(uiSort.changeState())
                {
                    case(UISort.ASCENDING):
                        freeQuests.sort(Comparator.comparing(UIQuestEntry::getQuestName));
                        memberQuests.sort(Comparator.comparing(UIQuestEntry::getQuestName));

                        break;
                    case(UISort.DESCENDING):
                        freeQuests.sort(Collections.reverseOrder(Comparator.comparing(UIQuestEntry::getQuestName)));
                        memberQuests.sort(Collections.reverseOrder(Comparator.comparing(UIQuestEntry::getQuestName)));
                        break;
                }

                displayQuests(activeTab);
            }
        });

        add(uiSort, c);
        c.gridy++;

        questSearchBar = uiBuilder.buildSearchBar(IconTextField.Icon.SEARCH);
        questSearchBar.addKeyListener(e -> filterQuestList(activeTab, questSearchBar.getText()));
        add(questSearchBar, c);
        c.gridy++;

        questListContainer.setLayout(new GridLayout(0, 1, 0, 1));
        questListContainer.setAutoscrolls(true);

        JScrollPane questListScrollFrame = new JScrollPane(questListContainer);
        questListScrollFrame.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 300));

        add(questListScrollFrame, c);
        c.gridy++;

        questContainer.setLayout(new BorderLayout());
        questContainer.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 300));

        add(questContainer, c);
        c.gridy++;

        tabGroup.select(tabGroup.getTab(0));

        drawUI(false);

        for (Quest quest : questManager.getFreeQuestList())
        {
            UIQuestEntry row = uiBuilder.buildUIQuest(quest.getName());
            row.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    questSelected(row);
                }
            });
            freeQuests.add(row);
        }

        for (Quest quest : questManager.getMembersQuestList())
        {
            UIQuestEntry row = uiBuilder.buildUIQuest(quest.getName());
            row.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    questSelected(row);
                }
            });
            memberQuests.add(row);
        }

        activeTab = QuestOptions.FREE;
        displayQuests(activeTab);
        drawUI(true);
    }

    @Override
    public void onActivate()
    {
        super.onActivate();
        syncSearchBar.requestFocusInWindow();

        if (syncSearchBar.getText().isEmpty() && !getLoggedInPlayer().isEmpty())
        {
            syncSearchBar.setText(getLoggedInPlayer());
            getPlayerData(getLoggedInPlayer());
        }
    }

    private String getLoggedInPlayer()
    {
        if(client != null && client.getGameState() == GameState.LOGGED_IN)
        {
            try
            {
                String playerName = client.getLocalPlayer().getName();
                return playerName == null ? "" : playerName;
            }
            catch (NullPointerException exception)
            {
                return "";
            }
        }
        return "";
    }

    // need to refactor away from hiscore
    private HiscoreResult lookupPlayer(String name)
    {
        if (!name.isEmpty())
        {
            try
            {
                HiscoreResult ironMan = hiscoreClient.lookup(name, HiscoreEndpoint.IRONMAN);
                if (ironMan == null)
                {
                    return hiscoreClient.lookup(name, HiscoreEndpoint.NORMAL);
                }
                return ironMan;
            }
            catch (IOException ex)
            {
                log.debug("Could not find stats for: " + name + ". Error: " + ex.getMessage());
            }
        }
        return null;
    }

    private void displayQuests(QuestOptions questOptions)
    {
        ArrayList<UIQuestEntry> questRows;
        questListContainer.removeAll();

        if(questOptions == QuestOptions.FREE)
        {
            for (UIQuestEntry freeQuest : freeQuests)
            {
                freeQuest.setQuestSelected(false);
            }
            questRows = freeQuests;
        }
        else
        {
            for (UIQuestEntry memberQuest : memberQuests)
            {
                memberQuest.setQuestSelected(false);
            }
            questRows = memberQuests;
        }

        for (UIQuestEntry row : questRows) {
            questListContainer.add(row);
        }

        if(!questSearchBar.getText().isEmpty())
        {
            filterQuestList(activeTab, questSearchBar.getText());
        }

        drawUI(true);

        questListContainer.revalidate();
        questListContainer.repaint();
    }

    private void getPlayerData(String name)
    {
        String trimmedName = name.trim();
        if (trimmedName.length() > MAX_USERNAME_LENGTH)
        {
            syncSearchBar.setIcon(IconTextField.Icon.ERROR);
            return;
        }
        else if(trimmedName.isEmpty())
        {
            if (!getLoggedInPlayer().isEmpty())
            {
                syncSearchBar.setText(getLoggedInPlayer());
                trimmedName = getLoggedInPlayer().trim();
            }
            else {
                loading = false;
                syncSearchBar.setEditable(true);
                return;
            }
        }

        loading = true;
        syncSearchBar.setEditable(false);

        playerSkills = lookupPlayer(trimmedName);
        if (playerSkills != null)
        {
            syncSearchBar.setEditable(true);
            syncSearchBar.setIcon(IconTextField.Icon.SEARCH);
            loading = false;
            tabGroup.select(tabGroup.getTab(0));
            return;
        }

        syncSearchBar.setEditable(true);
        if (!trimmedName.isEmpty())
        {
            syncSearchBar.setIcon(IconTextField.Icon.ERROR);
        }

        loading = false;
    }

    private void filterQuestList(QuestOptions activeTab, String filterText)
    {
        log.debug("in");
        ArrayList<UIQuestEntry> toFilterAgainst = activeTab == QuestOptions.FREE ? freeQuests : memberQuests;

        for (UIQuestEntry entry : toFilterAgainst)
        {
            log.debug("checking quest: " + entry.getQuestName());
            if(questContainsText(entry, filterText))
            {
                log.debug("quest contains");
                questListContainer.add(entry);
            }
            else
            {
                log.debug("quest doesn't contain");
                questListContainer.remove(entry);
            }
        }

        questListContainer.revalidate();
        questListContainer.repaint();
    }

    private boolean questContainsText(UIQuestEntry quest, String text)
    {
        return quest.getQuestName().toLowerCase().contains(text.toLowerCase());
    }

    private void drawUI(Boolean draw)
    {
        tabGroup.setVisible(draw);
        tabGroup.validate();
        tabGroup.repaint();

        uiSort.setVisible(draw);
        uiSort.validate();
        uiSort.repaint();

        if(!draw)
        {
            questListContainer.removeAll();
            questListContainer.validate();
            questListContainer.repaint();

            questContainer.removeAll();
            questContainer.validate();
            questContainer.repaint();
        }
    }

    private void questSelected(UIQuestEntry selectedRow)
    {
        if (activeTab == QuestOptions.FREE)
        {
            for (UIQuestEntry row : freeQuests)
            {
                if (row != selectedRow)
                {
                    row.setQuestSelected(false);
                }
                else
                {
                    row.setQuestSelected(true);
                }
            }
        }
        else
        {
            for (UIQuestEntry row : memberQuests)
            {
                if (row != selectedRow)
                {
                    row.setQuestSelected(false);
                }
                else
                {
                    row.setQuestSelected(true);
                }
            }
        }

        if(selectedRow.isSelected())
        {
            UIQuest uiQuest = questManager.buildQuestFromSkeleton(
                    selectedRow.getQuestName(),
                    uiBuilder.buildSkeletonQuest());

            hideQuest();

            questContainer.add(uiQuest);

            showQuest();
        }
    }

    private void showQuest()
    {
        questContainer.validate();
        questContainer.repaint();
        questContainer.setVisible(true);

        this.validate();
        this.repaint();
    }

    private void hideQuest()
    {
        questContainer.setVisible(false);
        questContainer.removeAll();
        questContainer.validate();
        questContainer.repaint();
    }
}
