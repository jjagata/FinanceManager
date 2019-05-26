package pl.financemanager;

import pl.financemanager.db.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class Application {
    public static void main(String[] args) throws SQLException {
        DBAdapter dbAdapter = new DBAdapter();

        Collection<Category> test = dbAdapter.getCategories();
        for(Category c :test){
            System.out.println(c);
        }

        Budget budget = new Budget();
        budget.setBudget(BigDecimal.valueOf(5000));
        budget.setBudgetId(1);

        Calendar calendar =new GregorianCalendar(2020,7,1);
        budget.setMonth(calendar);


        User user = new User();
        user.setId(Integer.valueOf(1));
        budget.setUser(user);

        dbAdapter.saveBudget(budget);

        Spending sp = new Spending ();
        sp.setAmount(BigDecimal.valueOf(300));
        sp.setDay(calendar);
        sp.setCategory(test.iterator().next());
        sp.setUser(user);
        sp.setSpendingId(1);
        dbAdapter.saveSpending(sp);
    }


}
