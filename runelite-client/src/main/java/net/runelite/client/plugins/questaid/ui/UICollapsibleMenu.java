package net.runelite.client.plugins.questaid.ui;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.questaid.QuestAidPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@Slf4j
public class UICollapsibleMenu extends JPanel
{
    private static final ImageIcon EXPAND_ICON;
    private static final ImageIcon SHRINK_ICON;

    JPanel headerPanel = new JPanel();
    JPanel itemPanel = new JPanel();
    JLabel expandLabel = new JLabel(SHRINK_ICON);

    private boolean expanded = true;

    private ArrayList<Component> menuItems = new ArrayList<>();

    static
    {
        EXPAND_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(QuestAidPlugin.class, "expand.png"));
        SHRINK_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(QuestAidPlugin.class, "shrink.png"));
    }

    public UICollapsibleMenu(String menuName, boolean collapseEnabled)
    {
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(0,0,0,0));

        JShadowedLabel uiMenuName = new JShadowedLabel(menuName);
        uiMenuName.setForeground(Color.orange);

        headerPanel.setLayout(new GridLayout());
        headerPanel.setBorder(new EmptyBorder(0,0,0,0));
        headerPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        headerPanel.add(uiMenuName, BorderLayout.EAST);

        if (collapseEnabled)
        {
            headerPanel.add(expandLabel, BorderLayout.CENTER);
            headerPanel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (expanded)
                    {
                        while(itemPanel.getComponentCount() > 1)
                        {
                            log.debug("Expanded. itemPanel size: " + itemPanel.getComponentCount());
                            itemPanel.remove(1);
                        }
                        expandLabel = new JLabel(EXPAND_ICON);
                        expanded = false;
                        draw();

                    }
                    else
                    {
                        expandLabel = new JLabel(SHRINK_ICON);
                        expanded = true;
                        initialize();
                    }
                }
            });
        }

        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.add(headerPanel);
        add(itemPanel);
    }

    public void addMenuItem(Component menuItem)
    {
        menuItems.add(menuItem);
    }

    public void initialize()
    {
        for (Component component : menuItems)
        {
            itemPanel.add(component);
        }

        draw();
    }

    public void clear()
    {
        menuItems.clear();
        itemPanel.removeAll();
        itemPanel.add(headerPanel);

        draw();
    }

    private void draw()
    {
        expandLabel.validate();
        expandLabel.repaint();
        headerPanel.validate();
        headerPanel.repaint();
        itemPanel.validate();
        itemPanel.repaint();
        this.validate();
        this.repaint();
    }
}
