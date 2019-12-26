package view.main.stock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.ItemDAO;
import controller.MainController;

public class AddItemPanel extends JFrame {
	
	private JTextField tfName;
	private JTextField tfDescription;
	private JTextField tfPrice;

	public AddItemPanel(JTable table) {
		
		setTitle("Ajouter un produit");
		setMinimumSize(new Dimension(280, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		JPanel panel = (JPanel) getContentPane();

		panel.setLayout(new FlowLayout());

		add(new JLabel("Nom"));

		tfName = new JTextField();
		tfName.setPreferredSize(new Dimension(260, 30));
		tfName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfName);

		add(new JLabel("Description"));

		tfDescription = new JTextField();
		tfDescription.setPreferredSize(new Dimension(260, 90));
		tfDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfDescription);
		
		add(new JLabel("Prix"));

		tfPrice = new JTextField();
		tfPrice.setPreferredSize(new Dimension(260, 30));
		tfPrice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfPrice);
		
		JButton addItemButton = new JButton("Ajouter un produit");
		addItemButton.setPreferredSize(new Dimension(260, 30));
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					ItemDAO.addItem(tfName.getText(), tfDescription.getText(), Double.parseDouble(tfPrice.getText()));
					MainController.refreshDisplay();
					exitWindow();
				} 
				catch(Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(addItemButton);

		setVisible(true);
	}
	
	public void exitWindow() {
		this.dispose();
	}
}
