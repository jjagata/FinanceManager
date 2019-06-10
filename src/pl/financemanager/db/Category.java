package pl.financemanager.db;

public class Category {
    private Integer categoryId;
    private String categoryName;

    public Category() {

    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
