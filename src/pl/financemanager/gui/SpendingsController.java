package pl.financemanager.gui;

import pl.financemanager.db.Category;
import pl.financemanager.db.Spending;
import pl.financemanager.db.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;

class SpendingsController implements ActionListener {
    private SpendingsView view;
    private AppLogic logic;

    public SpendingsController(SpendingsView view) {
        this.view = view;
        this.logic = AppLogic.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnLabel = e.getActionCommand();
        if (btnLabel.equals("saveSpending")) {
            String dayAsString = view.getDatePicker().getJFormattedTextField().getText();
            Calendar day = Utils.getCalendar(dayAsString);

            BigDecimal amount = null;
            try {
                amount = BigDecimal.valueOf(Double.valueOf(view.getAmountField().getText()));
            } catch (NumberFormatException ex) {
                AppContext.showError("Invalid amount value!");
                view.getAmountField().setText("");
                return;
            }

            Category cat = (Category) view.getCategories().getSelectedItem();
            int userId = AppContext.getUser().getId();
            try {
                logic.saveSpending(userId, day, cat, amount);
            } catch (SQLException ex) {
                AppContext.showError();
            }
        }

        view.update();
    }

}
