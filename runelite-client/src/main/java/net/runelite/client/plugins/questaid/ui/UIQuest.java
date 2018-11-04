package net.runelite.client.plugins.questaid.ui;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
public class UIQuest extends JPanel
{
    private GridBagConstraints gridBag;
    private JPanel containerPanel = new JPanel();
    private JLabel questName = new JLabel();
    private JScrollPane scrollPane;
    private JShadowedLabel questStartLocation = new JShadowedLabel();
    private UICollapsibleMenu requirements;
    private UICollapsibleMenu rewards;
    private UICollapsibleMenu unlocked;

    UIQuest(GridBagConstraints gridBagConstraints, boolean enableCollapsibleMenus)
    {
        requirements = new UICollapsibleMenu("Requirements", enableCollapsibleMenus);
        rewards = new UICollapsibleMenu("Rewards", enableCollapsibleMenus);
        unlocked = new UICollapsibleMenu("Unlocks", enableCollapsibleMenus);

        gridBag = gridBagConstraints;
        gridBagConstraints.insets = new Insets(0, 0, 5, 0);

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setLayout(new GridBagLayout());

        questName.setForeground(Color.white);
        questName.setOpaque(false);
        questName.setBorder(new EmptyBorder(2, 5, 5, 0));

        questStartLocation.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        questStartLocation.setShadow(ColorScheme.MEDIUM_GRAY_COLOR);
        questStartLocation.setOpaque(false);
        questStartLocation.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        questStartLocation.setBorder(new EmptyBorder(0, 5, 0, 0));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        infoPanel.add(questName);
        infoPanel.add(questStartLocation);

        add(infoPanel, gridBag);
        gridBag.gridy++;

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        //containerPanel.setBorder(new EmptyBorder(0,0,0,0));
        containerPanel.setAutoscrolls(true);

        scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 220));

        add(scrollPane,gridBag);
        gridBag.gridy++;
    }
    public void setQuestName(String questName)
    {
        this.questName.setText("<html><p>" + questName + "</p></html>");
    }

    public void setStartLocation(String startLocation)
    {
        this.questStartLocation.setText("<html><p>" + startLocation + "</p></html>");
    }

    public void addRequirement(String skillName, String supportText, ImageIcon icon, Border border)
    {
        requirements.addMenuItem(new UIRequirement(skillName, supportText, icon, border));
    }

    public void addReward(String skillName, String supportText, ImageIcon icon, Border border)
    {
        rewards.addMenuItem(new UIRequirement(skillName, supportText, icon, border));
    }

    public void addUnlock(String skillName, String supportText, ImageIcon icon, Border border)
    {
        unlocked.addMenuItem(new UIRequirement(skillName, supportText, icon, border));
    }

    public void initialize()
    {
        containerPanel.add(requirements);
        containerPanel.add(rewards);
        containerPanel.add(unlocked);

        requirements.initialize();
        rewards.initialize();
        unlocked.initialize();

        validateAndRepaint();
    }

    public void clear()
    {
        setQuestName("");
        setStartLocation("");

        requirements.clear();
        rewards.clear();
        unlocked.clear();

        validateAndRepaint();
    }

    private void validateAndRepaint()
    {
        containerPanel.validate();
        containerPanel.repaint();

        scrollPane.validate();
        scrollPane.repaint();

        this.validate();
        this.repaint();
    }
}
