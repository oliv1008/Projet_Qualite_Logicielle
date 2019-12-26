package controller;

import java.util.ArrayList;

import model.Shop;
import model.User;
import view.main.MainWindow;

public class MainController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;

	private static ArrayList<User> users;	// Users array
	private static ArrayList<Shop> shops;	// Shops array (contains the stocks for each shops)
	
	private static User currentUser;

	public static void main(String[] args) {
		ItemDAO.loadItemFile();
		ShopDAO.loadShopFile();	
		UserDAO.loadUserFile();

		LoginController loginController = new LoginController();
	
//		openMainView(UserDAO.getUserByMail("admin"));
	}

	/*===== BUILDER =====*/
	public static void openMainView(User user) {
		currentUser = user;
		view = new MainWindow();
		view.setTitle("Connecté en tant que <" + user.getMail() + "> (privilege=" + user.getPrivilege() + ")");
	}

	/*===== GETTERS AND SETTERS =====*/
	public static ArrayList<User> getUsers() {
		return users;
	}

	public static ArrayList<Shop> getShops() {
		return shops;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}

	/*===== METHODS =====*/
	public static void onExit() {
		ItemDAO.saveItemFile();
		ShopDAO.saveShopFile();
		UserDAO.saveUserFile();
	}
	
	public static void refreshDisplay() {
		view.getStockPanel().refresh();
	}

}
