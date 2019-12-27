package view.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.LoginController;
import model.Shop;
import view.misc.AbstractAddUserPanel;

public class SignupPanel extends AbstractAddUserPanel {
		
	public SignupPanel() {
		super();
	}
	
	@Override
	protected void addFinalButton() {
		signUpButton = new JButton("S'inscrire");
		signUpButton.setPreferredSize(new Dimension(480, 30));
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginController.signup(tFirstName.getText(), tLastName.getText(), (Shop)cbShop.getSelectedItem(), tMail.getText(), tPassword.getPassword());
					JOptionPane.showMessageDialog(new JFrame(), "Compte vendeur créé avec succès", "Information", JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception error) {
					JOptionPane.showMessageDialog(new JFrame(), "Informations incorrectes, réessayez", "Erreur", JOptionPane.ERROR_MESSAGE);
					tFirstName.setText("");
					tLastName.setText("");
					cbShop.setSelectedItem(0);
					tMail.setText("");
					tPassword.setText("");
				}
			}
		});
		add(signUpButton, BorderLayout.SOUTH);
	}
	
	@Override
	protected void addPrivilegeCB() {
		
	}
	
	@Override
	protected void updateSignUpButton() {
		if(firstNameValid && lastNameValid && shopValid && mailValid && passwordValid) {
			signUpButton.setEnabled(true);
		}
		else {
			signUpButton.setEnabled(false);
		}
	}
	
}
