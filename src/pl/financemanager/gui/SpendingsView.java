package pl.financemanager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pl.financemanager.db.Category;

class SpendingsView extends JPanel {
    private JLabel dayLabel;
    private JDatePickerImpl datePicker;
    private JLabel categoryLabel;
    private JComboBox<Category> categories;

    private JLabel amountLabel;
    private JTextField amountField;
    private JButton saveButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private AppLogic logic;

    public SpendingsView() {
        this.setLayout(new BorderLayout());

        this.logic = AppLogic.getInstance();

        this.dayLabel = new JLabel("Day: ");
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        DateLabelFormatter dateLabelFormatter = new DateLabelFormatter();
        this.datePicker = new JDatePickerImpl(datePanel, dateLabelFormatter);

        this.categoryLabel = new JLabel("Category: ");
        try {
            this.categories = new JComboBox<>(logic.getCategories());
        } catch (SQLException e) {
            AppContext.showError();
        }
        this.amountLabel = new JLabel("Amount: ");
        this.amountField = new JTextField(10);
        this.saveButton = new JButton("Save");
        this.table = new JTable();

        JPanel addSpendingPanel = new JPanel();
        addSpendingPanel.add(dayLabel);
        addSpendingPanel.add(datePicker);
        addSpendingPanel.add(amountLabel);
        addSpendingPanel.add(amountField);
        addSpendingPanel.add(categoryLabel);
        addSpendingPanel.add(categories);
        addSpendingPanel.add(saveButton);

        this.add(addSpendingPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        this.tableModel = new DefaultTableModel();
        this.table.setModel(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(800, 550));
        tablePanel.add(tableScrollPane);

        this.add(tablePanel, BorderLayout.SOUTH);

        SpendingsController budgetController = new SpendingsController(this);
        this.saveButton.setActionCommand("saveSpending");
        this.saveButton.addActionListener(budgetController);

        this.update();
    }

    public void update() {
        try {
            this.tableModel.setDataVector(logic.getSpendings(AppContext.getUser().getId()), Constants.SPENDINGS_TABLE_HEADER);
        } catch (SQLException e) {
            AppContext.showError();
        }
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }

    public JComboBox<Category> getCategories() {
        return categories;
    }

    public JTextField getAmountField() {
        return amountField;
    }
}

class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "dd.MM.yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
