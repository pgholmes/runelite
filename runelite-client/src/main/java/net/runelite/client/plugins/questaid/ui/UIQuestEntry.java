package net.runelite.client.plugins.questaid.ui;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIQuestEntry extends JPanel
{
    private static final ImageIcon ARROW_RIGHT_ICON;

    private final String questName;

    @Getter(AccessLevel.PUBLIC)
    private boolean isSelected;

    static
    {
        ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(UIQuestEntry.class, "/util/arrow_right.png"));
    }

    UIQuestEntry(String questName)
    {
        this.questName = questName;

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(0, 5, 0, 0));
        setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));

        MouseListener hoverListener = new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent mouseEvent)
            {
                if (!isSelected)
                {
                    setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent)
            {
                if (!isSelected)
                {
                    updateBackground();
                }
            }
        };
        addMouseListener(hoverListener);

        JShadowedLabel uiLabelName = new JShadowedLabel(questName);
        uiLabelName.setForeground(Color.WHITE);

        JLabel arrowLabel = new JLabel(ARROW_RIGHT_ICON);

        add(uiLabelName, BorderLayout.WEST);
        add(arrowLabel, BorderLayout.EAST);

    }

    public void setQuestSelected(boolean selected)
    {
        isSelected = selected;
        this.updateBackground();
    }

    private void updateBackground()
    {
        setBackground(this.isSelected ? ColorScheme.DARKER_GRAY_HOVER_COLOR.brighter() : ColorScheme.DARKER_GRAY_COLOR);
    }

    @Override
    public void setBackground(Color color)
    {
        super.setBackground(color);
    }

    public String getQuestName()
    {
        return questName;
    }
}
