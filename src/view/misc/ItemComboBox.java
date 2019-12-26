package view.misc;

import java.util.ArrayList;

import javax.swing.JComboBox;

import controller.ItemDAO;
import model.Item;

public class ItemComboBox extends JComboBox<Item> {

	public ItemComboBox() {
		super();
		reload();
	}
	
	public void reload() {
		removeAllItems();
		ArrayList<Item> items = ItemDAO.getAllItems();
		addItem(null);
		for(Item i : items) {
			addItem(i);
		}
	}
}
