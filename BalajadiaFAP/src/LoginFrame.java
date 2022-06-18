// Edriech G. Balajadia
// 1CSB
// FAP

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginFrame {

    // Login Frame Containers
    private JFrame loginF;
    private JPanel userPwP, loginBtnP;

    // Login Frame Components
    private JLabel userL, pwL;
    private JTextField userTF, pwTF;
    private JButton loginB;

    public LoginFrame() {
        loginF = new JFrame("Login");
        userPwP = new JPanel(new GridLayout(2, 2, 5, 5));
        loginBtnP = new JPanel();
        userL = new JLabel("Username: ", JLabel.RIGHT);
        pwL = new JLabel("Password: ", JLabel.RIGHT);
        userTF = new JTextField();
        pwTF = new JTextField();
        loginB = new JButton("Login");
    }

    public void init() {
        // Use Atomic Integers for thread safety
        AtomicInteger attempts = new AtomicInteger(0);
        AtomicInteger attemptsLeft = new AtomicInteger(3);
        HashMap<String, String> accounts = new HashMap<>();
        String str;

        // Reads the accounts
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("loginCredentials.txt")));
            while ((str = br.readLine()) != null) {
                accounts.put(str, br.readLine());
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error in reading in file!");
        }

        userPwP.add(userL);
        userPwP.add(userTF);
        userPwP.add(pwL);
        userPwP.add(pwTF);
        userPwP.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 70));

        loginBtnP.add(loginB);
        loginBtnP.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        loginF.setLayout(new BorderLayout());
        loginF.add(userPwP, BorderLayout.CENTER);
        loginF.add(loginBtnP, BorderLayout.SOUTH);
        loginF.setSize(460, 170);
        loginF.setLocationRelativeTo(null);
        loginF.setVisible(true);

        loginB.addActionListener(e -> {
            if (attempts.get() != 3) {
                if (pwTF.getText().equals(accounts.get(userTF.getText()))) {
                    // Disposes current frame
                    // Creates the Record List Frame and initializes it
                    loginF.dispose();
                    RecordListFrame rlf = new RecordListFrame();
                    rlf.init();

                    // Added an extra condition when users input an empty username / password
                } else if (userTF.getText().isEmpty() || pwTF.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(loginF, "Please enter a Username / Password",
                            "Empty Username / Password",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Increments attempts if the username / password is incorrect
                } else {
                    JOptionPane.showMessageDialog(loginF,
                            "Incorrect Username / Password\nAttempts left: " + attemptsLeft.decrementAndGet(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    attempts.getAndIncrement();

                    if (attempts.get() == 3) {
                        JOptionPane.showMessageDialog(loginF, "Sorry, you have reached the limit of tries, good bye!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        });

        loginF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
