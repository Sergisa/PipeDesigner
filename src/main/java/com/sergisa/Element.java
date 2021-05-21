package com.sergisa;

import javax.swing.*;
import java.awt.*;

public class Element {
    private String description;
    private String path;
    private ImageIcon icon;
    public Element(String iconPath, String description) {
        this(new ImageIcon(iconPath, description), Type.PIPE);
        this.description = description;
        this.path = iconPath;
    }

    public Element(ImageIcon icon, Type type) {
        int size = 70;
        this.icon = new ImageIcon(
                icon.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT), icon.getDescription()
        );
        this.description = this.icon.getDescription();
        this.icon.setDescription(description);
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Element{" +
                "description='" + description + '\'' +
                ", icon=" + icon +
                ", path=" + path +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private enum Type{
        TAP, TEE, PIPE,
    }

}
