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
import model.Item;

public class ModifyItemPanel extends JFrame {
	
	private JTextField tfName;
	private JTextField tfDescription;
	private JTextField tfPrice;

	public ModifyItemPanel(JTable table, Item item) {
		
		setTitle("Modifier un produit");
		setMinimumSize(new Dimension(280, 300));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);

		JPanel panel = (JPanel) getContentPane();

		panel.setLayout(new FlowLayout());

		add(new JLabel("Nom"));

		tfName = new JTextField(item.getName());
		tfName.setPreferredSize(new Dimension(260, 30));
		tfName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfName);

		add(new JLabel("Description"));

		tfDescription = new JTextField(item.getDescription());
		tfDescription.setPreferredSize(new Dimension(260, 90));
		tfDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfDescription);
		
		add(new JLabel("Prix"));

		tfPrice = new JTextField(String.valueOf(item.getPrice()));
		tfPrice.setPreferredSize(new Dimension(260, 30));
		tfPrice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		add(tfPrice);
		
		JButton addItemButton = new JButton("Modifier ce produit");
		addItemButton.setPreferredSize(new Dimension(260, 30));
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					ItemDAO.modifyItem(item, tfName.getText(), tfDescription.getText(), Double.parseDouble(tfPrice.getText()));
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
