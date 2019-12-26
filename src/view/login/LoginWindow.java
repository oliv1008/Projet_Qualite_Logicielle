package view.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class LoginWindow extends JFrame implements ActionListener {
	
	JPanel center;
	
	JButton signInButton;
	JButton signUpButton;
	
	public LoginWindow() {

		setTitle("Login");
		//		setSize(300,170);
		setMinimumSize(new Dimension(500, 315));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		setLayout(new BorderLayout());


		JPanel north = new JPanel();
		//		north.setLayout(new GridLayout(1, 0));

		signInButton = new JButton("Se connecter");
		signInButton.setPreferredSize(new Dimension(240, 30));
		signInButton.addActionListener(this);
		signInButton.setBackground(new Color(174, 174, 174)); 

		signUpButton = new JButton("S'inscrire");
		signUpButton.setPreferredSize(new Dimension(240, 30));
		signUpButton.addActionListener(this);

		north.add(signInButton);
		north.add(signUpButton);

		add(north, BorderLayout.NORTH);

		center = new JPanel();

		center.add(new SigninPanel());

		add(center, BorderLayout.CENTER);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Se connecter"	:	displaySigninPanel(); break;
		case "S'inscrire"	: 	displaySignupPanel(); break;
		}
	}
	
	public void displaySigninPanel() {
		center.removeAll(); 
		center.add(new SigninPanel()); 
		center.validate();
		signInButton.setBackground(new Color(174, 174, 174)); 
		signUpButton.setBackground(null); 
	}
	
	public void displaySignupPanel() {
		center.removeAll(); 
		center.add(new SignupPanel()); 
		center.validate();
		signUpButton.setBackground(new Color(174, 174, 174)); 
		signInButton.setBackground(null); 
	}
}
