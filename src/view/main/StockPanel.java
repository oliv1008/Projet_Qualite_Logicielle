package view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.Item;
import model.ItemList;
import model.Shop;

public class StockPanel extends JPanel implements ActionListener {

	/*===== ATTRIBUTES =====*/
	private ItemList itemList;
	private JTable table;

	private JButton addButton;
	private JButton removeButton;
	private JButton saveButton;
	private JButton syncButton;
	private JButton resetButton;

	private JComboBox<Item> itemComboBox;
	private JComboBox<Shop> shopComboBox;

	private JTextField searchBar;

	private TableRowSorter<TableModel> rowSorter;
	private ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();

	/*===== GETTERS =====*/

	public JComboBox<Item> getItemComboBox() {
		return itemComboBox;
	}

	public JComboBox<Shop> getShopComboBox() {
		return shopComboBox;
	}

	/*===== BUILDER =====*/
	public StockPanel() {
		setLayout(new BorderLayout());

		itemList = new ItemList();
		table = new JTable(itemList);
		add(new JScrollPane(table), BorderLayout.CENTER);

		setUpActionListener();

		setUpButtons();

		setUpFilterPanel();

		setUpSearchBar();

		setUpRenderers();

	}

	/*===== BUILDER METHODS =====*/

	/**
	 * Sets up the action listener to the table.
	 * This is used to disable some buttons (delete) when the user should click on them.
	 */
	private void setUpActionListener() {
//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent event) {
//				// If no cells are selected
//				if(table.getSelectionModel().isSelectionEmpty()) {
//					// Disable and change the color of buttons
//					removeButton.setBackground(Color.LIGHT_GRAY);
//					removeButton.setEnabled(false);
//				}
//				// Else
//				else {
//					// Enable them and reset the color
//					removeButton.setBackground(null);
//					removeButton.setEnabled(true);
//				}
//			}
//		});
	}

	/**
	 * Sets up all the buttons of the panel (addCheck, removeCheck, synchronize, save...)
	 */
	private void setUpButtons() {
		JPanel buttons = new JPanel();

//		// Add check
//		addButton = new JButton("Add new check");
//		addButton.addActionListener(this);
//		addButton.setIcon(new ImageIcon(addCheckIconPath));
//		buttons.add(addButton);
//
//		// Remove check
//		removeButton = new JButton("Remove selected");
//		removeButton.addActionListener(this);
//		removeButton.setIcon(new ImageIcon(removeCheckIconPath));
//		buttons.add(removeButton);
//
//		buttons.add(new JLabel("   |   "));
//
//		// Save model
//		saveButton = new JButton("Save model");
//		saveButton.addActionListener(this);
//		saveButton.setIcon(new ImageIcon(saveIconPath));
//		buttons.add(saveButton);
//
//		buttons.add(new JLabel("   |   "));
//
//		// Synchronize with Time Clock
//		syncButton = new JButton("Sync. with Time Clock");
//		syncButton.addActionListener(this);
//		syncButton.setIcon(new ImageIcon(syncIconPath));
//		buttons.add(syncButton);
//
//		buttons.add(new JLabel("   |   "));
//
		// Search bar
		searchBar = new JTextField(" Search bar");
		searchBar.setPreferredSize(new Dimension(200, 23));
		buttons.add(searchBar);
//
//		// Default look (disable some buttons)
//		removeButton.setBackground(Color.LIGHT_GRAY);
//		removeButton.setEnabled(false);

		add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * Sets up the filter panel, used to apply filters on the table
	 */
	private void setUpFilterPanel() {

		// Filter panel
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 12);
		JPanel filterPanel = new JPanel(flowLayout);
		filterPanel.setPreferredSize(new Dimension(298, 0));
		filterPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		// Initialization of filters list
		/*		0 = search bar
		  		1 = shop
		  		2 = item	*/
		for(int i=0; i<3; i++) {
			filters.add(RowFilter.regexFilter(""));
		}

		// Filter by employee
		filterPanel.add(new JLabel("Trier par magasin :"));

		// Uses a combo box of employee
		shopComboBox = new JComboBox<Shop>();
		shopComboBox.setPreferredSize(new Dimension(250, 25));
//		MainAppController.synchronizeEmployees(shopComboBox, false);
		filterPanel.add(shopComboBox);

		shopComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If no employee is selected, removes the filter
				if(shopComboBox.getSelectedItem() == null) {
					setFilter(1, "");
				}
				// Else, adds the employee as a new filter
				else {
					setFilter(1, shopComboBox.getSelectedItem().toString());
				}
			}
		});

		// Filter by department
		filterPanel.add(new JLabel("Trier par produit :"));

		// Uses a combo box of department
		itemComboBox = new JComboBox<Item>();
		itemComboBox.setPreferredSize(new Dimension(250, 25));
