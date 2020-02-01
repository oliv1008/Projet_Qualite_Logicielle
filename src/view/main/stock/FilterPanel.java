package view.main.stock;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.MainController;
import view.misc.ItemComboBox;
import view.misc.ShopComboBox;

/**
 * Panel used to filters the content of the JTable
 */
public class FilterPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	private JTable table;

	private ItemComboBox itemFilterComboBox;			/** ComboBox to filter by item **/	
	private ShopComboBox shopFilterComboBox;			/** ComboBox to filter by shop **/
	private JTextField searchBar;						/** Search bar **/
	private JButton resetButton;						/** Reset button for filters **/

	private TableRowSorter<TableModel> rowSorter;
	private ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();

	/*===== BUILDER =====*/
	public FilterPanel(JTable table) {
		this.table = table;

		// Filter panel
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		setPreferredSize(new Dimension(300, 0));

		JLabel filterPanelLabel = new JLabel("Filtrage des stocks", SwingConstants.CENTER);
		filterPanelLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		add(filterPanelLabel);

		// Initialization of filters list
		/*		0 = search bar
				1 = shop
				2 = item	*/
		for(int i=0; i<3; i++) {
			filters.add(RowFilter.regexFilter(""));
		}

		// Creates and apply a row sorter based on the model
		rowSorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);

		// Filter by employee
		add(new JLabel("Trier par magasin :"));

		// Uses a combo box of employee
		shopFilterComboBox = new ShopComboBox();
		shopFilterComboBox.setPreferredSize(new Dimension(260, 30));
		if(MainController.getCurrentUser().getPrivilege() < 4) {
			shopFilterComboBox.setSelectedItem(MainController.getCurrentUser().getShop());
			setFilter(1, shopFilterComboBox.getSelectedItem().toString());
		}
		shopFilterComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If no employee is selected, removes the filter
				if(shopFilterComboBox.getSelectedItem() == null) {
					setFilter(1, "");
				}
				// Else, adds the employee as a new filter
				else {
					setFilter(1, shopFilterComboBox.getSelectedItem().toString());
				}
			}
		});
		add(shopFilterComboBox);

		// Filter by department
		add(new JLabel("Trier par produit :"));

		// Uses a combo box of department
		itemFilterComboBox = new ItemComboBox();
		itemFilterComboBox.setPreferredSize(new Dimension(260, 30));
		itemFilterComboBox.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If no department is selected, removes the filter
				if(itemFilterComboBox.getSelectedItem() == null) {
					setFilter(2, "");
				}
				// Else, adds the department as a new filter
				else {
					setFilter(2, itemFilterComboBox.getSelectedItem().toString());
				}
			}
		});
		add(itemFilterComboBox);

		add(new JLabel("Barre de recherche :"));

		// Search bar
		searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(260, 30));
		add(searchBar);

		// Reset filters button
		resetButton = new JButton("Réinitialiser les filtres");
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				shopFilterComboBox.setSelectedItem(null);
				itemFilterComboBox.setSelectedItem(null);
				searchBar.setText("");
			}
		});
		resetButton.setPreferredSize(new Dimension(260, 30));
		add(resetButton);

		setUpSearchBar();
	}

	private void setUpSearchBar() {
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
	 * This method is used to modify an existing filter
	 * @param i the index of the filter to modify
	 * @param text the new text used by the filter (to reset, set text to "")
	 */
	private void setFilter(int i, String text) {
		table.clearSelection();
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
		shopFilterComboBox.setSelectedItem(null); 
		itemFilterComboBox.setSelectedItem(null); 
		searchBar.setText("");
	}

	public void refresh() {
		shopFilterComboBox.reload(); 
		itemFilterComboBox.reload();
	}
}
