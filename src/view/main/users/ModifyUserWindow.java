package view.main.users;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.User;

public class ModifyUserWindow extends JFrame {
	
	public ModifyUserWindow(User user) {

		setTitle("Modifier un utilisateur");
		setMinimumSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		
		setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.add(new ModifyUserPanel(user));

		add(center, BorderLayout.CENTER);
		
		setVisible(true);
	}

}
