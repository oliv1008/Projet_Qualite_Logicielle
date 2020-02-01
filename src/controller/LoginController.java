package controller;

import java.awt.event.WindowEvent;

import misc.BCrypt;
import model.Shop;
import model.User;
import view.login.LoginWindow;

public class LoginController {

	public final static String BAD_LOGIN = "bad login";
	public final static String BAD_SIGNUP = "bad signup";

	private static LoginWindow view;

	public LoginController() {
		view = new LoginWindow();
	}

	/**
	 * This method is used to login to the main app
	 * @param mail the mail typed by the user
	 * @param input the password typed by the user 
	 * @throws Exception if the password don't match the mail
	 */
	public static void login(String mail, char[] input) throws Exception {
		// If the password is correct
		if(isPasswordCorrect(mail, input)) {
			view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));	// We close the login window
			MainController.openMainView(UserDAO.getUserByMail(mail));				// And we open the main window
		}
		else {
			throw new Exception(BAD_LOGIN);
		}
	}

	/**
	 * This method is used to know if a password is correct or not
	 * @param mail the mail that should be linked with the password
	 * @param input the password
	 * @return true if the password is correct, else false
	 */
	public static boolean isPasswordCorrect(String mail, char[] input) {
		User user = UserDAO.getUserByMail(mail);	// We try to recover the user linked to the mail
		if(user != null) {							// If it exist
			String inputStr = new String(input);
			String hash = user.getHashedPwd();
			return BCrypt.checkpw(inputStr, hash);	// We check if the password is correct
		}
		else {
			return false;
		}
	}

	/**
	 * This method is used to sign up to the application
	 * @param firstName
	 * @param lastName
	 * @param shop
	 * @param mail
	 * @param password
	 * @throws Exception
	 */
	public static void signup(String firstName, String lastName, Shop shop, String mail, char[] password) throws Exception {
		try {
			UserDAO.addUser(firstName, lastName, shop, mail, password, User.SELLER);
			view.displaySigninPanel();
		}
		catch(Exception e) {
			throw new Exception(BAD_SIGNUP);
		}
	}
}
