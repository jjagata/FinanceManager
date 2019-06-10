package pl.financemanager.gui;

import pl.financemanager.db.Category;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;

class SpendingsController implements ActionListener {
    private SpendingsView view;
    private AppService logic;

    public SpendingsController(SpendingsView view) {
        this.view = view;
        this.logic = AppService.getInstance();
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
