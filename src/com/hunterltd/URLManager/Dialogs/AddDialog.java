package com.hunterltd.URLManager.Dialogs;

import com.hunterltd.URLManager.Utilities.UserData;

import javax.swing.*;

public class AddDialog extends InfoDialog {
    public AddDialog() {
        super("Add new URL", "Add");
    }

    @Override
    protected void onOK() {
        String name = this.nameTextField.getText(),
                urlString = this.urlTextField.getText();
        if (UserData.addNewItem(name, urlString)) {
            JOptionPane.showMessageDialog(this, "Invalid URL");
        }
        dispose();
    }
}
