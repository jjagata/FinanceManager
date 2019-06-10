package pl.financemanager.gui;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import pl.financemanager.db.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class AppService {
    private static AppService instance;
    private DBAdapter dba;

    private AppService(){
        this.dba = new DBAdapter();
    }

    public static AppService getInstance(){
        if (AppService.instance == null) {
            AppService.instance = new AppService();
        }
        return AppService.instance;
    }

    public User login(String log, char[] password) throws SQLException {
        return dba.login(log, String.valueOf(password));
    }

    public Object[][] getSpendings(int month, int year, int userId) throws SQLException {
        Collection<Spending> spendings = dba.getSpendings(userId, month, year);
        Object[][] result = new Object[spendings.size()][5];

        int j = 0;
        for (Spending spending : spendings) {
            result[j][0] = spending.getDay().get(Calendar.DAY_OF_MONTH);
            result[j][1] = Utils.getMonth(spending.getDay().get(Calendar.MONTH));
            result[j][2] = spending.getDay().get(Calendar.YEAR);
            result[j][3] = spending.getAmount();
            result[j][4] = spending.getCategory();
            j++;
        }

        return result;
    }

    public BigDecimal getSpendingsSum(int month, int year, int userId) throws SQLException {
        Collection<Spending> spendings = dba.getSpendings(userId, month, year);
        double sum = 0;
        for(Spending spending : spendings){
            if(spending.getAmount() != null){
                sum += spending.getAmount().doubleValue();
            }
        }
        return BigDecimal.valueOf(sum);
    }

    public Object[][] getSpendings(int userId) throws SQLException {
        Collection<Spending> spendings = dba.getSpendings(userId);

        Object[][] result = new Object[spendings.size()][3];

        int j = 0;
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


    public CategoryDataset createDataset(int month, int year, int userId) throws SQLException {
        final String DAY = "Day";
        Collection<Spending> spendings = dba.getSpendings(userId, month, year);
        YearMonth yearMonthObject = YearMonth.of(year, month + 1);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 1; i <= daysInMonth; i++) {
            Calendar day = new GregorianCalendar(year, month, i);
            BigDecimal cumulatedAmount = getCumulatedAmount(spendings, day);
            BigDecimal amount = getAmount(spendings, day);
            if(getAmount(spendings, day).doubleValue() == 0) {
                dataset.addValue(amount, DAY, day.get(Calendar.DAY_OF_MONTH));
            } else{
                dataset.addValue(cumulatedAmount, DAY, day.get(Calendar.DAY_OF_MONTH));
            }
        }

        return dataset;
    }

    public BigDecimal getAmount(Collection<Spending> spendings, Calendar day) {
        BigDecimal amount = BigDecimal.valueOf(0);

        for (Spending spending : spendings) {
            if (spending.getDay().get(Calendar.DAY_OF_MONTH) == day.get(Calendar.DAY_OF_MONTH)) {
                amount = amount.add(spending.getAmount());
            }
        }

        return amount;
    }

    public BigDecimal getCumulatedAmount(Collection<Spending> spendings, Calendar day) {
        BigDecimal amount = BigDecimal.valueOf(0);

        for (Spending spending : spendings) {
            if (spending.getDay().get(Calendar.DAY_OF_MONTH) <= day.get(Calendar.DAY_OF_MONTH)) {
                amount = amount.add(spending.getAmount());
            }
        }

        return amount;
    }

    public Object[][] getBudgets(int userId) throws SQLException {
        Collection<Budget> budgets = dba.getBudgets(userId);
        Object[][] result = new Object[budgets.size()][3];

        int j = 0;
        for (Budget budget : budgets) {
            result[j][0] = Utils.getMonth(budget.getMonth().get(Calendar.MONTH));
            result[j][1] = budget.getMonth().get(Calendar.YEAR);
            result[j][2] = budget.getBudget();
            j++;
        }

        return result;
    }

    public Budget getBudget(int userId, int month, int year) throws SQLException {
        return dba.getBudget(userId, month, year);
    }

    public void saveBudget(int userId, int month, int year, BigDecimal value) throws SQLException {
        Budget budget = new Budget(month, year, value, userId);
        Budget dbBudget = dba.getBudget(userId, month, year);
        if (dbBudget != null) {
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
