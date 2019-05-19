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

    public DBAdapter(){
       connectToDb();
    }

    public Collection<Category> getCategories()throws SQLException{
        Statement statement = conn.createStatement();
        String sql = "SELECT category_id, category_name FROM categories";
        ResultSet rs = statement.executeQuery(sql);
        Collection<Category> categories = new ArrayList<>();
        while(rs.next()){
            Category cat = new Category(rs.getInt("category_id"),rs.getString("category_name"));
            categories.add(cat);
        }
        return categories;
    }
    public Collection<Budget> getBudgets(int userId)throws SQLException{
        Statement statement = conn.createStatement();
        String sql = "SELECT user_id,amount, month, year, budget_id FROM budgets WHERE user_id ="+ userId;
        ResultSet rs = statement.executeQuery(sql);
        Collection<Budget> budgets = new ArrayList<>();
        while(rs.next()){
           Budget bu = new Budget();
           bu.setBudgetId(rs.getInt("budget_id"));
           bu.setBudget(rs.getBigDecimal("amount"));

           User user = new User();
           user.setId(rs.getInt("user_id"));
           bu.setUser(user);

           int month = rs.getInt("month");
           int year = rs.getInt("year");

           Calendar calendar = new GregorianCalendar(year,month,1);
           bu.setMonth(calendar);
           budgets.add(bu);

        }
        return budgets;
    }

    private void connectToDb(){
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
