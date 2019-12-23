package view.misc;

import java.util.ArrayList;

import javax.swing.JComboBox;

import controller.ShopDAO;
import model.Shop;

public class ShopComboBox extends JComboBox<Shop> {

	public ShopComboBox() {
		super();
		reload();
	}

	public void reload() {
		removeAllItems();
		ArrayList<Shop> shops = ShopDAO.getAllShops();
		addItem(null);
		for(Shop s : shops) {
			addItem(s);
		}
	}
}
