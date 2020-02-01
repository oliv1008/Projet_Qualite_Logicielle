package view.main.users;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Window used to create a new user.
 */
public class AddUserWindow extends JFrame {

	/*===== BUILDER =====*/
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
