package com.hunterltd.URLManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String opSys = System.getProperty("os.name");
        SwingUtilities.invokeLater(() -> {
            MainManagerGUI mainGUI;
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println(UIManager.getSystemLookAndFeelClassName());
                mainGUI = new MainManagerGUI();
                if (!opSys.equalsIgnoreCase("mac os x")) {
                    // Mac apps don't usually exit when their windows are all closed. They exit when the user Quits them
                    mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                mainGUI.pack();
                mainGUI.setVisible(true);
            } catch (ClassNotFoundException | IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }
}
