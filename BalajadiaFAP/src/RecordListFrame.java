// Edriech G. Balajadia
// 1CSB
// FAP

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public class RecordListFrame {

    // Constant for date and time format
    final static DateTimeFormatter FILENAME_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss")
            .withResolverStyle(ResolverStyle.STRICT);

    private ArrayList<Person> persons;

    // Record List Frame Containers
    private JFrame recordListF;
    private JPanel sortP, orderP, sortAndOrderP, btnP, sortOrderAndBtnP;

    // Record List Frame Components
    private JTextArea recordTA;
    private JScrollPane recordSP;
    private String[] sortFilters;
    private JComboBox<String> sortCB;
    private JRadioButton ascRB, descRB;
    private ButtonGroup orderBtnGrp;
    private JButton addB, removeB, exportB;
    private JLabel sortL;

    public RecordListFrame() {
        persons = new ArrayList<>();

        // Record List Frame Containers
        recordListF = new JFrame("List of Records");
        sortP = new JPanel(new GridLayout(1, 2, 5, 5));
        orderP = new JPanel(new GridLayout(2, 1, 5, 5));
        sortAndOrderP = new JPanel(new GridLayout(1, 2));
        btnP = new JPanel();
        sortOrderAndBtnP = new JPanel(new GridLayout(2, 1));

        // Record List Frame Components
        recordTA = new JTextArea("\tNAME\tBIRTHDAY\tAGE\n");
        recordSP = new JScrollPane(recordTA);
        sortL = new JLabel("Sort by: ", JLabel.RIGHT);
        sortFilters = new String[] { "Name", "Birthday", "Age" };
        sortCB = new JComboBox<String>(sortFilters);
        ascRB = new JRadioButton("Ascending");
        descRB = new JRadioButton("Descending");
        orderBtnGrp = new ButtonGroup();
        addB = new JButton("Add a Record");
        removeB = new JButton("Remove a Record");
        exportB = new JButton("Export to CSV File");

    }

    public void init() {
        // Record List Panel
        recordTA.setLineWrap(true);
        recordTA.setWrapStyleWord(true);
        recordSP.setBorder(BorderFactory.createEmptyBorder(20, 65, 20, 65));
        recordSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Sort Panel
        sortP.add(sortL);
        sortP.add(sortCB);
        sortP.setBorder(BorderFactory.createEmptyBorder(0, 65, 50, 65));

        // Order Panel
        orderP.add(ascRB);
        orderP.add(descRB);
        orderBtnGrp.add(ascRB);
        orderBtnGrp.add(descRB);
        orderP.setBorder(BorderFactory.createEmptyBorder(0, 115, 30, 65));

        // Sort and Order Panel
        sortAndOrderP.add(sortP);
        sortAndOrderP.add(orderP);

        // Button Panel
        btnP.add(addB);
        btnP.add(Box.createHorizontalStrut(30));
        btnP.add(removeB);
        btnP.add(Box.createHorizontalStrut(30));
        btnP.add(exportB);
        btnP.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Sort, Order, and Button Panel
        sortOrderAndBtnP.add(sortAndOrderP);
        sortOrderAndBtnP.add(btnP);

        recordListF.setLayout(new BorderLayout(5, 5));
        recordListF.add(recordSP, BorderLayout.CENTER);
        recordListF.add(sortOrderAndBtnP, BorderLayout.SOUTH);
        recordListF.setSize(625, 420);
        recordListF.setLocationRelativeTo(null);
        recordListF.setVisible(true);

        // Events for Record List Frame
        addB.addActionListener(e -> {
            AddRecordDialog addRecD = new AddRecordDialog(recordListF, persons, recordTA, orderBtnGrp);
            addRecD.init();
        });
        removeB.addActionListener(e -> {
            RemoveRecordDialog remRecD = new RemoveRecordDialog(recordListF, persons, recordTA);
            remRecD.init();
        });
        exportB.addActionListener(e -> {
            LocalDateTime ldtNow = LocalDateTime.now();
            String frmtldt = FILENAME_DATE_TIME_FORMAT.format(ldtNow);
            // Creates a file name based on local date time now to save the record list in a
            // csv file
            String fileName = frmtldt.replaceAll("[/ :]", "").concat(".csv");

            try {
                PrintWriter recordPW = new PrintWriter(new FileWriter(fileName));
                for (int i = 0; i < persons.size(); i++) {
                    String person = persons.get(i).toString().replace("\t", ",");
                    recordPW.println(person);
                }
                recordPW.close();
            } catch (IOException ioe) {
                System.err.println("Error in writing");
            }
        });

        // Filter options for Record List Frame
        sortCB.addActionListener(e -> {
            String filter = sortCB.getItemAt(sortCB.getSelectedIndex());
            if (ascRB.isSelected()) {
                sortRecordAsc(filter);
            } else if (descRB.isSelected()) {
                sortRecordDesc(filter);
            }
        });
        ascRB.addActionListener(e -> {
            String filter = sortCB.getSelectedItem().toString();
            sortRecordAsc(filter);
        });
        descRB.addActionListener(e -> {
            String filter = sortCB.getSelectedItem().toString();
            sortRecordDesc(filter);
        });

        recordListF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Sorts the record based on the given filter to ascending
    public void sortRecordAsc(String filter) {
        if (filter.equals("Name")) {
            persons.sort(Comparator.comparing(p -> p.getName().toLowerCase()));
        } else if (filter.equals("Birthday")) {
            persons.sort(Comparator.comparing(p -> p.getBirthDay()));
        } else if (filter.equals("Age")) {
            persons.sort(Comparator.comparingInt(p -> p.getAge()));
        }
        printRecord(persons, recordTA);
    }

    // Sorts the record based on the given filter to descending
    public void sortRecordDesc(String filter) {
        if (filter.equals("Name")) {
            persons.sort(Comparator.comparing((Person p) -> p.getName().toLowerCase()).reversed());
        } else if (filter.equals("Birthday")) {
            persons.sort(Comparator.comparing((Person p) -> p.getBirthDay()).reversed());
        } else if (filter.equals("Age")) {
            persons.sort(Comparator.comparingInt((Person p) -> p.getAge()).reversed());
        }
        printRecord(persons, recordTA);
    }

    // method to clear the record list text area and prints a new sorted record
    public void printRecord(ArrayList<Person> persons, JTextArea recordTA) {
        recordTA.setText("");
        recordTA.append("\tNAME\tBIRTHDAY\tAGE\n");
        for (Person p : persons) {
            recordTA.append("\t" + p.toString() + "\n");
        }
    }

}
