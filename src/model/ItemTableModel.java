package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import controller.ShopDAO;

public class ItemTableModel extends AbstractTableModel implements Serializable {

	private static final long serialVersionUID = -1625262976180949154L;

	/*===== ATTRIBUTES =====*/	
	private ArrayList<LineData> stocks;
	private final String[] columns = {"Magasin", "Produit", "Quantité"};

	private ArrayList<Shop> shops;

	/*===== BUILDER =====*/	

	public ItemTableModel() {
		rebuildModel();
	}
	
	public void rebuildModel() {
		stocks = new ArrayList<LineData>();

		// We recover the list of shops
		shops = ShopDAO.getAllShops();

		// For each shops, we add a new line for each item it has in its stocks
		for(Shop s : shops) {
			for(Entry<Item, Integer> entry : s.getStock().entrySet()) {
				stocks.add(new LineData(s, entry.getKey()));
			}
		}
		
	 	fireTableDataChanged();
	}

	/*===== GETTERS AND SETTERS =====*/	

	public void addStockLine(LineData line) {
		stocks.add(line);
		fireTableRowsInserted(stocks.size()-1, stocks.size()-1);
	}

	public void removeStockLine(int rowIndex) {
		stocks.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public LineData getStockLine(int rowIndex) {
		return stocks.get(rowIndex);
	}

	public ArrayList<LineData> getStocks(){
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
			LineData stock = stocks.get(rowIndex);
			switch(columnIndex) {
			case 2 : stock.setQuantity((Integer)value); break;
			default: break;
			}
			fireTableRowsUpdated(rowIndex, rowIndex);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
