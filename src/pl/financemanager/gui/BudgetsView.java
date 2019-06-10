package pl.financemanager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BudgetsView extends JPanel {
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JComboBox<String> months;
    private JComboBox<Integer> years;
    private JLabel budgetLabel;
    private JTextField budgetField;
    private JButton saveButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private AppLogic logic;

    public BudgetsView() {
        this.setLayout(new BorderLayout());

        this.monthLabel = new JLabel("Month: ");
        this.yearLabel = new JLabel("Year: ");
        this.months = new JComboBox<>(Utils.monthList);
        this.years = new JComboBox<>(Utils.yearList);
        this.budgetLabel = new JLabel("Budget: ");
        this.budgetField = new JTextField(10);
        this.saveButton = new JButton("Save");
        this.table = new JTable();
        this.logic = new AppLogic();

        JPanel addBudgetPanel = new JPanel();
        addBudgetPanel.add(this.monthLabel);
        addBudgetPanel.add(this.months);
        addBudgetPanel.add(this.yearLabel);
        addBudgetPanel.add(this.years);
        addBudgetPanel.add(this.budgetLabel);
        addBudgetPanel.add(this.budgetField);
        addBudgetPanel.add(this.saveButton);

        Calendar cal = new GregorianCalendar();
        this.months.setSelectedItem(Utils.getMonth(cal.get(Calendar.MONTH)));
        this.years.setSelectedItem(cal.get(Calendar.YEAR));

        this.add(addBudgetPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        this.tableModel = new DefaultTableModel();
        this.table.setModel(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(800, 550));
        tablePanel.add(tableScrollPane);

        this.add(tablePanel, BorderLayout.SOUTH);

        BudgetsController budgetController = new BudgetsController(this);
        this.saveButton.setActionCommand("saveBudget");
        this.saveButton.addActionListener(budgetController);

        this.update();
    }

    public void update() {
        try {
            this.tableModel.setDataVector(
                    logic.getBudgets(AppContext.getInstance().getUser().getId()),
                    Constants.BUDGETS_TABLE_HEADER);
        } catch (SQLException e) {
            AppContext.getInstance().showError();
        }

    }

    public JComboBox<String> getMonths() {
        return months;
    }

    public void setMonths(JComboBox<String> months) {
        this.months = months;
    }

    public JComboBox<Integer> getYears() {
        return years;
    }

    public void setYears(JComboBox<Integer> years) {
        this.years = years;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTextField getBudgetField() {
        return budgetField;
    }

    public void setBudgetField(JTextField budgetField) {
        this.budgetField = budgetField;
    }
}
