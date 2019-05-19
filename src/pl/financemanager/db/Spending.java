package pl.financemanager.db;

import java.math.BigDecimal;
import java.util.Calendar;

public class Spending {
    private Integer spendingId;
    private BigDecimal amount;
    private Calendar day;
    private Category category;
    private User user;

    public Spending() {
    }

    public Spending(Integer spendingId, BigDecimal amount, Calendar day, Category category, User user) {
        this.spendingId = spendingId;
        this.amount = amount;
        this.day = day;
        this.category = category;
        this.user = user;
    }

    public Integer getSpendingId() {
        return spendingId;
    }

    public void setSpendingId(Integer spendingId) {
        this.spendingId = spendingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Spending{" +
                "spendingId=" + spendingId +
                ", amount=" + amount +
                ", day=" + day +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}
