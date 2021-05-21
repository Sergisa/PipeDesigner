package com.sergisa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class DraggingForm extends JFrame {
    private JPanel root;
    private JButton addElement;
    private JPanel drawArea;
    private JList<String> notesList;
    private JLabel activeComponentDescription;
    private JCheckBox showGridCheckBox;
    private JButton clearButton;
    private CustomButton settings;
    private JCheckBox gridSnap;
    private CustomButton angleCreate;
    private CustomButton controlTerminalCreate;
    private final List<Element> availableElements;
    private DraggableComponent activeComponent;
    private final List<DraggableComponent> componentsList;
    private final DefaultListModel<String> elementListViewModel;
    private final NewElementSelectionDialog selectionDialog;

    public DraggingForm() {
        ActionListener creator = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getSource() == controlTerminalCreate){
                    System.out.println("Creating control terminal");
                }
            }
        };
        availableElements = new ArrayList<>();
        componentsList = new ArrayList<>();
        elementListViewModel = new DefaultListModel<>();
        $$$setupUI$$$();
        setVisible(true);
        setContentPane(root);
        setLocationRelativeTo(null);
        setSize(1543, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        notesList.setModel(elementListViewModel);
        selectionDialog = new NewElementSelectionDialog();
        selectionDialog.setLocationRelativeTo(this);
        clearButton.setIcon(new ImageIcon(new ImageIcon("src/main/resources/delete.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        angleCreate.addActionListener(creator);
        controlTerminalCreate.addActionListener(creator);
        clearButton.addActionListener(e -> {
            ((GridOverlay) drawArea).clearComponents();
        });
        gridSnap.addActionListener(e -> {
            if (((JCheckBox) e.getSource()).isSelected()) {
                Settings.getInstance().setSnapToGrid(true);
            } else {
                Settings.getInstance().setSnapToGrid(false);
            }
        });
        showGridCheckBox.addActionListener(e -> {
            if (((JCheckBox) e.getSource()).isSelected()) {
                System.out.println("Show");
                ((GridOverlay) drawArea).showGrid();
            } else {
                System.out.println("Hide");
                ((GridOverlay) drawArea).hideGrid();
            }
        });
        selectionDialog.setListener(selectedElement -> {
            DraggableComponent newDraggableObject = new DraggableComponent(selectedElement.getIcon(), JLabel.LEFT);
            newDraggableObject.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        itemSelected((DraggableComponent) e.getSource());
                    }
                }
            });
            /*TimerTask timerTask = new MyTimerTask(newDraggableObject, elementListViewModel);
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 0, 100);
            */

            newDraggableObject.setMoveType(DraggableComponent.Movement.BOTH);
            addElement(newDraggableObject);
            elementListViewModel.addElement(newDraggableObject.toString());
        });
        addElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionDialog.pack();
                selectionDialog.setVisible(true);
            }
        });
        settings.addActionListener(e -> {
            SettingsWindow settings = new SettingsWindow("Settings");
            settings.setLocationRelativeTo(this);
            settings.setVisible(true);
        });

        /*TimerTask timerTask = new MyTimerTask();
        // стартуем TimerTask в виде демона

        Timer timer = new Timer(true);
        // будем запускать каждых 10 секунд (10 * 1000 миллисекунд)
        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);*/
    }

    public void addElement(DraggableComponent component) {
        componentsList.add(component);
        drawArea.add(component);
        drawArea.repaint();
    }

    private void itemSelected(DraggableComponent component) {
        if (activeComponent != null) {
            activeComponent.setInActive();
            if (activeComponent.equals(component)) {
                activeComponent = null;
            } else {
                component.setActive();
                activeComponent = component;
            }
        } else {
            component.setActive();
            activeComponent = component;
        }
        //setDescription(activeComponent);
        component.repaint();
    }

    private void setDescription(DraggableComponent element) {
        if (element == null) {
            //activeComponentDescription.setText("Empty");
            //activeComponentDescription.setForeground(Color.GRAY);
        } else {
            //activeComponentDescription.setForeground(new Color(51, 51, 51));
            //activeComponentDescription.setText(element.toString());
        }
    }

    public void toolSelected(ActionEvent e){
        String path = "";
        if (e.getSource() == angleCreate){
            path = "src/main/resources/components/ext_int_angle.png";
        }
        else if (e.getSource() == controlTerminalCreate){
            path = "src/main/resources/components/terminal.png";
        }

        DraggableComponent component = new DraggableComponent(new ImageIcon(path), 2);
        componentsList.add(component);
        drawArea.add(component);
        drawArea.repaint();
    }

    public void setGridStep(int step) {
        ((GridOverlay) drawArea).setStep(step);
    }

    private void createUIComponents() {
        drawArea = new GridOverlay();
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        root = new JPanel();
        root.setLayout(new GridBagLayout());
        addElement = new JButton();
        addElement.setText("addElement");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        root.add(addElement, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        root.add(scrollPane1, gbc);
        notesList = new JList();
        notesList.setValueIsAdjusting(true);
        notesList.putClientProperty("List.isFileList", Boolean.FALSE);
        scrollPane1.setViewportView(notesList);
        final JLabel label1 = new JLabel();
        label1.setText("ActiveElement");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        root.add(label1, gbc);
        activeComponentDescription = new JLabel();
        activeComponentDescription.setText("Label");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        root.add(activeComponentDescription, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        root.add(drawArea, gbc);
        showGridCheckBox = new JCheckBox();
        showGridCheckBox.setSelected(true);
        showGridCheckBox.setText("Show grid");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        root.add(showGridCheckBox, gbc);
        clearButton = new JButton();
        clearButton.setHorizontalTextPosition(0);
        clearButton.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        root.add(clearButton, gbc);
        settings.setHorizontalTextPosition(0);
        settings.setIcon(new ImageIcon(getClass().getResource("/settings.png")));
        settings.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        root.add(settings, gbc);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setMargin(new Insets(10, 10, 10, 10));
        toolBar1.setOrientation(1);
        toolBar1.setRollover(false);
        toolBar1.putClientProperty("JToolBar.isRollover", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        root.add(toolBar1, gbc);
        gridSnap = new JCheckBox();
        gridSnap.setSelected(true);
        gridSnap.setText("Привязвть к сетке");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        root.add(gridSnap, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

    private class MyTimerTask extends TimerTask {
        DraggableComponent component;
        DefaultListModel<String> elementListViewModel;

        public MyTimerTask(DraggableComponent newDraggableObject, DefaultListModel<String> elementListViewModel) {
            this.elementListViewModel = elementListViewModel;
            component = newDraggableObject;
        }

        @Override
        public void run() {
            activeComponentDescription.setText(component.toString());
            //elementListViewModel.addElement(component.toString());
        }

    }
}
