package com.sergisa;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DraggableComponent extends JLabel {
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    private final int padding = 10;
    private Movement moveType;
    private final ImageIcon image;
    private final String description;
    private boolean locked = false;
    GridOverlay parent;
    Border border = BorderFactory.createLineBorder(Color.BLUE, 1, true);

    public DraggableComponent(ImageIcon image) {
        super(image);
        this.description = image.getDescription();
        this.image = image;
        parent = (GridOverlay) getParent();
        setLayout(null);
        setIcon(image);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(true);
        setBounds(20, 20, image.getIconWidth() + 20, image.getIconHeight() + 20);
        DraggableEventListener dragListener = new DraggableEventListener(Settings.getInstance().getGridStep());
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
        addKeyListener(new CustomKeyAdapter());
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setVerticalAlignment(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(padding, padding, padding, padding),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        repaint();
    }

    public void setActive() {
        if (locked) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 1, true),
                    BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLUE, 1, true),
                    BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));

        }
        requestFocus();
        repaint();
        this.getParent().repaint();
    }

    public void setInActive() {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(padding, padding, padding, padding),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));
        this.getParent().repaint();
        repaint();
        transferFocusBackward();
    }

    public void toggleLock() {
        locked = !locked;
        if (locked) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.RED, 1, true),
                    BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.BLUE, 1, true),
                    BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));
        }
        repaint();
        this.getParent().repaint();
    }

    public void rotate(int angle) {
        Icon icon = getIcon();
        ImageIcon imageIcon = (ImageIcon) icon;
        Image image = imageIcon.getImage();
        BufferedImage bi = ImageUtils.convertToBufferedImage(image);
        bi = ImageUtils.rotateImageBetter(bi, angle);
        setIcon(new ImageIcon(bi));
        repaint();
        this.getParent().repaint();
    }

    public Movement getMoveType() {
        return moveType;
    }

    public void setMoveType(Movement moveType) {
        this.moveType = moveType;
    }
    public void moveDown(int steps){
        setLocation(getLocation().x, getLocation().y + steps);
    }
    public void moveUp(int steps) {
        setLocation(getLocation().x, getLocation().y - steps);
    }
    public void moveRight(int steps) {
        setLocation(getLocation().x + steps, getLocation().y);
    }
    public void moveLeft(int steps) {
        setLocation(getLocation().x - steps, getLocation().y);
    }

    public void delete() {
        System.out.println(getParent());
        GridOverlay parent = (GridOverlay) getParent();
        parent.remove(DraggableComponent.this);
        parent.revalidate();
        parent.repaint();
    }

    @Override
    public String toString() {
        return "DraggableComponent{" +
                "screenX=" + screenX +
                ", screenY=" + screenY +
                ", myX=" + myX +
                ", myY=" + myY +
                ", moveType=" + moveType +
                ", image=" + description +
                ", locked=" + locked +
                '}';
    }

    enum Movement {
        HORIZONTAL,
        VERTICAL,
        BOTH,
        NONE
    }

    public class CustomKeyAdapter extends KeyAdapter {
        public CustomKeyAdapter() {
            super();
        }

        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_R && e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
                rotate(90);
            }
            if (e.getKeyCode() == KeyEvent.VK_L && e.getModifiersEx() == KeyEvent.ALT_DOWN_MASK) {
                toggleLock();
            }
            if (e.getKeyCode() == KeyEvent.VK_L && e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
                rotate(-90);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft(Settings.getInstance().getGridStep());
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                moveUp(Settings.getInstance().getGridStep());
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moveDown(Settings.getInstance().getGridStep());
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                moveRight(Settings.getInstance().getGridStep());
            }
            if(e.getKeyCode() == KeyEvent.VK_DELETE){
                delete();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
        }
    }

    public class DraggableEventListener implements MouseListener, MouseMotionListener {
        boolean snap = false;
        private int moveStep = 1;

        public DraggableEventListener(int step) {
            this.moveStep = step;
        }

        public DraggableEventListener() {
            this(1);
        }

        public void mousePressed(MouseEvent e) {
            screenX = e.getXOnScreen();
            screenY = e.getYOnScreen();
            myX = getX();
            myY = getY();
        }

        public void mouseDragged(MouseEvent e) {
            int deltaX =0;
            int deltaY = 0;
            if (moveType == Movement.HORIZONTAL) {
                deltaX = e.getXOnScreen() - screenX;
            } else if (moveType == Movement.VERTICAL) {
                deltaY = e.getYOnScreen() - screenY;
            } else if (moveType == Movement.BOTH) {
                deltaX = e.getXOnScreen() - screenX;
                deltaY = e.getYOnScreen() - screenY;
            }

            if(!Settings.getInstance().isSnapToGrid()){
                moveStep = 1;
            }else {
                moveStep = Settings.getInstance().getGridStep();
            }
            int newX = (myX + (moveStep * (deltaX / moveStep)));
            int newY = (myY + (moveStep * (deltaY / moveStep)));

            if (!locked || (e.getButton() == MouseEvent.BUTTON1)) {
                setBounds(newX, newY, getWidth(), getHeight());
                setLocation(newX, newY);
                setLayout(null);
                //repaint();
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            getParent().repaint();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }
}
