package view.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.MainController;
import model.User;
import view.main.account.AccountPanel;
import view.main.stock.StockPanel;
import view.main.users.UserPanel;

public class MainWindow extends JFrame {

	/*===== ATTRIBUTES =====*/
	StockPanel stockPanel = new StockPanel();
	UserPanel usersPanel = new UserPanel();
	AccountPanel accountPanel = new AccountPanel();	
	
	JButton stockButton;
	JButton usersButton;
	JButton accountButton;

	JPanel contentPane = new JPanel();
	CardLayout cardLayout = new CardLayout();
	String[] listContent = {"Stock", "Users", "Account"};

	/*===== BUILDER =====*/
	public MainWindow() {
		// Window configuration (name, size and minimum size, location...)
		setTitle("Application principale");
		setSize(1200,600);
		setMinimumSize(new Dimension(770, 200));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainController.onExit();
			}
		});

		setUpButtons();

		setVisible(true);
	}
	/*=====*********=====*/

	public void setUpButtons() {		

		JPanel buttonPane = new JPanel();

		stockButton = new JButton("Gestion du stock");
		stockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stockButton.setBackground(new Color(174, 174, 174)); 
				usersButton.setBackground(null);
				accountButton.setBackground(null);
				cardLayout.show(contentPane, listContent[0]);
			}	
		});
		stockButton.setBackground(new Color(174, 174, 174)); 

		usersButton = new JButton("Gestion des utilisateurs");
		usersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stockButton.setBackground(null); 
				usersButton.setBackground(new Color(174, 174, 174));
				accountButton.setBackground(null);
				cardLayout.show(contentPane, listContent[1]);
			}	
		});
		if(MainController.getCurrentUser().getPrivilege() < User.ADMIN) {
			usersButton.setEnabled(false);
		}

		accountButton = new JButton("Gestion du compte");
		accountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stockButton.setBackground(null); 
				usersButton.setBackground(null);
				accountButton.setBackground(new Color(174, 174, 174));
				cardLayout.show(contentPane, listContent[2]);
			}	
		});

		// Adds all the buttons to the button panel
		buttonPane.setLayout(new GridLayout(1, 3));
		buttonPane.add(stockButton);
		buttonPane.add(usersButton);
		buttonPane.add(accountButton);

		// Adds all the panels to the content panel
		contentPane.setLayout(cardLayout);
		contentPane.add(stockPanel, listContent[0]);
		contentPane.add(usersPanel, listContent[1]);
		contentPane.add(accountPanel, listContent[2]);

		getContentPane().add(buttonPane, BorderLayout.NORTH);
		getContentPane().add(contentPane, BorderLayout.CENTER);

	}

	/*===== GETTERS =====*/

	public StockPanel getStockPanel() {
		return stockPanel;
	}

	public UserPanel getUsersPanel() {
		return usersPanel;
	}

	public AccountPanel getAccountPanel() {
		return accountPanel;
	}

}
