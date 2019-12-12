package view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame {

	public MainWindow() {

		setTitle("Application");
		setMinimumSize(new Dimension(900, 600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		setLayout(new BorderLayout());

		setVisible(true);
	}
}
