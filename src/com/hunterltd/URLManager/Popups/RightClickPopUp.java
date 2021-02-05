package com.hunterltd.URLManager.Popups;

import com.hunterltd.URLManager.MainManagerGUI;

import javax.swing.*;

public class RightClickPopUp extends JPopupMenu {
    public RightClickPopUp(JList<String> list) {
        JMenuItem add = new JMenuItem("Add"),
                edit =new JMenuItem("Edit"),
                remove = new JMenuItem("Remove");
        for (JMenuItem item :
                new JMenuItem[]{add, edit, remove}) {
            add(item);
            item.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "Add":
                        MainManagerGUI.addItem(list);
                        break;
                    case "Edit":
                        MainManagerGUI.editItem(list);
                        break;
                    case "Remove":
                        MainManagerGUI.removeItems(list);
                        break;
                }
            });
        }
    }

    public RightClickPopUp(JTextField component) {
        JMenuItem cut = new JMenuItem("Cut"),
                copy = new JMenuItem("Copy"),
                paste = new JMenuItem("Paste");
        for (JMenuItem item :
                new JMenuItem[]{cut, copy, paste}) {
            add(item);
            item.addActionListener(e -> {
                switch (e.getActionCommand()) {
                    case "Copy":
                        component.copy();
                        break;
                    case "Cut":
                        component.cut();
                        break;
                    case "Paste":
                        component.paste();
                        break;
                    default:
                        break;
                }
            });
        }
    }

}
