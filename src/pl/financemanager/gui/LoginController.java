package pl.financemanager.gui;

import pl.financemanager.db.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class LoginController implements ActionListener {
    private JFrame frame;
    private AppLogic logic;
    private LoginView view;

    public LoginController(JFrame frame, LoginView view) {
        this.frame = frame;
        this.view = view;
        this.logic = AppLogic.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnLabel = e.getActionCommand();
        if (btnLabel.equals("Login")) {
            User user = null;
            try {
                user = logic.login(view.getLoginTextField().getText(), view.getPasswordField().getPassword());
            } catch (SQLException ex) {
                AppContext.showError();
            }

            if (user == null) {
                AppContext.showError("Invalid username or passowrd!");
            } else {
                AppContext.createInstance(user);
                frame.getContentPane().removeAll();
                frame.repaint();

                new MainAppView(this.frame);
            }

        } else if (btnLabel.equals("Cancel")) {
            System.exit(0);
        }

    }

}
