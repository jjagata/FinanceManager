package pl.financemanager.gui;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import pl.financemanager.db.Budget;
import pl.financemanager.db.DBAdapter;
import pl.financemanager.db.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;

public class AppLogic {
	DBAdapter dba = new DBAdapter();

	public User login(String log, char[] password)throws SQLException {
		return dba.login(log, String.valueOf(password));
	}

	public Object[][] getSpendings(int month, int year, int userId) {
		// call DB
		Object[][] spendings = null;

		Object[][] data = { { "1", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "2", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "3", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "4", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "5", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "6", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "7", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "8", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "9", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "10", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "11", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "12", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "13", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "14", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "15", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "16", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "17", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "18", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "19", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "20", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "21", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "22", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "23", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "24", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "25", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "26", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "27", Utils.getMonth(month), year, 100, 1500, "Food" },
				{ "28", Utils.getMonth(month), year, 100, 1400, "Food" },
				{ "29", Utils.getMonth(month), year, 100, 1300, "Food" },
				{ "30", Utils.getMonth(month), year, 100, 1300, "Food" },
				{ "31", Utils.getMonth(month), year, 100, 1300, "Food" } };
		return data;

		// return spendings;
	}

	public CategoryDataset createDataset(int month, int year, int userId) {
		final String DAY = "Day";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(Math.random() * 1000, DAY, "01.01.2019");
		dataset.addValue(Math.random() * 1000, DAY, "02.01.2019");
		dataset.addValue(Math.random() * 1000, DAY, "03.01.2019");
		dataset.addValue(Math.random() * 1000, DAY, "04.01.2019");

		return dataset;
	}

	public Object[][] getBudgets(int userId) throws SQLException {
		// call DB
		Collection<Budget> budgets = dba.getBudgets(userId);

		Object[][] result = new Object[budgets.size()][3];

		int j = 0;
		// iterate the set:
		for (Budget budget: budgets) {
			result[j][0] = Utils.getMonth(budget.getMonth().get(Calendar.MONTH));
			result[j][1] = budget.getMonth().get(Calendar.YEAR);
			result[j][2] = budget.getBudget();
			j++;
		}

		return result;
	}

	public void saveBudget(int userId, int month, int year, BigDecimal budget) throws SQLException {

		//dba.saveBudget();
	}

	public String[] getCategories() {
		return new String[] { "Food", "Clothes", "Fuel" };
	}

	public Object[][] getSpendings(int userId) {
		// call DB
		Object[][] spendings = null;

		Object[][] data = { { "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 }, { "01.01.2017", Math.random() * 100 },
				{ "01.01.2017", Math.random() * 100 } };
		return data;

		// return budgets;
	}
}
