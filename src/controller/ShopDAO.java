package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import misc.Serialize;
import model.Item;
import model.Shop;

public class ShopDAO {

	private static final String shopFilePath = "data/shops.data";

	private static ArrayList<Shop> shops;

	/**
	 * Load the list of shops from the filesystem
	 * @param reset if true, create a new database
	 */
	@SuppressWarnings("unchecked")
	public static void loadShopFile(boolean reset) {
		if(reset) {
			shops = new ArrayList<Shop>();
		}
		else {
			try {
				shops = (ArrayList<Shop>)Serialize.load(shopFilePath);
			} catch (FileNotFoundException e) {
				shops = new ArrayList<Shop>();
				// We fill the shop list, as it's impossible to add shops in the main app.
				populateShopList(shops);
				System.out.println("[ShopDAO] Model file not found, creation of a new file");
			}
		}
	}

	/**
	 * Save the list of shops to the filesystem
	 */
	public static void saveShopFile() {
		Serialize.save(shops, shopFilePath);
	}

	/**
	 * Return the list of shops
	 * @return the list of shops
	 */
	public static ArrayList<Shop> getAllShops() {
		return shops;
	}

	/**
	 * Search for a shop of a given name
	 * @param name the name of the shop
	 * @return the shop of the given name, or null if not found
	 */
	public static Shop getShopByName(String name) {
		for(Shop s : shops) {
			if(s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	private static void populateShopList(ArrayList<Shop> shops) {
		shops.add(new Shop("Tours", "64 Avenue Jean Portalis"));
		shops.add(new Shop("Blois", "123 Rue du Pont"));
		shops.add(new Shop("Paris", "41 Boulevard de la République"));

		ArrayList<Item> items = ItemDAO.getAllItems();
		for(Shop s : shops) {
			for(Item i : items) {
				s.setQuantity(i, 0);
			}	
		}
	}
}
