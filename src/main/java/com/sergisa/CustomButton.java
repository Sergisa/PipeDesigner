package com.sergisa;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(Icon icon) {

        super(resolveIcon((ImageIcon) icon));
    }

    public CustomButton() {

        super();
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        super.setIcon(resolveIcon((ImageIcon) defaultIcon));
    }

    private static Icon resolveIcon(ImageIcon icon) {
        return new ImageIcon(icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
    }
}
