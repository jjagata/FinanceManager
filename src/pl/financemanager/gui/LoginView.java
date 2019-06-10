package pl.financemanager.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginView extends JPanel {
	private JFrame frame;
	private JLabel loginLabel;
	private JLabel passwordLabel;
	private JTextField loginTextField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton cancelButton;
	
	public LoginView(JFrame frame) {
		this.frame = frame;
		this.setLayout(new GridBagLayout());
		
		this.loginLabel = new JLabel("Login: ");
		this.passwordLabel = new JLabel("Password: ");
		this.loginTextField = new JTextField(20);
		this.passwordField = new JPasswordField(20);
		this.loginButton = new JButton("Login");
		this.cancelButton = new JButton("Cancel");
		
		GridBagConstraints cs = new GridBagConstraints();
		cs.insets = new Insets(2, 2, 2, 2);
		cs.fill = GridBagConstraints.HORIZONTAL;

		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		this.add(loginLabel, cs);

		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		this.add(loginTextField, cs);

		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		this.add(passwordLabel, cs);

		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		this.add(passwordField, cs);   

		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 3;
		JPanel bp = new JPanel();
        bp.add(this.loginButton);
        bp.add(this.cancelButton);
		this.add(bp, cs);
		
		LoginController loginController = new LoginController(frame, this);
		this.loginButton.addActionListener(loginController);
		this.cancelButton.addActionListener(loginController);
		
		frame.setSize(400,200);
		this.frame.setResizable(false);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		
		frame.setVisible(true);
		frame.add(this);
	}

	public JTextField getLoginTextField() {
		return loginTextField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}
}
