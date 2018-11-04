package net.runelite.client.plugins.questaid.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UISync extends JPanel
{
    public JLabel questField;

    public UISync(int width, int height)
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(2, 0, 2, 0));

        JPanel column = new JPanel(new BorderLayout(7, 0));
        column.setBorder(new EmptyBorder(0, 5, 0, 5));

        questField = new JLabel();
        questField.setText("Sync Player Skills");
        questField.setPreferredSize(new Dimension(width, height));
        questField.setOpaque(false);
        questField.setForeground(Color.white);

        column.add(questField, BorderLayout.WEST);

        add(column, BorderLayout.CENTER);

    }
}
