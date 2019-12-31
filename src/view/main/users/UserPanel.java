package view.main.users;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.UserDAO;
import model.User;
import view.misc.UserComboBox;

public class UserPanel extends JPanel {

	private UserComboBox userComboBox;

	private JLabel lFirstName;
	private JLabel lLastName;
	private JLabel lShop;
	private JLabel lMail;
	private JLabel lPrivilege;
	
	private JButton modifyUserButton;
	private JButton removeUserButton;

	public UserPanel() {

		JPanel contentPanel = new JPanel();

		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		contentPanel.setPreferredSize(new Dimension(400, 600));

		JLabel adminPanelLabel = new JLabel("Gestion des utilisateurs", SwingConstants.CENTER);
		adminPanelLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		contentPanel.add(adminPanelLabel);

		userComboBox = new UserComboBox();
		userComboBox.setPreferredSize(new Dimension(280, 30));
		userComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				updatePanel();
			}
		});
		contentPanel.add(userComboBox);

		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setLayout(new GridLayout(5, 2));
		userInfoPanel.setPreferredSize(new Dimension(400, 150));

		userInfoPanel.add(new JLabel("Firstname :"));
		lFirstName = new JLabel();
		userInfoPanel.add(lFirstName);

		userInfoPanel.add(new JLabel("Lastname :"));
		lLastName = new JLabel();
		userInfoPanel.add(lLastName);

		userInfoPanel.add(new JLabel("Shop :"));
		lShop = new JLabel();
		userInfoPanel.add(lShop);

		userInfoPanel.add(new JLabel("Mail :"));
		lMail = new JLabel();
		userInfoPanel.add(lMail);

		userInfoPanel.add(new JLabel("Privilege :"));
		lPrivilege = new JLabel();
		userInfoPanel.add(lPrivilege);

		contentPanel.add(userInfoPanel);

		JButton addUserButton = new JButton("Ajouter un nouvel utilisateur");
		addUserButton.setPreferredSize(new Dimension(280, 30));
		addUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				new AddUserWindow();
			}
		});
		contentPanel.add(addUserButton);

		removeUserButton = new JButton("Supprimer l'utilisateur selectionné");
		removeUserButton.setPreferredSize(new Dimension(280, 30));
		removeUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(userComboBox.getSelectedItem() != null) {
					try {
						UserDAO.deleteUser((User)userComboBox.getSelectedItem());
						userComboBox.reload();
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		contentPanel.add(removeUserButton);

		modifyUserButton = new JButton("Modifier l'utilisateur sélectionné");
		modifyUserButton.setPreferredSize(new Dimension(280, 30));
		modifyUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(userComboBox.getSelectedItem() != null) {
					new ModifyUserWindow((User)userComboBox.getSelectedItem());
				}
			}
		});
		contentPanel.add(modifyUserButton);
		
		updatePanel();

		add(contentPanel);
	}

	public void refresh() {
		userComboBox.reload();
	}
	
	private void updatePanel() {
		if(userComboBox.getSelectedItem() != null) {
			User user = (User)userComboBox.getSelectedItem();
			lFirstName.setText(user.getFirstName());
			lLastName.setText(user.getLastName());
			lShop.setText(user.getShop().toString());
			lMail.setText(user.getMail());
			lPrivilege.setText(String.valueOf(user.getPrivilege()));
			modifyUserButton.setEnabled(true);
			removeUserButton.setEnabled(true);
		}
		else {
			lFirstName.setText("-");
			lLastName.setText("-");
			lShop.setText("-");
			lMail.setText("-");
			lPrivilege.setText("-");
			modifyUserButton.setEnabled(false);
			removeUserButton.setEnabled(false);
		}
	}

}
