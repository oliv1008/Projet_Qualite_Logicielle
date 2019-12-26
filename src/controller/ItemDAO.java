package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import misc.ExceptionBlabla;
import misc.PermissionException;
import misc.Serialize;
import model.Item;
import model.Shop;
import model.User;

public class ItemDAO {

	private static final String itemsFilePath = "data/items.data";

	private static ArrayList<Item> items;

	/**
	 * Load the list of items from the filesystem
	 */
	@SuppressWarnings("unchecked")
	public static void loadItemFile() {
		try {
			items = (ArrayList<Item>)Serialize.load(itemsFilePath);
		} catch (FileNotFoundException e) {
			items = new ArrayList<Item>();
			populateItemList(items);
			System.out.println("[ItemDAO] Model file not found, creation of a new file");
		}	
	}

	/**
	 * Save the list of items to the filesystem
	 */
	public static void saveItemFile() {
		Serialize.save(items, itemsFilePath);
	}

	/**
	 * Return the list of items sold by the shops
	 * @return the list of items sold by the shops
	 */
	public static ArrayList<Item> getAllItems(){
		return items;
	}

	/**
	 * Add an item to the global item list, and to the stocks of each shop
	 * @param name
	 * @param description
	 * @param price
	 * @throw PermissionException
	 * @throw ExceptionBlabla
	 */
	public static void addItem(String name, String description, double price) throws PermissionException, ExceptionBlabla {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(isNameValid(name) && isDescriptionValid(description) && isPriceValid(price)) {
				
				Item item = new Item(name, description, price);

				items.add(item);

				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {
					s.getStock().put(item, 0);
				}
				
			}
			else {
				throw new ExceptionBlabla("Données non valides");
			}
		}
		else {
			throw new PermissionException("Vous devez être SUPER-ADMIN ou plus pour exécuter cette action");
		}
	}

	/**
	 * Delete an item from the global list, and from the stocks of each shop
	 * @param item the item to delete
	 * @throws PermissionException
	 * @throws ExceptionBlabla
	 */
	public static void deleteItem(Item item) throws PermissionException, ExceptionBlabla {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(item != null) {
				items.remove(item);

				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {

					s.getStock().remove(item);
				}
			}
			else {
				throw new ExceptionBlabla("Données non valides");
			}
		}
		else {
			throw new PermissionException("Vous devez être SUPER-ADMIN ou plus pour exécuter cette action");
		}
	}

	/**
	 * Modify the properties of an item in the global list, and in the stocks of each shop
	 * @param item the item to modify
	 * @param newName the new name
	 * @param newDescription the new description
	 * @param newPrice the new price
	 * @throws PermissionException
	 * @throws ExceptionBlabla
	 */
	public static void modifyItem(Item item, String newName, String newDescription, double newPrice) throws PermissionException, ExceptionBlabla {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(item != null && isNameValid(newName) && isDescriptionValid(newDescription) && isPriceValid(newPrice)) {
				
				items.get(items.indexOf(item)).setName(newName);
				items.get(items.indexOf(item)).setDescription(newDescription);
				items.get(items.indexOf(item)).setPrice(newPrice);

				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {
					Integer quantity = s.getStock().get(item);		// est ce que c'est bien de faire ça ?
					s.getStock().remove(item);
					s.getStock().put(new Item(newName, newDescription, newPrice), quantity);
				}
				
			}
			else {
				throw new ExceptionBlabla("Données non valides");
			}	
		}
		else {
			throw new PermissionException("Vous devez être SUPER-ADMIN ou plus pour exécuter cette action");
		}
	}

	/**
	 * Transfer an item from a shop to another
	 * @param item the item to transfer
	 * @param quantity the quantity to transfer
	 * @param from the shop of departure
	 * @param to the shop of arrival
	 * @throws PermissionException
	 * @throws ExceptionBlabla
	 */
	public static void transferItem(Item item, Integer quantity, Shop from, Shop to) throws PermissionException, ExceptionBlabla {
		// Permissions : (>= ADMIN) || (== SELLER && same shop)
		if(MainController.getCurrentUser().getPrivilege() >= User.ADMIN ||
				(MainController.getCurrentUser().getPrivilege() == User.SELLER
				&& MainController.getCurrentUser().getShop().equals(from))) {
			// Coherence : (quantity <= stock) && (shop_from != shop_to)
			if(item != null && from != null && to != null && quantity <= from.getStock().get(item) && !from.equals(to)) {

				int fromQuantity = from.getQuantity(item);
				int toQuantity = to.getQuantity(item);

				from.setQuantity(item, fromQuantity - quantity);
				to.setQuantity(item, toQuantity + quantity);

			}
			else {
				throw new ExceptionBlabla("Données non valides");
			}

		}
		else {
			throw new PermissionException("Vous devez être VENDEUR ou plus pour exécuter cette action");
		}
	}

	/**
	 * Change the stock of an item
	 * @param shop the shop concerned by the change
	 * @param item the item to change
	 * @param quantity the new stock
	 * @throws PermissionException
	 * @throws ExceptionBlabla
	 * TODO : déplacer cette méthode dans ShopDAO ?
	 */
	public static void changeStock(Shop shop, Item item, Integer quantity) throws PermissionException, ExceptionBlabla {
		// Permissions : (>= ADMIN) || (== SELLER && same shop)
		if(MainController.getCurrentUser().getPrivilege() >= User.ADMIN ||
				(MainController.getCurrentUser().getPrivilege() == User.SELLER
				&& MainController.getCurrentUser().getShop().equals(shop))) {
			// Coherence : (quantity >= 0)
			if(shop != null && item != null && quantity >= 0) {
				
				shop.setQuantity(item, quantity);
				
			}
			else {
				throw new ExceptionBlabla("Données non valides");
			}
		}
		else {
			throw new PermissionException("Vous devez être VENDEUR ou plus pour exécuter cette action");
		}
	}

	/**
	 * Check if the name of an item is valid
	 * @param name the name to check
	 * @return true if valid, else false
	 */
	public static boolean isNameValid(String name) {
		if(name == null) return false;
		return name.matches("[A-Za-z]+");
	}

	/**
	 * Check if the description of an item is valid
	 * @param description the description to check
	 * @return true if valid, else false
	 */
	public static boolean isDescriptionValid(String description) {
		if(description == null) return false;
		return !description.isEmpty();
	}

	/**
	 * Check if the price of an item is valid
	 * @param price the price to check
	 * @return true if valid, else false
	 */
	public static boolean isPriceValid(double price) {
		String s = String.valueOf(price);
		return price > 0 && (s.substring(s.indexOf('.')+1)).length() <= 2;
	}

	//// TMP ////
	private static void populateItemList(ArrayList<Item> items) {
		items.add(new Item("Pomme", "c'est bon", 2.5));
		items.add(new Item("Fraise", "c'est rouge", 4));
		items.add(new Item("Banane", "c'est long", 1.3));
		items.add(new Item("Tractopelle", "c'est cool", 126));
	}
}
