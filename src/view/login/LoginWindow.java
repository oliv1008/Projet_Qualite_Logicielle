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

/**
 * Window for the authentification system
 */
public class LoginWindow extends JFrame {
	
	/*===== ATTRIBUTES =====*/
	private JPanel center;
	
	private JButton signInButton;
	private JButton signUpButton;
	
	/*===== BUILDER =====*/
	public LoginWindow() {

		setTitle("Authentification");
		setMinimumSize(new Dimension(500, 335));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		setLayout(new BorderLayout());

		JPanel north = new JPanel();

		signInButton = new JButton("Se connecter");
		signInButton.setPreferredSize(new Dimension(240, 30));
		signInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displaySigninPanel();
			}
		});
		signInButton.setBackground(new Color(174, 174, 174)); 

		signUpButton = new JButton("S'inscrire");
		signUpButton.setPreferredSize(new Dimension(240, 30));
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displaySignupPanel();
			}
		});

		north.add(signInButton);
		north.add(signUpButton);

		add(north, BorderLayout.NORTH);

		center = new JPanel();

		center.add(new SigninPanel());

		add(center, BorderLayout.CENTER);

		setVisible(true);
	}

	/*===== METHODS =====*/
	
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
