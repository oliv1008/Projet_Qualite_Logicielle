package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import controller.MainController;

/**
 * This class is used ad the model for the JTable of checks.
 * It contains all the methods needed to manage a table of checks.
 */
public class ItemList extends AbstractTableModel implements Serializable {

	private static final long serialVersionUID = -1625262976180949154L;

	/*===== ATTRIBUTES =====*/	
	private ArrayList<StockLine> stocks;
	private final String[] columns = {"Magasin", "Produit", "Quantité"};

	private ArrayList<Shop> shops = new ArrayList<Shop>();

	/*===== BUILDER =====*/	

	/**
	 * Default builder. 
	 * @param checks the ArrayList of checks contained in the company
	 */
	public ItemList() {
		stocks = new ArrayList<StockLine>();
				
		this.shops = MainController.getShopList();
		
		for(Shop s : shops) {
			for(Entry<Item, Integer> entry : s.getStock().entrySet()) {
				System.out.println(s + " " + entry.getKey() + " " + entry.getValue());
				stocks.add(new StockLine(s, 
						entry.getKey(), 
						entry.getValue()));
			}
		}
	}

	/*===== GETTERS AND SETTERS =====*/	

	/**
	 * Add a check to the model and update the tableModel
	 * @param check the department to add
	 */
	//	public void addCheck(Check check) {
	//		checks.add(check);
	//		fireTableRowsInserted(checks.size()-1, checks.size()-1);
	//	}

	/**
	 * Remove a check from the model and update the tableModel
	 * @param rowIndex the row index of the check to remove
	 */
	//	public void removeCheck(int rowIndex) {
	//		checks.remove(rowIndex);
	//		fireTableRowsDeleted(rowIndex, rowIndex);
	//	}

	/**
	 * Can be used to get a check depending of his place in the table
	 * @param rowIndex the row index of the check
	 * @return the check at that row index
	 */
	public StockLine getStockLine(int rowIndex) {
		return stocks.get(rowIndex);
	}

	/**
	 * Getter for the check list
	 * @return the current list of checks
	 */
	public ArrayList<StockLine> getStocks(){
		return stocks;
	}

	/*===== METHODS =====*/	

	@Override
	public int getRowCount() {
		return stocks.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return stocks.get(rowIndex).getShop();
		case 1:
			return stocks.get(rowIndex).getItem();
		case 2:
			return stocks.get(rowIndex).getQuantity();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if(value != null) {
			StockLine stock = stocks.get(rowIndex);
			switch(columnIndex) {
			default: break;
			}
		}
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case 0 : return Shop.class;
		case 1 : return Item.class;
		case 2 : return Integer.class;
		default : return Object.class;
		}
	}
}