//		MainAppController.synchronizeDepartments(itemComboBox);
		filterPanel.add(itemComboBox);

		itemComboBox.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If no department is selected, removes the filter
				if(itemComboBox.getSelectedItem() == null) {
					setFilter(2, "");
				}
				// Else, adds the department as a new filter
				else {
					setFilter(2, itemComboBox.getSelectedItem().toString());
				}
			}
		});

		// Reset filters button
		resetButton = new JButton("Reset filters");
		resetButton.addActionListener(this);
		resetButton.setPreferredSize(new Dimension(250, 25));
		filterPanel.add(resetButton);

		this.add(new JScrollPane(filterPanel), BorderLayout.EAST);
	}

	/**
	 * Sets up search bar, which also act as a filter
	 */
	private void setUpSearchBar() {
		// Creates and apply a row sorter based on the model
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		// Only used to clear text "Search bar" on the first click
		searchBar.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				searchBar.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) { }
		});

		// Adds a document listener to the search bar
		searchBar.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				// If there's no text in the search bar, removes the filter
				if (searchBar.getText().trim().length() == 0) {
					setFilter(0, "");
				}
				// Else, adds the text as a new filter
				else {
					setFilter(0, searchBar.getText());
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (searchBar.getText().trim().length() == 0) {
					setFilter(0, "");
				}
				else {
					setFilter(0, searchBar.getText());
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

		});
	}

	/**
	 * Sets up cell renderer (icons late check, colors, text formatting...)
	 */
	private void setUpRenderers() {
//		table.setDefaultRenderer(Object.class, new EmptyCellRenderer());
//		table.getColumnModel().getColumn(2).setCellRenderer(new TimeCellRenderer(itemList));
//		table.getColumnModel().getColumn(5).setCellRenderer(new DurationCellRenderer());
	}

	/*===== METHODS =====*/

	/**
	 * This method is used to modify an existing filter
	 * @param i the index of the filter to modify
	 * @param text the new text used by the filter (to reset, set text to "")
	 */
	private void setFilter(int i, String text)
	{
		// Sets the filter 
		filters.set(i, RowFilter.regexFilter("(?i)" + text));
		// Update the row filter (tells it to use all filters with the method "andFilters")
		RowFilter<Object,Object> rowFilter = RowFilter.andFilter(filters);
		
		rowSorter.setRowFilter(rowFilter);
	} 

	/**
	 * This method is used to reset all the filters
	 */
	public void resetFilters() {
		shopComboBox.setSelectedItem(null); 
		itemComboBox.setSelectedItem(null); 
		searchBar.setText("");
	}

	/*===== ACTION LISTENERS =====*/

	/**
	 * Override of the actionPerformed method, is used to manage the effects of the buttons.
	 * @see CheckController
	 * @see MainAppController#saveModel
	 * @see MainAppController#syncWaitingChecks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
//		case "Save model" 			: MainAppController.saveModel(); break;
//		case "Add new check" 		: CheckController.addCheck(itemList); break;
//		case "Remove selected"  	: CheckController.removeCheck(itemList, table); break;
//		case "Sync. with Time Clock": MainAppController.syncWaitingChecks(); itemList.fireTableDataChanged(); break;
		case "Reset filters"		: this.resetFilters(); break;
		}
	}

}
