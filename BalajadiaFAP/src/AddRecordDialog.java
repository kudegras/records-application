// Edriech G. Balajadia
// 1CSB
// FAP

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class AddRecordDialog {

    // Constant for the inputted date format
    final static DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM-d-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    // Values of the Record List passed
    private Person person;
    private ArrayList<Person> persons;
    private JTextArea recordTA;
    private ButtonGroup orderBtnGrp;

    // Containers for Add Dialog
    private JDialog addRecD;
    private JFrame recListF;
    private JPanel nameAndBdayP, bdayDrpdwnP, addRecBtnP;

    // Components for Add Dialog
    private JLabel addRecNameL, bDayL;
    private JTextField addRecNameTF;
    private JComboBox<String> monthCB, dayCB, yearCB;
    private JButton saveBackB, saveAddB, addRecBackB;

    public AddRecordDialog(JFrame recListF, ArrayList<Person> persons, JTextArea recordTA, ButtonGroup orderBtnGrp) {
        // Values of the Record List passed
        this.recListF = recListF;
        this.persons = persons;
        this.recordTA = recordTA;
        this.orderBtnGrp = orderBtnGrp;

        // Containers for Add Dialog
        addRecD = new JDialog(recListF, "Add records", true);
        nameAndBdayP = new JPanel(new GridLayout(2, 2, 5, 5));
        bdayDrpdwnP = new JPanel(new GridLayout(1, 3, 5, 0));
        addRecBtnP = new JPanel();

        // Components for Add Dialog
        addRecNameL = new JLabel("Name: ", JLabel.RIGHT);
        bDayL = new JLabel("Birthday: ", JLabel.RIGHT);
        addRecNameTF = new JTextField();
        String[] dd = new String[31], yy = new String[123];
        String[] mm = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec" };
        int day = 01, year = 1900;
        for (int i = 0; i < 31; i++) {
            dd[i] = "" + day++;
        }
        for (int i = 0; i < 123; i++) {
            yy[i] = "" + year++;
        }
        monthCB = new JComboBox<String>(mm);
        dayCB = new JComboBox<String>(dd);
        yearCB = new JComboBox<String>(yy);
        saveBackB = new JButton("Save and Go Back");
        saveAddB = new JButton("Save and Add Another");
        addRecBackB = new JButton("Back");
    }

    public void init() {
        // Add Record Dialog
        bdayDrpdwnP.add(monthCB);
        bdayDrpdwnP.add(dayCB);
        bdayDrpdwnP.add(yearCB);

        nameAndBdayP.add(addRecNameL);
        nameAndBdayP.add(addRecNameTF);
        nameAndBdayP.add(bDayL);
        nameAndBdayP.add(bdayDrpdwnP);
        nameAndBdayP.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 175));

        addRecBtnP.add(saveBackB);
        addRecBtnP.add(Box.createHorizontalStrut(30));
        addRecBtnP.add(saveAddB);
        addRecBtnP.add(Box.createHorizontalStrut(30));
        addRecBtnP.add(addRecBackB);
        addRecBtnP.setBorder(BorderFactory.createEmptyBorder(0, 80, 20, 80));

        addRecD.setSize(700, 200);
        addRecD.setLocationRelativeTo(null);
        addRecD.setLayout(new BorderLayout());
        addRecD.add(nameAndBdayP, BorderLayout.CENTER);
        addRecD.add(addRecBtnP, BorderLayout.SOUTH);

        // Events for Add Record Dialog
        saveBackB.addActionListener(e -> {
            if (addPersonToRecord()) {
                addRecD.dispose();
            }
        });
        saveAddB.addActionListener(e -> {
            addPersonToRecord();
        });
        addRecBackB.addActionListener(e -> {
            addRecD.dispose();
        });

        addRecD.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addRecD.setVisible(true);
    }

    // Add persons to record and validates if added
    public boolean addPersonToRecord() {
        // Added an extra condition when users add an empty name
        if (addRecNameTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(recListF, "Please enter a Name", "Empty Name",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        String dateInput = monthCB.getSelectedItem() + "-" + dayCB.getSelectedItem()
                + "-" + yearCB.getSelectedItem();
        try {
            // Parse the inputted date to make a local date object
            LocalDate localDateInput = LocalDate.parse(dateInput, INPUT_DATE_FORMAT);
            if (localDateInput.isBefore(LocalDate.now())) {
                person = new Person(addRecNameTF.getText(), localDateInput);
                persons.add(person);
                recordTA.append("\t" + person.toString() + "\n");

                // Clear inputs
                addRecNameTF.setText("");
                monthCB.setSelectedIndex(0);
                dayCB.setSelectedIndex(0);
                yearCB.setSelectedIndex(0);
                // Resets the sort buttons since adding new Persons messes up the arrangement
                // and adds to the first available index
                orderBtnGrp.clearSelection();
                return true;
            } else {
                JOptionPane.showMessageDialog(recListF, "Given date is in the future!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(recListF, "Given date is invalid!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}
