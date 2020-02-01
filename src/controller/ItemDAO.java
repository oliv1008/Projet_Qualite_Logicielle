package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import misc.CoherenceException;
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
	 * @param reset if true, create a new database
	 */
	@SuppressWarnings("unchecked")
	public static void loadItemFile(boolean reset) {
		if(reset) {
			items = new ArrayList<Item>();
		}
		else {
			try {
				items = (ArrayList<Item>)Serialize.load(itemsFilePath);
			} catch (FileNotFoundException e) {
				items = new ArrayList<Item>();
				System.out.println("[ItemDAO] Model file not found, creation of a new file");
			}
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
	public static void addItem(String name, String description, double price) throws PermissionException, CoherenceException {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(isNameValid(name) && isDescriptionValid(description) && isPriceValid(price)) {

				Item item = new Item(name, description, price);

				// We add the item to the item list
				items.add(item);

				// And then we add it to each shop
				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {
					s.getStock().put(item, 0);
				}

			}
			else {
				throw new CoherenceException("Données non valides");
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
	 * @throws CoherenceException
	 */
	public static void deleteItem(Item item) throws PermissionException, CoherenceException {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(item != null) {

				// We delete the item from the item list
				items.remove(item);

				// And then we delete it from each shop
				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {
					s.getStock().remove(item);
				}
			}
			else {
				throw new CoherenceException("Données non valides");
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
	 * @throws CoherenceException
	 */
	public static void modifyItem(Item item, String newName, String newDescription, double newPrice) throws PermissionException, CoherenceException {
		// Permissions : (== SUPER_ADMIN)
		if(MainController.getCurrentUser().getPrivilege() == User.SUPER_ADMIN) {
			// Coherence
			if(item != null && isNameValid(newName) && isDescriptionValid(newDescription) && isPriceValid(newPrice)) {
				
				// We modify the item in each shop
				ArrayList<Shop> shops = ShopDAO.getAllShops();
				for(Shop s : shops) {
					Integer quantity = s.getQuantity(item);
					System.out.println("Shop="+s+", Quantity="+quantity);
					s.getStock().remove(item);
					s.getStock().put(new Item(newName, newDescription, newPrice), quantity);
				}
				
				// And then the modify it in the item list
				int index = items.indexOf(item);
				items.get(index).setName(newName);
				items.get(index).setDescription(newDescription);
				items.get(index).setPrice(newPrice);

			}
			else {
				throw new CoherenceException("Données non valides");
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
	 * @throws CoherenceException
	 */
	public static void transferItem(Item item, Integer quantity, Shop from, Shop to) throws PermissionException, CoherenceException {
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
				throw new CoherenceException("Données non valides");
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
	 * @throws CoherenceException
	 * TODO : déplacer cette méthode dans ShopDAO ?
	 */
	public static void changeStock(Shop shop, Item item, Integer quantity) throws PermissionException, CoherenceException {
		// Permissions : (>= ADMIN) || (== SELLER && same shop)
		if(MainController.getCurrentUser().getPrivilege() >= User.ADMIN ||
				(MainController.getCurrentUser().getPrivilege() == User.SELLER
				&& MainController.getCurrentUser().getShop().equals(shop))) {
			// Coherence : (quantity >= 0)
			if(shop != null && item != null && quantity >= 0) {

				shop.setQuantity(item, quantity);

			}
			else {
				throw new CoherenceException("Données non valides");
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

}
