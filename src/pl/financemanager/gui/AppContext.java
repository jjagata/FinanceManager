package pl.financemanager.gui;

import pl.financemanager.db.User;

import javax.swing.*;

public class AppContext {
    private static User user;
    private static AppContext instance;

    private AppContext() {
    }

    private AppContext(User user) {
        this.user = user;
    }

    public static AppContext getInstance() {
        if (AppContext.instance == null) {
            AppContext.instance = new AppContext();
        }
        return AppContext.instance;
    }

    public static AppContext createInstance(User user) {
        if (AppContext.instance == null) {
            AppContext.instance = new AppContext(user);
        }
        return AppContext.instance;
    }

    public static User getUser() {
        return user;
    }

    public static void showError(){
        JOptionPane.showMessageDialog(null,
                "An error occurred!",
                "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showError(String message){
        JOptionPane.showMessageDialog(null,
                message,
                "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }
}
