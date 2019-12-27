package view.main.stock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ItemDAO;
import controller.MainController;
import model.Item;
import model.ItemList;
import model.Shop;
import model.User;
import view.misc.ShopComboBox;

public class StockPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	private ItemList itemList;
	private JTable table;

	private AdminPanel adminPanel = null;				/** Admin Panel for item management **/
	private FilterPanel filterPanel;					/** Filter Panel for filtering stocks **/

	private SpinnerNumberModel quantitySpinnerModel;	/** SpinnerModel for item quantity **/
	private JSpinner quantitySpinner;					/** Spinner for item quantity **/
	private SpinnerNumberModel transferSpinnerModel;	/** SpinnerModel for item transfer **/
	private JSpinner transferSpinner;					/** Spinner for item transfer **/
	private ShopComboBox shopComboBox;					/** ComboBox for item transfer **/
	private JButton transferButton;						/** Button for item transfer **/

	/*===== BUILDER =====*/
	public StockPanel() {
		setLayout(new BorderLayout());

		// Adds stock panel
		itemList = new ItemList();
		table = new JTable(itemList);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// Adds buttons panel
		setUpButtons();

		// Adds filter panel
		filterPanel = new FilterPanel(table);
		add(filterPanel, BorderLayout.EAST);

		// Adds admin panel, if needed
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			adminPanel = new AdminPanel(table);
			add(adminPanel, BorderLayout.WEST);
		}

		setUpActionListener();

	}

	/*===== BUILDER METHODS =====*/

	/**
	 * Sets up the action listener to the table.
	 * This is used to disable some buttons (delete) when the user should click on them.
	 */
	private void setUpActionListener() {
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				// If no cells are selected
				if(table.getSelectionModel().isSelectionEmpty()) {
					quantitySpinner.setEnabled(false);
					transferSpinner.setEnabled(false);
					shopComboBox.setEnabled(false);
					transferButton.setEnabled(false);
					quantitySpinnerModel.setValue(0);
					transferSpinnerModel.setValue(0);
				}
				else {
					int privilege = MainController.getCurrentUser().getPrivilege();
					Shop userShop = MainController.getCurrentUser().getShop();
					Shop selectedShop = (Shop)itemList.getValueAt(table.getSelectedRow(), 0);

					if(privilege <= User.RESTRICTED_SELLER || (privilege == User.SELLER && !userShop.equals(selectedShop))) {
						quantitySpinner.setEnabled(false);
						transferSpinner.setEnabled(false);
						shopComboBox.setEnabled(false);
						transferButton.setEnabled(false);
					}
					// Else
					else {
						quantitySpinner.setEnabled(true);
						quantitySpinnerModel.setValue(itemList.getValueAt(table.getSelectedRow(), 2));
						transferSpinner.setEnabled(true);
						transferSpinnerModel.setValue(0);
						shopComboBox.setEnabled(true);
						transferButton.setEnabled(true);
					}
				}
			}
		});
	}

	/**
	 * Sets up all the buttons of the panel (addCheck, removeCheck, synchronize, save...)
	 */
	private void setUpButtons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		//		buttons.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		buttons.add(new JLabel("Modifier la quantité d'un produit :"));

		quantitySpinnerModel = new SpinnerNumberModel(0, 0, null, 1);
		quantitySpinner = new JSpinner(quantitySpinnerModel);
		quantitySpinner.setPreferredSize(new Dimension(40, 25));
		quantitySpinner.addChangeListener(new ChangeListener() {      
			@Override
			public void stateChanged(ChangeEvent ce) {
				if(!table.getSelectionModel().isSelectionEmpty()) {
					try {
						Item item = 		(Item)itemList.getValueAt(table.getSelectedRow(), 1);
						Integer quantity = 	(int)quantitySpinnerModel.getValue();
						Shop shop = 		(Shop)itemList.getValueAt(table.getSelectedRow(), 0);
						ItemDAO.changeStock(shop, item, quantity);
						itemList.setValueAt(quantity, table.getSelectedRow(), 1);
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		quantitySpinner.setEnabled(false);
		buttons.add(quantitySpinner);

		buttons.add(new JLabel("     |        Transferer"));

		transferSpinnerModel = new SpinnerNumberModel(0, 0, null, 1);
		transferSpinner = new JSpinner(transferSpinnerModel);
		transferSpinner.setPreferredSize(new Dimension(40, 25));
		transferSpinner.setEnabled(false);
		buttons.add(transferSpinner);

		buttons.add(new JLabel("produit(s) au magasin"));

		shopComboBox = new ShopComboBox();
		buttons.add(shopComboBox);

		transferButton = new JButton("OK");
		transferButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					Item item = 		(Item)itemList.getValueAt(table.getSelectedRow(), 1);
					Integer quantity = 	(int)transferSpinnerModel.getValue();
					Shop from = 		(Shop)itemList.getValueAt(table.getSelectedRow(), 0);
					Shop to = 			(Shop)shopComboBox.getSelectedItem();
					ItemDAO.transferItem(item, quantity, from, to);
					MainController.refreshDisplay();
				}
				catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		transferButton.setEnabled(false);
		buttons.add(transferButton);

		buttons.add(new JLabel("     |        "));

		JButton seeItem = new JButton("Consulter le produit");
		seeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!table.getSelectionModel().isSelectionEmpty()) {
					new SeeItemPanel(table, (Item)itemList.getValueAt(table.getSelectedRow(), 1));
				}
			}
		});
		buttons.add(seeItem);

		add(buttons, BorderLayout.SOUTH);
	}
	
	/*===== METHODS =====*/
	public void refresh() {
		itemList.rebuildModel();
		shopComboBox.reload();
		filterPanel.refresh();
		if(adminPanel != null) adminPanel.refresh();
	}

}
