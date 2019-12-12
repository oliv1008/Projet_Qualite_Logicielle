package view.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;

public class SigninPanel extends JPanel implements ActionListener {

	private JTextField tMail;
	private JPasswordField tPassword;

	public SigninPanel() {

		setLayout(new BorderLayout());

		JPanel center = new JPanel();

		center.setLayout(new GridLayout(6, 2, 0, 5));
		center.setPreferredSize(new Dimension(480, 200));

		JLabel lMail = new JLabel("Mail");
		center.add(lMail);

		tMail = new JTextField();
		center.add(tMail);

		JLabel lPassword = new JLabel("Mot de passe");
		center.add(lPassword);

		tPassword = new JPasswordField();
		center.add(tPassword);

		add(center, BorderLayout.CENTER);

		JButton signUpButton = new JButton("Se connecter");
		signUpButton.setPreferredSize(new Dimension(480, 30));
		signUpButton.addActionListener(this);

		add(signUpButton, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Se connecter" :	
			try {
				LoginController.login(tMail.getText(), tPassword.getPassword());
			} catch(Exception error) {
//				error.printStackTrace();
				JOptionPane.showMessageDialog(this, "Mot de passe incorrect, réessayez", "Erreur", JOptionPane.ERROR_MESSAGE);
				tPassword.setText("");
			}

			break;
		}
	}

}
