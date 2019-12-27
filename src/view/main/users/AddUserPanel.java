package view.main.users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.MainController;
import controller.UserDAO;
import model.Shop;
import model.User;
import view.misc.AbstractAddUserPanel;

public class AddUserPanel extends AbstractAddUserPanel {

	public AddUserPanel() {
		super();
	}

	@Override
	protected void addFinalButton() {
		signUpButton = new JButton("Ajouter un utilisateur");
		signUpButton.setPreferredSize(new Dimension(480, 30));
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					UserDAO.addUser(tFirstName.getText(), 
							tLastName.getText(), 
							(Shop)cbShop.getSelectedItem(), 
							tMail.getText(), 
							tPassword.getPassword(), 
							(Integer)cbPrivilege.getSelectedItem());
					MainController.refreshDisplay();
					exitWindow();
				} catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					tFirstName.setText("");
					tLastName.setText("");
					cbShop.setSelectedItem(0);
					tMail.setText("");
					tPassword.setText("");
					cbPrivilege.setSelectedItem(2);
				}
			}
		});
		add(signUpButton, BorderLayout.SOUTH);
	}

	@Override
	protected void addPrivilegeCB() {
		JLabel lLastName = new JLabel("Privilège");
		center.add(lLastName);

		cbPrivilege = new JComboBox<Integer>();
		cbPrivilege.addItem(User.SUPER_ADMIN);
		cbPrivilege.addItem(User.ADMIN);
		cbPrivilege.addItem(User.SELLER);
		cbPrivilege.addItem(User.RESTRICTED_SELLER);
		cbPrivilege.setSelectedItem(2);
		center.add(cbPrivilege);
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

	public void exitWindow() {
		JFrame topFrame = (JFrame)SwingUtilities.getWindowAncestor(this);
		topFrame.dispose();
	}

}
