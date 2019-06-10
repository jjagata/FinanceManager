package pl.financemanager.db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class DBAdapter {
    private Properties props;
    private Connection conn;

    public DBAdapter() {
        connectToDb();
    }

    public Collection<Category> getCategories() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT category_id, category_name FROM categories ORDER BY category_name";
        ResultSet rs = statement.executeQuery(sql);
        Collection<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category cat = new Category(rs.getInt("category_id"), rs.getString("category_name"));
            categories.add(cat);
        }
        return categories;
    }

    public Collection<Budget> getBudgets(int userId) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT user_id,amount, month, year, budget_id FROM budgets WHERE user_id =" + userId + " ORDER BY year, month";
        ResultSet rs = statement.executeQuery(sql);
        Collection<Budget> budgets = new ArrayList<>();
        while (rs.next()) {
            Budget bu = new Budget();
            bu.setBudgetId(rs.getInt("budget_id"));
            bu.setBudget(rs.getBigDecimal("amount"));

            User user = new User();
            user.setId(rs.getInt("user_id"));
            bu.setUser(user);

            int month = rs.getInt("month");
            int year = rs.getInt("year");

            Calendar calendar = new GregorianCalendar(year, month, 1);
            bu.setMonth(calendar);
            budgets.add(bu);
        }
        return budgets;
    }

    public Budget getBudget(int userId, int month, int year) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT user_id,amount, month, year, budget_id FROM budgets WHERE user_id =" + userId + " AND month = " + month + " AND year = " + year;
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next() == false) {
            return null;
        }

        Budget bu = new Budget();
        bu.setBudgetId(rs.getInt("budget_id"));
        bu.setBudget(rs.getBigDecimal("amount"));

        User user = new User();
        user.setId(rs.getInt("user_id"));
        bu.setUser(user);

        Calendar calendar = new GregorianCalendar(year, month, 1);
        bu.setMonth(calendar);

        return bu;
    }

    public void saveBudget(Budget bu) throws SQLException {
        Statement statement = conn.createStatement();
        String sql;
        if (bu.getBudgetId() == null) {
            sql = "INSERT INTO budgets(user_id,amount, month,year) VALUES (" + bu.getUser().getId() + "," + bu.getBudget() +
                    "," + bu.getMonth().get(Calendar.MONTH) + "," + bu.getMonth().get(Calendar.YEAR) + ")";
        } else {
            sql = "UPDATE budgets SET amount=" + bu.getBudget() + ", month=" + bu.getMonth().get(Calendar.MONTH) +
                    ",year=" + bu.getMonth().get(Calendar.YEAR) + " WHERE budget_id =" + bu.getBudgetId();

        }

        statement.execute(sql);
    }

    public void saveSpending(Spending sp) throws SQLException {
        Statement statement = conn.createStatement();
        String sql;
        if (sp.getSpendingId() == null) {
            sql = "INSERT INTO expenses(user_id,amount,day, month,year,category_id) VALUES ("
                    + sp.getUser().getId() + ","
                    + sp.getAmount() + ","
                    + sp.getDay().get(Calendar.DAY_OF_MONTH) + ","
                    + sp.getDay().get(Calendar.MONTH) + ","
                    + sp.getDay().get(Calendar.YEAR) + ","
                    + sp.getCategory().getCategoryId() +
                    ")";
        } else {
            sql = "UPDATE expenses SET amount="
                    + sp.getAmount() + ", day="
                    + sp.getDay().get(Calendar.DAY_OF_MONTH) + ", month="
                    + sp.getDay().get(Calendar.MONTH) + ",year="
                    + sp.getDay().get(Calendar.YEAR) + ", category_id="
                    + sp.getCategory().getCategoryId() + " WHERE expenses_id ="
                    + sp.getSpendingId();

        }

        statement.execute(sql);
    }

    public Collection<Spending> getSpendings(int userId, int month, int year) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT expenses_id,user_id,amount, month, year, day, category_id FROM expenses WHERE user_id =" + userId + " AND month =" +
                month + " AND year = " + year + " ORDER BY year, month, day";
        ResultSet rs = statement.executeQuery(sql);
        Collection<Spending> spendings = new ArrayList<>();

        while (rs.next()) {
            Spending sp = new Spending();
            sp.setSpendingId(rs.getInt("expenses_id"));
            sp.setAmount(rs.getBigDecimal("amount"));

            User user = new User();
            user.setId(rs.getInt("user_id"));
            sp.setUser(user);

            int day = rs.getInt("day");

            Calendar calendar = new GregorianCalendar(year, month, day);
            sp.setDay(calendar);
            spendings.add(sp);

            sp.setCategory(getCategory(rs.getInt("category_id")));
        }
        return spendings;
    }

    public Collection<Spending> getSpendings(int userId) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT expenses_id, user_id, amount, month, year, day, category_id FROM expenses WHERE user_id =" + userId + " ORDER BY year, month, day";
        ResultSet rs = statement.executeQuery(sql);
        Collection<Spending> spendings = new ArrayList<>();

        while (rs.next()) {
            Spending sp = new Spending();
            sp.setSpendingId(rs.getInt("expenses_id"));
            sp.setAmount(rs.getBigDecimal("amount"));

            User user = new User();
            user.setId(rs.getInt("user_id"));
            sp.setUser(user);

            Calendar calendar = new GregorianCalendar(rs.getInt("year"), rs.getInt("month"), rs.getInt("day"));
            sp.setDay(calendar);
            spendings.add(sp);

            sp.setCategory(getCategory(rs.getInt("category_id")));
        }
        return spendings;
    }

    private Category getCategory(Integer categoryId) throws SQLException {
        Collection<Category> categories = getCategories();
        for (Category cat : categories) {
            if (cat.getCategoryId().equals(categoryId)) {
                return cat;
            }
        }
        return null;
    }

    public User login(String log, String password) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = "SELECT user_id, login, password FROM users WHERE login = '" + log + "' AND password = '" +
                password + "'";
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next() == false) {
            return null;
        }
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setPassword(rs.getString("password"));
        user.setLogin(rs.getString("login"));

        return user;
    }

//-----------------------------------------------------------------------------

    private void connectToDb() {
        try {
            readDatabaseProperties();
            conn = getConnection();
        } catch (SQLException ex) {
            for (Throwable t : ex)
                t.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readDatabaseProperties() throws IOException {
        props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);
    }

    private Connection getConnection() throws SQLException {
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}
