package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import controller.ShopDAO;

/**
 * This class is used ad the model for the JTable of checks.
 * It contains all the methods needed to manage a table of checks.
 */
public class ItemList extends AbstractTableModel implements Serializable {

	private static final long serialVersionUID = -1625262976180949154L;

	/*===== ATTRIBUTES =====*/	
	private ArrayList<StockLine> stocks;
	private final String[] columns = {"Magasin", "Produit", "Quantité"};

	private ArrayList<Shop> shops;

	/*===== BUILDER =====*/	

	/**
	 * Default builder. 
	 * @param checks the ArrayList of checks contained in the company
	 */
	public ItemList() {
		rebuildModel();
	}
	
	public void rebuildModel() {
		stocks = new ArrayList<StockLine>();

		shops = ShopDAO.getAllShops();

		for(Shop s : shops) {
			for(Entry<Item, Integer> entry : s.getStock().entrySet()) {
				stocks.add(new StockLine(s, entry.getKey()));
			}
		}
		
	 	fireTableDataChanged();
	}

	/*===== GETTERS AND SETTERS =====*/	

	/**
	 * Add a check to the model and update the tableModel
	 * @param check the department to add
	 */
	public void addStockLine(StockLine line) {
		stocks.add(line);
		fireTableRowsInserted(stocks.size()-1, stocks.size()-1);
	}

	/**
	 * Remove a check from the model and update the tableModel
	 * @param rowIndex the row index of the check to remove
	 */
	public void removeStockLine(int rowIndex) {
		stocks.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

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
			case 2 : stock.setQuantity((Integer)value); break;
			default: break;
			}
			fireTableRowsUpdated(rowIndex, rowIndex);
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
