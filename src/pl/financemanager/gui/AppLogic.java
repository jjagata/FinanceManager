package pl.financemanager.gui;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import pl.financemanager.db.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

public class AppLogic {
    DBAdapter dba = new DBAdapter();
    Collection<Category> categories;

    public User login(String log, char[] password) throws SQLException {
        return dba.login(log, String.valueOf(password));
    }

    public Object[][] getSpendings(int month, int year, int userId) throws SQLException {
        // call DB
        Collection<Spending> spendings = dba.getSpendings(userId, month, year);

        Object[][] result = new Object[spendings.size()][6];

        int j = 0;
        for (Spending spending : spendings) {
            result[j][0] = spending.getDay().get(Calendar.DAY_OF_MONTH);
            result[j][1] = Utils.getMonth(spending.getDay().get(Calendar.MONTH));
            result[j][2] = spending.getDay().get(Calendar.YEAR);
            result[j][3] = spending.getAmount();
            result[j][4] = "";
            result[j][5] = spending.getCategory();
            j++;
        }

        return result;
    }

    public Object[][] getSpendings(int userId) throws SQLException {
        /// call DB
        Collection<Spending> spendings = dba.getSpendings(userId);

        Object[][] result = new Object[spendings.size()][3];

        int j = 0;
        // iterate the set:
        for (Spending spending : spendings) {
            result[j][0] = Utils.getFormattedCalendar(spending.getDay());
            result[j][1] = spending.getAmount();
            result[j][2] = spending.getCategory();
            j++;
        }

        return result;
    }

    public void saveSpending(Integer userId, Calendar day, Category cat, BigDecimal amount) throws SQLException {
        User user = new User(userId);
        Spending spending = new Spending(null, amount, day, cat, user);
        dba.saveSpending(spending);
    }


    public CategoryDataset createDataset(int month, int year, int userId) throws SQLException{
        final String DAY = "Day";

        Collection<Spending> spendings = dba.getSpendings(userId, month, year);

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
        for (Budget budget : budgets) {
            result[j][0] = Utils.getMonth(budget.getMonth().get(Calendar.MONTH));
            result[j][1] = budget.getMonth().get(Calendar.YEAR);
            result[j][2] = budget.getBudget();
            j++;
        }

        return result;
    }

    public void saveBudget(int userId, int month, int year, BigDecimal value) throws SQLException {
        Budget budget = new Budget(month, year, value, userId);
        Budget dbBudget = dba.getBudget(userId, month, year);
        if(dbBudget != null) {
            budget.setBudgetId(dbBudget.getBudgetId());
        }
        dba.saveBudget(budget);
    }

    public CategoryModel getCategories() throws SQLException {
        Collection<Category> categories = dba.getCategories();
        Category[] categoryArray = new Category[categories.size()];

        int j = 0;
        for (Category cat : categories) {
            categoryArray[j] = cat;
            j++;
        }

        return new CategoryModel(categoryArray);
    }


}
