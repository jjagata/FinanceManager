package pl.financemanager.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
		int month = view.getMonths().getSelectedIndex();
		int year = (Integer) view.getYears().getSelectedItem();
		try {
			view.getTableModel().setDataVector(logic.getSpendings(month, year, 1), Constants.REPORT_TABLE_HEADER);
		} catch (SQLException ex) {
			AppContext.getInstance().showError();
		}

		view.update();
	}

}
