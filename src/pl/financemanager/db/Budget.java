package pl.financemanager.db;
import java.math.BigDecimal;
import java.util.Calendar;

public class Budget {
    private Integer budgetId;
    private Calendar month;
    private BigDecimal budget ;
    private User user;

    public Budget(){

    }

    public Budget(Integer budgetId, Calendar month, BigDecimal budget, User user) {
        this.budgetId = budgetId;
        this.month = month;
        this.budget = budget;
        this.user = user;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public Calendar getMonth() {
        return month;
    }

    public void setMonth(Calendar month) {
        this.month = month;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", month=" + month +
                ", budget=" + budget +
                ", user=" + user +
                '}';
    }
}
