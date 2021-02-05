package com.hunterltd.URLManager;

import com.hunterltd.URLManager.Dialogs.AddDialog;
import com.hunterltd.URLManager.Dialogs.EditDialog;
import com.hunterltd.URLManager.Popups.RightClickPopUp;
import com.hunterltd.URLManager.Utilities.UserData;
import com.hunterltd.URLManager.Utilities.Webpage;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainManagerGUI extends JFrame {
    private JPanel rootPanel;
    private JList<String> urlList;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    public static void openDialog(JDialog dialog) {
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void addItem(JList<String> list) {
        openDialog(new AddDialog());
        list.setListData(UserData.getNames());
    }

    public static void editItem(JList<String> list) {
        int i = list.getSelectedIndex();
        if (i >= 0) {
            openDialog(new EditDialog(i));
            list.setListData(UserData.getNames());
        }
    }

    public static void removeItems(JList<String> list) {
        UserData.removeItem(list.getSelectedIndices());
        list.setListData(UserData.getNames());
    }

    public MainManagerGUI() {
        add(rootPanel);
        setTitle("URL Manager");

        urlList.setListData(UserData.getNames());

        rootPanel.registerKeyboardAction(e -> removeItems(urlList),
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        rootPanel.registerKeyboardAction(e -> {
                    int[] indices = urlList.getSelectedIndices();
                    for (int i :
                            indices) {
                        Webpage.open(UserData.getURLs()[i]);
                    }
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );

        urlList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 || e.getClickCount() == 3) {
                    Webpage.open(UserData.getURLs()[urlList.locationToIndex(e.getPoint())]);
                }
            }
        });
        urlList.setComponentPopupMenu(new RightClickPopUp(urlList));

        addButton.addActionListener(e -> addItem(urlList));
        editButton.addActionListener(e -> editItem(urlList));
        removeButton.addActionListener(e -> removeItems(urlList));
    }

}
