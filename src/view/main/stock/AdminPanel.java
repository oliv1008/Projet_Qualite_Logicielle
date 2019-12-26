package view.main.stock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.ItemDAO;
import controller.MainController;
import model.Item;
import view.misc.ItemComboBox;

public class AdminPanel extends JPanel {

	private ItemComboBox itemComboBox;

	public AdminPanel(JTable table) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		setPreferredSize(new Dimension(300, 0));
		//		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JLabel adminPanelLabel = new JLabel("Gestion des produits", SwingConstants.CENTER);
		adminPanelLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		add(adminPanelLabel);

		itemComboBox = new ItemComboBox();
		itemComboBox.setPreferredSize(new Dimension(260, 30));
		add(itemComboBox);

		JButton addItemButton = new JButton("Ajouter un nouveau produit");
		addItemButton.setPreferredSize(new Dimension(260, 30));
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
					new AddItemPanel(table);
			}
		});
		add(addItemButton);

		JButton removeItemButton = new JButton("Retirer le produit sélectionné");
		removeItemButton.setPreferredSize(new Dimension(260, 30));
		removeItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(itemComboBox.getSelectedItem() != null) {
					try {
						ItemDAO.deleteItem((Item)itemComboBox.getSelectedItem());
						MainController.refreshDisplay();
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		add(removeItemButton);

		JButton modifyItemButton = new JButton("Modifier le produit sélectionné");
		modifyItemButton.setPreferredSize(new Dimension(260, 30));
		modifyItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(itemComboBox.getSelectedItem() != null) {
					new ModifyItemPanel(table, (Item)itemComboBox.getSelectedItem());
				}
			}
		});
		add(modifyItemButton);
	}

	public void refresh() {
		itemComboBox.reload();
	}

}
