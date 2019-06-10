package pl.financemanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpendingsController implements ActionListener {
	private SpendingsView view;
	private AppLogic logic;
	
	public SpendingsController(SpendingsView view) {
		this.view = view;
		this.logic = new AppLogic();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		view.getTableModel().setDataVector(logic.getSpendings(1), Constants.SPENDINGS_TABLE_HEADER);
		
	}

}
