package pl.financemanager.gui;

import javax.swing.*;
import java.awt.*;

public class LoginWindow{
    private JFrame frame;
    private JPanel panel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow(String title) {
        frame = new JFrame(title);
        //frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 220);
        frame.setLocationRelativeTo(null);

        loginLabel = new JLabel("Login:");
        passwordLabel = new JLabel("Password:");
        loginTextField = new JTextField(10);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(loginLabel);
        panel.add(loginTextField);


        frame.add(panel);
        frame.setVisible(true);

    }

    public void init(){
        loginButton.addActionListener(e -> login());
    }

    public void login(){
        // DBAdapter.login
    }



    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }

    public void setLoginLabel(JLabel loginLabel) {
        this.loginLabel = loginLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JTextField getLoginTextField() {
        return loginTextField;
    }

    public void setLoginTextField(JTextField loginTextField) {
        this.loginTextField = loginTextField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }
}
