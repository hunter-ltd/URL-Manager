package com.hunterltd.URLManager.Dialogs;

import com.hunterltd.URLManager.Utilities.UserData;

import javax.swing.*;

public class EditDialog extends InfoDialog {
    private final int editIndex;
    private final String oldName;
    private final String oldURLString;

    public EditDialog(int index) {
        super("Edit " + UserData.getNames()[index], "Save");

        editIndex = index;
        oldName = UserData.getNames()[index];
        oldURLString = UserData.getURLs()[index].toString();
        nameTextField.setText(oldName);
        urlTextField.setText(oldURLString);
    }

    @Override
    protected void onOK() {
        String newName = nameTextField.getText(),
                newURLString = urlTextField.getText();
        if (UserData.addNewItem(newName, newURLString)) {
            JOptionPane.showMessageDialog(this, "Invalid URL");
        } else {
            if (UserData.getNames()[editIndex].equals(oldName) &&
                    UserData.getURLs()[editIndex].toString().equals(oldURLString))
            {
                UserData.removeItem(new int[] { editIndex });
            } else {
                UserData.removeItem(new int[] { editIndex + 1 });
            }
        }
        dispose();
    }
}
