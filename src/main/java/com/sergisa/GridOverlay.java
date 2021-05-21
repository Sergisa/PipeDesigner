package com.sergisa;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridOverlay extends JPanel {
    private static final int LINES = Settings.getInstance().getLinesCount();

    private static int LINE_SPACING = Settings.getInstance().getGridStep();
    private List<Line> gridLines;
    private boolean gridVisible = true;

    public GridOverlay(LayoutManager layout) {
        super(layout);
    }

    public GridOverlay() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gridVisible) gridLines = generateLines();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(Settings.getInstance().getLinePattern());
        g2d.setColor(Color.LIGHT_GRAY);

        for (Line line : gridLines) {
            g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
    }

    public void hideGrid() {
        gridVisible = false;
        gridLines.clear();
        repaint();
    }

    public void showGrid() {
        gridVisible = true;
        gridLines = generateLines();
        repaint();
    }

    public void setStep(int step) {
        LINE_SPACING = step;
        repaint();
    }

    public void clearComponents() {
        for (Component c : getComponents()) {
            System.out.println(c);
            if (c instanceof DraggableComponent) {
                remove(c);
            }
        }
        revalidate();
        repaint();
    }

    private List<Line> generateLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 1; i <= LINES; i++) {
            lines.add(new Line(0, i + (LINE_SPACING * i), this.getSize().width, i + (LINE_SPACING * i)));
        }
        for (int i = 1; i <= LINES; i++) {
            lines.add(new Line(i + (LINE_SPACING * i), 0, i + (LINE_SPACING * i), this.getSize().height));
        }
        return lines;
    }

    public static class Line {
        int x1;
        int x2;
        int y1;
        int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public int getX2() {
            return x2;
        }

        public int getY1() {
            return y1;
        }

        public int getY2() {
            return y2;
        }

        public double getLength() {
            return Point.distance(x1, y1, x2, y2);
        }


        @Override
        public String toString() {
            return "Line{" +
                    "x1=" + x1 +
                    ", x2=" + x2 +
                    ", y1=" + y1 +
                    ", y2=" + y2 +
                    ", length=" + getLength() +
                    '}';
        }
    }

}
