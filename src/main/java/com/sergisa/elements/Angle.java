package com.sergisa.elements;

import javax.swing.*;

public class Angle extends AbstractTool{

    public Angle(String description, Type type) {
        super(description, type);
    }

    enum Type implements ElementType{
        OUTER_INNER_INNER(""),
        INNER_INNER_OUTER(""),
        OUTER_OUTER_INNER("");

        ImageIcon imageFile;
        Type(ImageIcon icon) {
            imageFile = icon;
        }
        Type(String path) {
            this(new ImageIcon(path));
        }

        @Override
        public ImageIcon getIcon() {
            return imageFile;
        }
    }
}
