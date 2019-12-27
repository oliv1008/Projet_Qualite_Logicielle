package controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import misc.CoherenceException;
import misc.PermissionException;
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
			users.add(new User("admin", "admin", new Shop("Polytech", ""), "admin", "admin", User.SUPER_ADMIN));
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
	 * Return the list of users
	 * @return the list of users
	 */
	public static ArrayList<User> getAllUsers() {
		return users;
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
	public static void addUser(String firstName, String lastName, Shop shop, String mail, char[] password, int privilege) throws PermissionException, CoherenceException {
		// Coherence
		if(isNameValid(firstName) && isNameValid(lastName) && isShopValid(shop) && isMailValid(mail) && isPasswordValid(password)) {
			// Permissions (== SUPER_ADMIN) || (== ADMIN && shop == current.shop) || (current == null)
			User currentUser = MainController.getCurrentUser();
			if(currentUser == null) {
				users.add(new User(firstName, lastName, shop, mail, new String(password), privilege));
			}
			else if(currentUser.getPrivilege() == User.SUPER_ADMIN ||
					(currentUser.getPrivilege() == User.ADMIN && currentUser.getShop().equals(shop))) {
				users.add(new User(firstName, lastName, shop, mail, new String(password), privilege));
			}
			else {
				throw new PermissionException("Vous n'avez pas les permissions requises pour exécuter cette action");
			}
		}
		else {
			throw new CoherenceException("Données non valides");
		}
	}

	/**
	 * Delete an user
	 * @param user the user to delete
	 * @throws PermissionException
	 * @throws CoherenceException
	 */
	public static void deleteUser(User user) throws PermissionException, CoherenceException {
		// Coherence
		if(user != null) {
			// Permissions : user != "admin" && ((==SUPER_ADMIN) || (==ADMIN && user.shop = current.shop)) && user != current
			User current = MainController.getCurrentUser();
			if(!user.equals(UserDAO.getUserByMail("admin")) && !user.equals(current) &&
					(current.getPrivilege() == User.SUPER_ADMIN || 
					(current.getPrivilege() == User.ADMIN && current.getShop().equals(user.getShop())))) {
				
				if (JOptionPane.showConfirmDialog(null, "Etes vous sur de supprimer le compte de ["+user.getMail()+"] ?", "Attention", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				users.remove(user);
				}

			}
			else {
				throw new PermissionException("Vous n'avez pas les permissions requises pour exécuter cette action");
			}
		}
		else {
			throw new CoherenceException("Données non valides");
		}
	}

	public static void deleteOwnAccount()  throws PermissionException, CoherenceException {
		// Permissions : user != "admin"
		if(!(MainController.getCurrentUser()).equals(UserDAO.getUserByMail("admin"))) {

			if (JOptionPane.showConfirmDialog(null, "Votre compte va être supprimé. Etes vous sur ?", "Attention", 
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				users.remove(MainController.getCurrentUser());
				MainController.setCurrentUser(null);
				MainController.getView().dispose();
				new LoginController();
			} 
		}
		else {
			throw new PermissionException("Vous n'avez pas les permissions requises pour exécuter cette action");
		}
	}

	/**
	 * Modify the properties of an user
	 * @param user
	 * @param firstName
	 * @param lastName
	 * @param shop
	 * @param mail
	 * @param password
	 * @param privilege
	 * @throws PermissionException
	 * @throws CoherenceException
	 */
	public static void modifyUser(User user, String firstName, String lastName, Shop shop, String mail, char[] password, int privilege) throws PermissionException, CoherenceException {
		System.out.println(user + " | " + firstName + " | " + lastName + " | " + shop + " | " + mail + " | " + new String(password) + " | " + privilege);
		// Coherence
		if(user != null && isNameValid(firstName) && isNameValid(lastName) && isShopValid(shop)) {
			// Permissions : (==SUPER_ADMIN) || (...)
			User current = MainController.getCurrentUser();
			if(current.getPrivilege() == User.SUPER_ADMIN || 
					(current.getPrivilege() == User.ADMIN && 
					 current.getShop().equals(user.getShop()) && 
					 current.getShop().equals(shop) && 
					 user.getPrivilege() < User.ADMIN && 
					 privilege <= User.ADMIN)) {

				int index = users.indexOf(user);
				users.get(index).setFirstName(firstName);
				users.get(index).setLastName(lastName);
				users.get(index).setShop(shop);
//				users.get(index).setMail(mail);							// est ce qu'on dit qu'on peut changer le mail 
//				users.get(index).setHashedPwd(new String(password));	// et le mdp ?
				users.get(index).setPrivilege(privilege);
				
			}
			else {
				throw new PermissionException("Vous n'avez pas les permissions requises pour exécuter cette action");
			}
		}
		else {
			throw new CoherenceException("Données non valides");
		}
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

	/**
	 * Check if the name of an user is valid
	 * @param name the name to check
	 * @return true if valid, else false
	 * TODO : ça serait cool de gérer les accents/les tirets... (idem pour tout le reste)
	 */
	public static boolean isNameValid(String name) {
		if(name == null) return false;
		return name.matches("[A-Za-z]+");
	}

	/**
	 * Check if the shop of an user is valid
	 * @param shop the shop to check
	 * @return true if valid, else false
	 */
	public static boolean isShopValid(Shop shop) {
		if(shop == null) return false;
		return ShopDAO.getAllShops().contains(shop);
	}

	/**
	 * Check if the mail of an user is valid
	 * @param mail the mail to check
	 * @return true if valid, else false
	 */
	public static boolean isMailValid(String mail) {
		if(mail == null) return false;
		for(User u : users) {
			if(u.getMail().equals(mail)) {
				return false;
			}
		}
		return mail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+[.][a-z]+$");
	}

	/**
	 * Check if the password of an user is valid
	 * @param password the password to check
	 * @return true if valid, else false
	 */
	public static boolean isPasswordValid(char[] password) {
		if(password == null) return false;
		return password.length > 0;
	}

	///// TMP /////
	private static void populateUserList(ArrayList<User> users) {
		try {
			addUser("Tom", 		"Suchel", 		ShopDAO.getShopByName("Lidl"), 		"tom.suchel@gmail.com", 	(new String("tom")).toCharArray(), 		User.SUPER_ADMIN);
			addUser("Olivier", 	"Millochau", 	ShopDAO.getShopByName("Carrouf"), 	"o.millochau@gmail.com", 	(new String("crevette")).toCharArray(), User.SELLER);
			addUser("Sam", 		"Souville", 	ShopDAO.getShopByName("Monop"), 	"samsam@gmail.com", 		(new String("sam")).toCharArray(), 		User.RESTRICTED_SELLER);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


}
