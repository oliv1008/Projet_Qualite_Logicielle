package view.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.LoginController;
import model.Shop;
import view.misc.ShopComboBox;

public class SignupPanel extends JPanel {

	private JTextField tLastName;
	private JTextField tFirstName;
	private ShopComboBox cbShop;
	private JTextField tMail;
	private JPasswordField tPassword;
	
	private JButton signUpButton;
	
	private boolean lastNameValid = false;
	private boolean firstNameValid = false;
	private boolean shopValid = true;
	private boolean mailValid = false;
	private boolean passwordValid = false;
	
	
	public SignupPanel() {
		
		setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		
		center.setLayout(new GridLayout(6, 2, 0, 5));
		center.setPreferredSize(new Dimension(480, 200));
		
		JLabel lLastName = new JLabel("Nom");
		center.add(lLastName);
		
		tLastName = new JTextField();
		tLastName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tLastName.getText().matches("[A-Za-z]+")) {
					tLastName.setBorder(BorderFactory.createLineBorder(null));
					lastNameValid = true;
				}
				else {
					tLastName.setBorder(BorderFactory.createLineBorder(Color.RED));
					lastNameValid = false;
				}
				updateSignUpButton();
			}
		});
		tLastName.setBorder(BorderFactory.createLineBorder(Color.RED));
		center.add(tLastName);
		
		JLabel lFirstName = new JLabel("Prénom");
		center.add(lFirstName);
		
		tFirstName = new JTextField();
		tFirstName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tLastName.getText().matches("[A-Za-z]+")) {
					tFirstName.setBorder(BorderFactory.createLineBorder(null));
					firstNameValid = true;
				}
				else {
					tFirstName.setBorder(BorderFactory.createLineBorder(Color.RED));
					firstNameValid = false;
				}
				updateSignUpButton();
			}
		});
		tFirstName.setBorder(BorderFactory.createLineBorder(Color.RED));
		center.add(tFirstName);
		
		JLabel lShop = new JLabel("Magasin");
		center.add(lShop);
		
		cbShop = new ShopComboBox();	
		cbShop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbShop.getSelectedItem() == null) {
					cbShop.setBorder(BorderFactory.createLineBorder(Color.RED));
					shopValid = false;
				}
				else {
					cbShop.setBorder(BorderFactory.createLineBorder(null));
					shopValid = true;
				}
				updateSignUpButton();
			}
		});
		cbShop.setBorder(BorderFactory.createLineBorder(Color.RED));
		center.add(cbShop);
		
		JLabel lMail = new JLabel("Mail");
		center.add(lMail);
		
		tMail = new JTextField();
		tMail.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tMail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+[.][a-z]+$")) {
					tMail.setBorder(BorderFactory.createLineBorder(null));
					mailValid = true;
				}
				else {
					tMail.setBorder(BorderFactory.createLineBorder(Color.RED));
					mailValid = false;
				}
				updateSignUpButton();
			}
		});
		tMail.setBorder(BorderFactory.createLineBorder(Color.RED));
		center.add(tMail);
		
		JLabel lPassword = new JLabel("Mot de passe");
		center.add(lPassword);
		
		tPassword = new JPasswordField();
		tPassword.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tPassword.getPassword().length != 0) {
					tPassword.setBorder(BorderFactory.createLineBorder(null));
					passwordValid = true;
				}
				else {
					tPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
					passwordValid = false;
				}
				updateSignUpButton();
			}
		});
		tPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
		center.add(tPassword);
		
		add(center, BorderLayout.CENTER);
		
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
		
		updateSignUpButton();
	}
	
	private void updateSignUpButton() {
		if(firstNameValid && lastNameValid && shopValid && mailValid && passwordValid) {
			signUpButton.setEnabled(true);
		}
		else {
			signUpButton.setEnabled(false);
		}
	}
	
}
