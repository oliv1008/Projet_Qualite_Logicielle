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
	 */
	@SuppressWarnings("unchecked")
	public static void loadShopFile() {
		try {
			shops = (ArrayList<Shop>)Serialize.load(shopFilePath);
		} catch (FileNotFoundException e) {
			shops = new ArrayList<Shop>();
			populateShopList(shops);
			System.out.println("[ShopDAO] Model file not found, creation of a new file");
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
	
	//// TMP ////
	public static void populateShopList(ArrayList<Shop> shops) {
		shops.add(new Shop("Lidl", "26 Rue de la discount"));
		shops.add(new Shop("Carrouf", "123 Rue de en vrai ça passe"));
		shops.add(new Shop("Monop", "41 Rue de la douille"));
		
		ArrayList<Item> items = ItemDAO.getAllItems();
		for(Shop s : shops) {
			for(Item i : items) {
				s.getStock().put(i, 0);
			}	
		}
	}
}
