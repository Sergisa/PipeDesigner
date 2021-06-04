package com.sergisa;

import com.google.gson.Gson;
import com.sergisa.elements.AbstractTool;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    private Stroke stroke1;
    private static final Gson gson = new Gson();
    private static final String path = "/home/sergisa/.dragger/settings.json";
    private int gridStep = 10;
    private boolean snapToGrid = true;
    private int linesCount = 20;
    private int defaultIconSize = 35;
    private static Settings settingsInstance;
    private final List<AbstractTool> availableElements;

    private Settings() {
        availableElements = new ArrayList<>();

        /*for (File f : new File("src/main/resources/components").listFiles()) {

            availableElements.add(new Element(f.getPath(), "Ext - Ext angle"));
        }
        availableElements.add(new Element("src/main/resources/components/ext_ext_angle.png", "Ext - Ext angle"));
        availableElements.add(new Element("src/main/resources/components/int_int_angle.png", "Ext - Ext angle"));
        availableElements.add(new Element("src/main/resources/components/ext_int_angle.png", "Ext - Int angle"));
        availableElements.add(new Element("src/main/resources/components/angle_control.png", "Angle controlling terminal"));
        availableElements.add(new Element("src/main/resources/components/big_control.png", "Pipe control terminal"));
        availableElements.add(new Element("src/main/resources/components/broken_filt.png", "Ext - Int angle"));
        availableElements.add(new Element("src/main/resources/components/control.png", "Ext - Int angle"));
         */

    }

    public static Settings getInstance() {
        if (settingsInstance == null) {
            settingsInstance = new Settings();
        }
        return settingsInstance;
    }

    public Stroke getLinePattern() {
        float[] dashingPattern1 = {4f, 4f};
        stroke1 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 1, dashingPattern1, 2);
        return stroke1;
    }

    public int getGridStep() {
        return gridStep;
    }

    public void setGridStep(int gridStep) {
        this.gridStep = gridStep;
        Application.getInstance().getMainWinowInstance().setGridStep(gridStep);
        System.out.println("Set grid Step to " + gridStep);
    }

    public int getDefaultIconSize() {
        return defaultIconSize;
    }

    public void setDefaultIconSize(int defaultIconSize) {
        this.defaultIconSize = defaultIconSize;
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
            writer.write(gson.toJson(Settings.getInstance()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            FileReader reader = new FileReader(path);
            settingsInstance = gson.fromJson(reader, Settings.class);
            System.out.println(gson.fromJson(reader, Settings.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isSnapToGrid() {
        return snapToGrid;
    }

    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }

    public List<AbstractTool> getAvailableElements() {
        return availableElements;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "stroke1=" + stroke1 +
                ", gridStep=" + gridStep +
                ", linesCount=" + linesCount +
                '}';
    }
}
