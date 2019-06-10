package pl.financemanager.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import pl.financemanager.db.Budget;

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
        int month = this.months.getSelectedIndex();
        int year = (Integer) this.years.getSelectedItem();
        int userId = AppContext.getInstance().getUser().getId();

        try {
            this.tableModel.setDataVector(
                    logic.getSpendings(month, year, userId),
                    Constants.REPORT_TABLE_HEADER);
        } catch (SQLException e) {
            AppContext.showError();
        }

        CategoryPlot p = (CategoryPlot) this.chart.getPlot();
        p.clearRangeMarkers();
        p.getRangeAxis().setUpperBound(5000);
        Budget budget = null;
        BigDecimal spendingsSum = null;
        try {
            p.setDataset(logic.createDataset(month, year, userId));
            budget = logic.getBudget(userId, month, year);
            spendingsSum = logic.getSpendingsSum(month, year, userId);
        } catch (SQLException e) {
            AppContext.showError();
        }

        if (budget != null) {
            if(spendingsSum != null && spendingsSum.doubleValue() > budget.getBudget().doubleValue()){
                p.getRangeAxis().setUpperBound(spendingsSum.doubleValue() * 1.2);
            } else {
                p.getRangeAxis().setUpperBound(budget.getBudget().doubleValue() * 1.2);
            }

            ValueMarker budgetMarker = new ValueMarker(budget.getBudget().doubleValue(), Color.BLACK, new BasicStroke(2.0F));
            p.addRangeMarker(budgetMarker);
        }
    }

    private JFreeChart createChart(int month, int year, int userId) {
        JFreeChart barChart = null;
        try {
            barChart = ChartFactory.createBarChart("Spendings", "Day", "Money",
                    logic.createDataset(month, (Integer) year, userId),
                    PlotOrientation.VERTICAL, false, true, false);
        } catch (SQLException e) {
            AppContext.showError();
        }

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
