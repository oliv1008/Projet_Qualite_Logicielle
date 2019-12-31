package view.main.account;

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

import controller.MainController;
import controller.UserDAO;

public class AccountPanel extends JPanel {

	public AccountPanel() {
		
		setLayout(new GridLayout(12, 0));
		
		add(new JPanel());
		add(new JPanel());
		
		JLabel adminPanelLabel = new JLabel("Gestion du compte", SwingConstants.CENTER);
		adminPanelLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		add(adminPanelLabel);
		
		JPanel firstNamePanel = new JPanel();
		firstNamePanel.add(new JLabel("Prénom :"));
		firstNamePanel.add(new JLabel(MainController.getCurrentUser().getFirstName()));
		add(firstNamePanel);
		
		JPanel lastNamePanel = new JPanel();
		lastNamePanel.add(new JLabel("Nom :"));
		lastNamePanel.add(new JLabel(MainController.getCurrentUser().getLastName()));
		add(lastNamePanel);
		
		JPanel shopPanel = new JPanel();
		shopPanel.add(new JLabel("Magasin :"));
		shopPanel.add(new JLabel(MainController.getCurrentUser().getShop().toString()));
		add(shopPanel);
		
		JPanel mailPanel = new JPanel();
		mailPanel.add(new JLabel("Mail :"));
		mailPanel.add(new JLabel(MainController.getCurrentUser().getMail()));
		add(mailPanel);
		
		JPanel buttonsPanel = new JPanel();
		JButton deleteAccountButton = new JButton("Supprimer le compte");
		deleteAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					UserDAO.deleteOwnAccount();
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonsPanel.add(deleteAccountButton);
		add(buttonsPanel);
	}
	
	
}
