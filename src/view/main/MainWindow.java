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

import controller.MainController;
import view.main.stock.StockPanel;

/**
 * This class represent the "main view" of the Main App.
 * It contains the different tabs of the main app : employees, departments, checks and settings panels.
 * @see EmployeesJTable
 * @see DepartmentsJTable
 * @see CheckJTable
 * @see SettingsPanel
 */
public class MainWindow extends JFrame implements ActionListener {

	/*===== ATTRIBUTES =====*/
	StockPanel stockPanel = new StockPanel();
	JPanel usersPanel = new JPanel();
	JPanel accountPanel = new JPanel();	
	
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MainController.onExit();
			}
		});

		setUpButtons();

		setVisible(true);
		
		stockPanel.setBackground(Color.RED);
		usersPanel.setBackground(Color.BLUE);
		accountPanel.setBackground(Color.GREEN);
	}
	/*=====*********=====*/

	public void setUpButtons() {		

		JPanel buttonPane = new JPanel();

		stockButton = new JButton("Gestion du stock");
//		stockButton.setIcon(new ImageIcon());
		stockButton.addActionListener(this);
		stockButton.setBackground(new Color(174, 174, 174)); 

		usersButton = new JButton("Gestion des utilisateurs");
//		usersButton.setIcon(new ImageIcon());
		usersButton.addActionListener(this);

		accountButton = new JButton("Gestion du compte");
//		accountButton.setIcon(new ImageIcon());
		accountButton.addActionListener(this);

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

	public JPanel getUsersPanel() {
		return usersPanel;
	}

	public JPanel getAccountPanel() {
		return accountPanel;
	}

	/*===== ACTION LISTENER =====*/

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Gestion du stock" :
			stockButton.setBackground(new Color(174, 174, 174)); 
			usersButton.setBackground(null);
			accountButton.setBackground(null);
			cardLayout.show(contentPane, listContent[0]);
			break;
			
		case "Gestion des utilisateurs" :
			stockButton.setBackground(null); 
			usersButton.setBackground(new Color(174, 174, 174));
			accountButton.setBackground(null);
			cardLayout.show(contentPane, listContent[1]);
			break;
			
		case "Gestion du compte" :
			stockButton.setBackground(null); 
			usersButton.setBackground(null);
			accountButton.setBackground(new Color(174, 174, 174));
			cardLayout.show(contentPane, listContent[2]);
			break;
		}
	}

}
