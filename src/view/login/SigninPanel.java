package view.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;

/**
 * Panel for the connection to the main app
 */
public class SigninPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	private JTextField tMail;
	private JPasswordField tPassword;

	/*===== BUILDER =====*/
	public SigninPanel() {

		setLayout(new BorderLayout(5, 5));

		JPanel center = new JPanel();

		center.setLayout(new GridLayout(6, 2, 0, 5));
		center.setPreferredSize(new Dimension(480, 220));

		JLabel lMail = new JLabel("Mail");
		center.add(lMail);

		tMail = new JTextField();
		center.add(tMail);

		JLabel lPassword = new JLabel("Mot de passe");
		center.add(lPassword);

		tPassword = new JPasswordField();
		center.add(tPassword);

		add(center, BorderLayout.CENTER);

		JButton signInButton = new JButton("Se connecter");
		signInButton.setPreferredSize(new Dimension(480, 30));
		signInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					LoginController.login(tMail.getText(), tPassword.getPassword());
				} catch(Exception error) {
					if(error.getMessage() == LoginController.BAD_LOGIN) {
						JOptionPane.showMessageDialog(new JFrame(), "Mot de passe incorrect, réessayez", "Erreur", JOptionPane.ERROR_MESSAGE);
						tPassword.setText("");
					}
					else {
						error.printStackTrace();
					}
				}
			}	
		});
		add(signInButton, BorderLayout.SOUTH);

	}

}
