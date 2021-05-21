package com.sergisa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ElementComboRenderer extends JLabel implements ListCellRenderer<Element>{
    private final int marginX = 10;
    private final int marginY = 10;

    @Override
    public Component getListCellRendererComponent(JList<? extends Element> list, Element value, int index, boolean isSelected, boolean cellHasFocus) {
        Border margin = new EmptyBorder(marginY,marginX,marginY,marginX);
        setBorder(margin);

        setText(value.getDescription());
        setIcon(new ImageIcon(value.getIcon().getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT)));
        return this;
    }
}
