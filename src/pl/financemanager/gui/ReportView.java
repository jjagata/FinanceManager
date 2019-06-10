package pl.financemanager.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;

public class ReportView extends JPanel {
	private JLabel monthLabel;
	private JLabel yearLabel;
	private JComboBox<String> months;
	private JComboBox<Integer> years;
	private JTable table;
	private DefaultTableModel tableModel;
	private AppLogic logic;
	private ChartPanel chartPanel;
	private JFreeChart chart;

	public ReportView() {
		this.setLayout(new BorderLayout());

		this.monthLabel = new JLabel("Month: ");
		this.yearLabel = new JLabel("Year: ");
		this.months = new JComboBox<>(Utils.monthList);
		this.years = new JComboBox<>(Utils.yearList);
		this.table = new JTable();
		this.logic = new AppLogic();

		JPanel filterPanel = new JPanel();
		filterPanel.add(this.monthLabel);
		filterPanel.add(this.months);
		filterPanel.add(this.yearLabel);
		filterPanel.add(this.years);

		Calendar cal = new GregorianCalendar();
		this.months.setSelectedItem(Utils.getMonth(cal.get(Calendar.MONTH)));
		this.years.setSelectedItem(cal.get(Calendar.YEAR));

		this.add(filterPanel, BorderLayout.NORTH);

		JPanel tablePanel = new JPanel();
		this.tableModel = new DefaultTableModel();
		this.table.setModel(tableModel);
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(700, 200));
		tablePanel.add(tableScrollPane);

		this.add(tablePanel, BorderLayout.CENTER);

		this.chart = createChart(this.months.getSelectedIndex(), (Integer) this.years.getSelectedItem(), 1);
		this.chartPanel = new ChartPanel(chart);
		this.chartPanel.setPreferredSize(new Dimension(700, 350));
		this.add(this.chartPanel, BorderLayout.SOUTH);

		ReportController rvController = new ReportController(this);
		this.months.setActionCommand("changeMonth");
		this.months.addActionListener(rvController);
		this.years.setActionCommand("changeYear");
		this.years.addActionListener(rvController);

		this.update();
	}

	public void update() {
		this.tableModel.setDataVector(
				logic.getSpendings(this.months.getSelectedIndex(), (Integer) this.years.getSelectedItem(), 1),
				Constants.REPORT_TABLE_HEADER);

		CategoryPlot p = (CategoryPlot) this.chart.getPlot();
		p.setDataset(logic.createDataset(this.months.getSelectedIndex(), (Integer) this.years.getSelectedItem(), 1));
	}

	private JFreeChart createChart(int month, int year, int userId) {
		JFreeChart barChart = ChartFactory.createBarChart("Spendings", "Day", "Money",
				logic.createDataset(this.months.getSelectedIndex(), (Integer) this.years.getSelectedItem(), 1),
				PlotOrientation.VERTICAL, false, true, false);

		CategoryPlot p = (CategoryPlot) barChart.getPlot();
		p.getRangeAxis().setUpperBound(6000);
		ValueMarker budgetMarker = new ValueMarker(5000, Color.BLACK, new BasicStroke(2.0F));
		p.addRangeMarker(budgetMarker);

		return barChart;
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

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

}
