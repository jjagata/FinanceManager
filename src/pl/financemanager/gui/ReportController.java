package pl.financemanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class ReportController implements ActionListener {
	private AppService logic;
	private ReportView view;

	public ReportController(ReportView view) {
		this.logic = AppService.getInstance();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int month = view.getMonths().getSelectedIndex();
		int year = (Integer) view.getYears().getSelectedItem();
		try {
			view.getTableModel().setDataVector(logic.getSpendings(month, year, 1), Constants.REPORT_TABLE_HEADER);
		} catch (SQLException ex) {
			AppContext.showError();
		}

		view.update();
	}

}
