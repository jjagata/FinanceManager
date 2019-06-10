package pl.financemanager.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class SpendingsView extends JPanel {
	private JLabel dayLabel;
	private JDatePickerImpl datePicker;
	private JLabel categoryLabel;
	private JComboBox<String> categories;

	private JLabel amountLabel;
	private JTextField amountField;
	private JButton saveButton;
	private JTable table;
	private DefaultTableModel tableModel;
	private AppLogic logic;

	public SpendingsView() {
		this.setLayout(new BorderLayout());

		this.logic = new AppLogic();

		this.dayLabel = new JLabel("Day: ");
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		this.categoryLabel = new JLabel("Category: ");
		this.categories = new JComboBox<>(logic.getCategories());
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
		this.tableModel.setDataVector(logic.getSpendings(1), Constants.SPENDINGS_TABLE_HEADER);

	}

	public JDatePickerImpl getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(JDatePickerImpl datePicker) {
		this.datePicker = datePicker;
	}

	public JComboBox<String> getCategories() {
		return categories;
	}

	public void setCategories(JComboBox<String> categories) {
		this.categories = categories;
	}

	public JTextField getAmountField() {
		return amountField;
	}

	public void setAmountField(JTextField amountField) {
		this.amountField = amountField;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
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
