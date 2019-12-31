package view.main.users;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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

public class ModifyUserPanel extends AbstractAddUserPanel {

	private User user;
	
	public ModifyUserPanel(User user) {
		super();
		this.user = user;
		tLastName.setText(user.getLastName());
		tFirstName.setText(user.getFirstName());
		cbShop.setSelectedItem(user.getShop());
		tMail.setText(user.getMail());
		tPassword.setText("");
		cbPrivilege.setSelectedItem(user.getPrivilege());
		
		tMail.setEnabled(false);
		tPassword.setEnabled(false);
	}
	
	@Override
	protected void addFinalButton() {
		signUpButton = new JButton("Modifier cet utilisateur");
		signUpButton.setPreferredSize(new Dimension(480, 30));
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					UserDAO.modifyUser(user, 
							tFirstName.getText(),
							tLastName.getText(),
							(Shop)cbShop.getSelectedItem(),
							tMail.getText(),
							tPassword.getPassword(),
							(Integer)cbPrivilege.getSelectedItem()
							);
					MainController.refreshDisplay();
					exitWindow();
				} catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					tLastName.setText(user.getLastName());
					tFirstName.setText(user.getFirstName());
					cbShop.setSelectedItem(user.getShop());
					tMail.setText(user.getMail());
					tPassword.setText("");
					cbPrivilege.setSelectedItem(user.getPrivilege());
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
		if(firstNameValid && lastNameValid && shopValid) {
			signUpButton.setEnabled(true);
			tPassword.setBorder(BorderFactory.createLineBorder(null));
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
