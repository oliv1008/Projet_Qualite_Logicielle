package view.misc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class AbstractAddUserPanel extends JPanel {

	protected JTextField tLastName;
	protected JTextField tFirstName;
	protected ShopComboBox cbShop;
	protected JTextField tMail;
	protected JPasswordField tPassword;
	protected JComboBox<Integer> cbPrivilege;
	
	protected JPanel center;

	protected JButton signUpButton;

	protected boolean lastNameValid = false;
	protected boolean firstNameValid = false;
	protected boolean shopValid = false;
	protected boolean mailValid = false;
	protected boolean passwordValid = false;

	public AbstractAddUserPanel(){
		setLayout(new BorderLayout(5, 5));

		center = new JPanel();

		center.setLayout(new GridLayout(6, 2, 0, 5));
		center.setPreferredSize(new Dimension(480, 220));

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
		
		addPrivilegeCB();

		addFinalButton();

		updateSignUpButton();
	}

	protected abstract void updateSignUpButton();

	protected abstract void addFinalButton();

	protected abstract void addPrivilegeCB();

}
