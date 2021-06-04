package com.sergisa.elements;

import com.sergisa.DraggableComponent;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractTool extends DraggableComponent {
    protected String description;
    protected String path;
    protected ImageIcon icon;
    protected ElementType type;

    public AbstractTool(String description, ElementType type) {
        super(type.getIcon());
        type.getIcon().setDescription(description);
        this.description = description;
        this.type = type;
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

}
