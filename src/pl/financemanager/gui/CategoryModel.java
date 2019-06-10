package pl.financemanager.gui;

import pl.financemanager.db.Category;

import javax.swing.*;

class CategoryModel extends DefaultComboBoxModel<Category> {
    public CategoryModel(Category[] items) {
        super(items);
    }

    @Override
    public Category getSelectedItem() {
        Category selectedCategory = (Category) super.getSelectedItem();
        return selectedCategory;
    }
}
