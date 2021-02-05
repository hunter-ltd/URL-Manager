package com.hunterltd.URLManager.Dialogs;

import com.hunterltd.URLManager.Popups.RightClickPopUp;

import javax.swing.*;
import java.awt.event.*;

public class InfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    protected JTextField nameTextField;
    protected JTextField urlTextField;

    public InfoDialog(String title, String buttonText) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(title);

        buttonOK.setText(buttonText);
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        for (JTextField field :
                new JTextField[]{nameTextField, urlTextField}) {
            field.setComponentPopupMenu(new RightClickPopUp(field));
        }
    }

    protected void onOK() {
        dispose();
    }

    protected void onCancel() {
        dispose();
    }
}
