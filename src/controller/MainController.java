package controller;

import model.User;
import view.main.MainWindow;

public class MainController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;

	private static User currentUser = null;

	public static void main(String[] args) {
		ItemDAO.loadItemFile(false);	//
		ShopDAO.loadShopFile(false);	// false = don't reset the database
		UserDAO.loadUserFile(false);	//

		new LoginController();
	}

	/*===== BUILDER =====*/
	public static void openMainView(User user) {
		setCurrentUser(user);
		view = new MainWindow();
		view.setTitle("Connecté en tant que <" + user.getMail() + "> (privilege=" + user.getPrivilege() + ")");
	}

	/*===== GETTERS AND SETTERS =====*/
	public static MainWindow getView() {
		return view;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	/*===== METHODS =====*/
	public static void onExit() {
		ItemDAO.saveItemFile();
		ShopDAO.saveShopFile();
		UserDAO.saveUserFile();
	}
	
	public static void refreshDisplay() {
		view.getStockPanel().refresh();
		view.getUsersPanel().refresh();
	}

}
