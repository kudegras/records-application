// Edriech G. Balajadia
// 1CSB
// FAP

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class RemoveRecordDialog {

    // Values of the Record List passed
    private ArrayList<Person> persons;
    private JTextArea recordTA;

    // Remove Record Dialog Containers
    private JDialog remRecD;
    private JFrame recListF;
    private JPanel nameP, remRecBtnP;

    // Remove Record Dialog compnents
    private JLabel remRecNameL;
    private JTextField remRecNameTF;
    private JButton remBackB, saveRemB, remRecBackB;

    public RemoveRecordDialog(JFrame recListF, ArrayList<Person> persons, JTextArea recordTA) {
        // Values of the Record List passed
        this.recListF = recListF;
        this.persons = persons;
        this.recordTA = recordTA;

        // Containers for Add and Remove Dialogs
        remRecD = new JDialog(recListF, "Remove Records", true);
        nameP = new JPanel(new GridLayout(1, 2, 5, 0));
        remRecBtnP = new JPanel();

        // Components for Add and Remove Dialog
        remRecNameL = new JLabel("Name: ", JLabel.RIGHT);
        remRecNameTF = new JTextField();
        remBackB = new JButton("Remove and Go Back");
        saveRemB = new JButton("Save and Remove Another");
        remRecBackB = new JButton("Back");
    }

    public void init() {
        // Remove Record Dialog
        nameP.add(remRecNameL);
        nameP.add(remRecNameTF);
        nameP.setBorder(BorderFactory.createEmptyBorder(30, 0, 25, 175));

        remRecBtnP.add(remBackB);
        remRecBtnP.add(Box.createHorizontalStrut(30));
        remRecBtnP.add(saveRemB);
        remRecBtnP.add(Box.createHorizontalStrut(30));
        remRecBtnP.add(remRecBackB);
        remRecBtnP.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        remRecD.setSize(700, 180);
        remRecD.setLocationRelativeTo(null);
        remRecD.setLayout(new BorderLayout());
        remRecD.add(nameP, BorderLayout.CENTER);
        remRecD.add(remRecBtnP, BorderLayout.SOUTH);

        // Events for Remove Record Dialog
        remBackB.addActionListener(e -> {
            if (removePersonFromRecord()) {
                remRecD.dispose();
            }
        });
        saveRemB.addActionListener(e -> {
            removePersonFromRecord();
        });
        remRecBackB.addActionListener(e -> {
            remRecD.dispose();
        });

        remRecD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        remRecD.setVisible(true);
    }

    // Removes a person from the record and validates if removed
    public boolean removePersonFromRecord() {
        // Added an extra condition when users add an empty name
        if (remRecNameTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(recListF, "Please enter a Name", "Empty Name",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Iterator<Person> personsItr = persons.iterator();
            while (personsItr.hasNext()) {
                Person person = personsItr.next();
                if (person.getName().equals(remRecNameTF.getText())) {
                    personsItr.remove();

                    // Clear inputs and prints new list with the person remove
                    remRecNameTF.setText("");
                    recordTA.setText("");
                    recordTA.append("\tNAME\tBIRTHDAY\tAGE\n");
                    for (Person p : persons) {
                        recordTA.append("\t" + p.toString() + "\n");
                    }
                    return true;
                }
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(recListF, "An IllegalArgumentException Caught: Name Not Found!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
