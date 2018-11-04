package net.runelite.client.plugins.questaid.ui;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

import java.awt.*;

public class UIBuilder
{
    public GridBagConstraints buildGridBadConstraints()
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);

        return gridBagConstraints;
    }
    public UIQuestEntry buildUIQuest(String questName)
    {
        return new UIQuestEntry(questName);
    }

    public UISync buildUISync()
    {
        return new UISync(PluginPanel.PANEL_WIDTH - 20, 15);
    }

    public UISync buildUISync(Font font)
    {
        UISync uiSync = buildUISync();
        uiSync.setFont(font);

        return uiSync;
    }

    public UISort buildUISort(int width, int height)
    {
        return new UISort(width, height);
    }

    public IconTextField buildSearchBar(IconTextField.Icon icon)
    {
        IconTextField iconTextField = new IconTextField();
        iconTextField.setIcon(icon);
        iconTextField.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        iconTextField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        iconTextField.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        iconTextField.setMinimumSize(new Dimension(0, 30));

        return iconTextField;
    }

    public MaterialTabGroup buildMaterialTabGroup()
    {
        MaterialTabGroup tabGroup = new MaterialTabGroup();
        tabGroup.setLayout(new GridLayout(1, 5, 7, 7));

        return tabGroup;
    }

    public UIQuest buildSkeletonQuest()
    {
        UIQuest uiQuest = new UIQuest(this.buildGridBadConstraints(), false);
        return(uiQuest);
    }
}
