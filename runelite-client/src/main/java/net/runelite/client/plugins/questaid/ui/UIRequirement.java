package net.runelite.client.plugins.questaid.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
public class UIRequirement extends JPanel
{
    @Getter(AccessLevel.PUBLIC)
    public static final Border GREEN_BORDER = new CompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_COMPLETE_COLOR).darker()),
            BorderFactory.createEmptyBorder(7, 12, 7, 7));

    public static final Border RED_BORDER = new CompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_ERROR_COLOR).darker()),
            BorderFactory.createEmptyBorder(7, 12, 7, 7));

    public static final Border ORANGE_BORDER = new CompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, (ColorScheme.PROGRESS_INPROGRESS_COLOR).darker()),
            BorderFactory.createEmptyBorder(7, 12, 7, 7));

    public static final Border NO_BORDER = new EmptyBorder(7, 16, 7, 7);

    private final JPanel uiInfo;

    UIRequirement(String skillName, String supportText, ImageIcon icon, Border border)
    {
        setLayout(new BorderLayout());
        setBorder(border == null ? NO_BORDER : border);
        setBackground(ColorScheme.DARKER_GRAY_COLOR);

        uiInfo = new JPanel(new GridLayout(2, 1));
        uiInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        uiInfo.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH, 30));
        uiInfo.setBorder(new EmptyBorder(0, 0, 0, 0));

        JShadowedLabel uiLabelName = new JShadowedLabel(skillName);
        uiLabelName.setBorder(new EmptyBorder(0,10,0,0));
        uiLabelName.setForeground(Color.WHITE);
        uiInfo.add(uiLabelName);

        add(uiInfo, BorderLayout.CENTER);

        if(supportText != null && !supportText.trim().isEmpty())
        {
            setSupportText(supportText);
        }

        setIcon(icon);
    }

    private void setSupportText(String supportText)
    {
        JShadowedLabel uiSupportLabel = new JShadowedLabel(supportText);
        uiSupportLabel.setFont(FontManager.getRunescapeSmallFont());
        uiSupportLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        uiSupportLabel.setBorder(new EmptyBorder(0,10,0,0));
        uiSupportLabel.setText(supportText);
        uiInfo.add(uiSupportLabel);
    }

    private void setIcon(ImageIcon uiIcon)
    {
        if ( uiIcon != null)
        {
            JLabel uiIconLabel = new JLabel(uiIcon);
            uiIconLabel.setMinimumSize(new Dimension(36,32));
            uiIconLabel.setMinimumSize(new Dimension(36,32));
            uiIconLabel.setPreferredSize(new Dimension(36,32));
            uiIconLabel.setHorizontalAlignment(JLabel.CENTER);

            add(uiIconLabel, BorderLayout.LINE_START);
        }
    }
}
