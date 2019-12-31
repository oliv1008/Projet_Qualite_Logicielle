package view.main.users;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AddUserWindow extends JFrame {

	public AddUserWindow() {

		setTitle("Ajouter un utilisateur");
		setMinimumSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		
		setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.add(new AddUserPanel());

		add(center, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
