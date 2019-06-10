package pl.financemanager.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainAppView {
	private JFrame frame;

	private ReportView reportView;
	private BudgetsView budgetsView;
	private SpendingsView spendingsView;

	public MainAppView(JFrame frame) {
		this.frame = frame;
		this.reportView = new ReportView();
		this.budgetsView = new BudgetsView();
		this.spendingsView = new SpendingsView();

		this.frame.setSize(1024, 700);
		this.frame.setResizable(false);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		this.frame.setLocation(x, y);

		JTabbedPane tabPane = new JTabbedPane();

		tabPane.addTab("Overview", reportView);
		tabPane.addTab("Budgets", budgetsView);
		tabPane.addTab("Spendings", spendingsView);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				if ("Overview".equals(sourceTabbedPane.getTitleAt(index))) {
					reportView.update();
				} else if ("Budgets".equals(sourceTabbedPane.getTitleAt(index))) {
					budgetsView.update();
				} else if ("Spendings".equals(sourceTabbedPane.getTitleAt(index))) {
					spendingsView.update();
				}
			}
		};

		tabPane.addChangeListener(changeListener);

		this.frame.add(tabPane);
	}

}
