package pl.financemanager.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;

public class ReportController implements ActionListener {
	private AppLogic logic;
	private ReportView view;

	public ReportController(ReportView view) {
		this.logic = new AppLogic();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if ("changeMonth".equals(e.getActionCommand())) {
//
//		} else if ("changeYear".equals(e.getActionCommand())) {
//
//		}

		int month = view.getMonths().getSelectedIndex();
		int year = (Integer) view.getYears().getSelectedItem();
		view.getTableModel().setDataVector(logic.getSpendings(month, year, 1), Constants.REPORT_TABLE_HEADER);

		CategoryPlot p = (CategoryPlot) view.getChart().getPlot();
		p.setDataset(logic.createDataset(month, year, 1));
		p.getRangeAxis().setUpperBound(6000);
		ValueMarker budgetMarker = new ValueMarker(5000, Color.BLACK, new BasicStroke(2.0F));
		p.addRangeMarker(budgetMarker);
	}

}
