package com.sergisa.elements;

import javax.swing.*;
import java.io.File;

public class TapTee extends AbstractTool{

    public TapTee(String description, Type type) {
        super(description, type);
    }

    enum Type implements ElementType{
        OUTER_INNER(""),
        INNER_INNER(""),
        OUTER_OUTER("");

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
