package pl.financemanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BudgetsController implements ActionListener {
	private BudgetsView view;
	private AppLogic logic;

	public BudgetsController(BudgetsView view) {
		this.view = view;
		this.logic = new AppLogic();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();
		if (btnLabel.equals("saveBudget")) {
			int month = view.getMonths().getSelectedIndex();
			int year = (Integer) view.getYears().getSelectedItem();
			BigDecimal budget = null;
			try {
				budget = BigDecimal.valueOf(Double.valueOf(view.getBudgetField().getText()));
			} catch (NumberFormatException ex) {
				AppContext.getInstance().showError("Invalid budget value!");
				view.getBudgetField().setText("");
				return;
			}

			int userId = AppContext.getInstance().getUser().getId();
			try {
				logic.saveBudget(userId, month, year, budget);
			} catch (SQLException ex) {
				AppContext.getInstance().showError();
			}
		}

		view.update();
	}

}
