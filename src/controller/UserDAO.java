package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import misc.Serialize;
import model.Shop;
import model.User;

public class UserDAO {

	private static final String userFilePath = "data/users.data";
	
	private static ArrayList<User> users;

	/**
	 * Load the list of users from the filesystem
	 */
	@SuppressWarnings("unchecked")
	public static void loadUserFile() {
		try {
			users = (ArrayList<User>)Serialize.load(userFilePath);
		} catch (FileNotFoundException e) {
			users = new ArrayList<User>();
			// Add the super-admin user (mail="admin", password="admin")
			UserDAO.addUser(new User("admin", "admin", new Shop("Steam", "Steam"), "admin", "admin", User.SUPER_ADMIN));
			populateUserList(users);
			System.out.println("[UserDAO] Model file not found, creation of a new file");
		}
	}

	/**
	 * Save the list of users to the filesystem
	 */
	public static void saveUserFile() {
		Serialize.save(users, userFilePath);
	}

	/**
	 * Add a new user
	 * @param firstName
	 * @param lastName
	 * @param shop
	 * @param mail
	 * @param password
	 * @param privilege
	 */
	public static void addUser(String firstName, String lastName, Shop shop, String mail, char[] password, int privilege) {
		users.add(new User(firstName, lastName, shop, mail, new String(password), privilege));
	}
	
	/**
	 * Add a new user
	 * @param user
	 */
	public static void addUser(User user) {
		users.add(user);
	}

	/**
	 * Search for a user of a given mail
	 * @param mail the mail of the user
	 * @return the user of the given mail, or null if not found
	 */
	public static User getUserByMail(String mail) {
		for(User u : users) {
			if(u.getMail().equals(mail)) {
				return u;
			}
		}
		return null;
	}
	
	///// TMP /////
	private static void populateUserList(ArrayList<User> users) {
		addUser("Tom", "Suchel", ShopDAO.getShopByName("Lidl"), "tom.suchel@gmail.com", (new String("tom")).toCharArray(), User.SUPER_ADMIN);
		addUser("Olivier", "Millochau", ShopDAO.getShopByName("Carrouf"), "o.millochau@gmail.com", (new String("crevette")).toCharArray(), User.SELLER);
		addUser("Sam", "Souville", ShopDAO.getShopByName("Monop"), "samsam@gmail.com", (new String("sam")).toCharArray(), User.RESTRICTED_SELLER);
	}


}
