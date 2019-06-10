package pl.financemanager.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                	startGUI();
                } catch (Exception e) {
                	JOptionPane.showMessageDialog(null,
        				    "An error occurred!",
        				    "Inane error",
        				    JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	}
	
	public static void startGUI() throws Exception {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Finance Manager");
				
		new LoginView(frame);
    }

}
