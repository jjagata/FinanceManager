package pl.financemanager;

import pl.financemanager.db.Category;
import pl.financemanager.db.DBAdapter;

import java.sql.SQLException;
import java.util.Collection;

public class Application {
    public static void main(String[] args) throws SQLException {
        DBAdapter dbAdapter = new DBAdapter();

        Collection<Category> test = dbAdapter.getCategories();
        for(Category c :test){
            System.out.println(c);
        }
    }


}
