package controller;

import java.util.ArrayList;
import java.util.HashMap;

import misc.BCrypt;
import model.Item;
import model.Shop;
import model.User;
import view.main.MainWindow;

public class MainController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;

	private static ArrayList<User> users;
	private static ArrayList<Shop> shops;

	public static void main(String[] args) {
		LoginController loginController = new LoginController();

		users = new ArrayList<User>();
		String hashed = BCrypt.hashpw("admin", BCrypt.gensalt());
		users.add(new User("admin", "admin", new Shop(), "admin", hashed, User.SUPER_ADMIN));
	}

	/*===== BUILDER =====*/
	public static void openMainView() {
		view = new MainWindow();
	}

	/*===== GETTERS AND SETTERS =====*/
	public static ArrayList<User> getUsers() {
		return users;
	}

	public static ArrayList<Shop> getShops() {
		return shops;
	}

	public static void setTitle(String title) {
		view.setTitle(title);
	}

	/*===== METHODS =====*/
	/**
	 * TMP
	 * @return
	 */
	public static ArrayList<Shop> getShopList(){
		ArrayList<Shop> shops = new ArrayList<Shop>();
		HashMap<Item, Integer> stock = new HashMap<Item, Integer>();
		stock.put(new Item("Pomme", "c'est bon", 1.5), 3);	
		shops.add(new Shop("Lidl", "123 rue chépaou", stock));
		return shops;
	}
	
	

}
