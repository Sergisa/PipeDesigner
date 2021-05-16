package com.sergisa;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info);
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }


        Application.getInstance().start();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DraggingForm mainForm = new DraggingForm();
            }
        });
    }
}


