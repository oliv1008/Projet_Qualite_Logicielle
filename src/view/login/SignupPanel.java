package view.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupPanel extends JPanel implements ActionListener {

	private JTextField tLastName;
	private JTextField tFirstName;
	private JTextField tShop;
	private JTextField tMail;
	private JPasswordField tPassword;
	
	public SignupPanel() {
		
		setLayout(new BorderLayout());
		
//		setPreferredSize(new Dimension(500, 200));
		
		JPanel center = new JPanel();
		
		center.setLayout(new GridLayout(6, 2, 0, 5));
		center.setPreferredSize(new Dimension(480, 200));
		
		JLabel lLastName = new JLabel("Nom");
		center.add(lLastName);
		
		tLastName = new JTextField();
		center.add(tLastName);
		
		JLabel lFirstName = new JLabel("Prénom");
		center.add(lFirstName);
		
		tFirstName = new JTextField();
		center.add(tFirstName);
		
		JLabel lShop = new JLabel("Magasin");
		center.add(lShop);
		
		tShop = new JTextField();
		center.add(tShop);
		
		JLabel lMail = new JLabel("Mail");
		center.add(lMail);
		
		tMail = new JTextField();
		center.add(tMail);
		
		JLabel lPassword = new JLabel("Mot de passe");
		center.add(lPassword);
		
		tPassword = new JPasswordField();
		center.add(tPassword);
		
		add(center, BorderLayout.CENTER);
		
		JButton signUpButton = new JButton("S'inscrire");
		signUpButton.setPreferredSize(new Dimension(480, 30));
		
		add(signUpButton, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "S'inscrire" :	break;
		}
	}
	
}
