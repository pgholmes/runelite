package net.runelite.client.plugins.questaid.ui;

import net.runelite.client.plugins.questaid.QuestAidPlugin;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UISort extends JPanel
{
    private static final ImageIcon SORT_ICON_INACTIVE;
    private static final ImageIcon SORT_ICON_ACTIVE;

    public static final String ASCENDING = "ASCENDING";
    public static final String DESCENDING = "DESCENDING";

    public JLabel questField;

    private String currentOrdering = ASCENDING;

    static
    {
        SORT_ICON_INACTIVE = new ImageIcon(ImageUtil.getResourceStreamFromClass(QuestAidPlugin.class, "sort_inactive.png"));
        SORT_ICON_ACTIVE = new ImageIcon(ImageUtil.getResourceStreamFromClass(QuestAidPlugin.class, "sort_active.png"));
    }

    public UISort(int width, int height)
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(2, 0, 2, 0));
        setSize(width, height);

        JPanel column = new JPanel(new BorderLayout(7, 0));
        column.setBorder(new EmptyBorder(0, 5, 0, 5));

        questField = new JLabel();
        questField.setText("Sort");
        questField.setSize(new Dimension(width, height));
        questField.setOpaque(false);
        questField.setForeground(Color.white);

        JLabel sortLabel = new JLabel(SORT_ICON_INACTIVE);

        column.add(questField, BorderLayout.WEST);

        add(column, BorderLayout.CENTER);
        add(sortLabel, BorderLayout.WEST);

        MouseListener hoverListener = new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent mouseEvent)
            {
                sortLabel.setIcon(SORT_ICON_ACTIVE);
                sortLabel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent)
            {
                sortLabel.setIcon(SORT_ICON_INACTIVE);
                sortLabel.repaint();
            }
        };
        addMouseListener(hoverListener);
    }

    public String changeState()
    {
        currentOrdering = currentOrdering == ASCENDING ? DESCENDING : ASCENDING;
        return currentOrdering;
    }
}
