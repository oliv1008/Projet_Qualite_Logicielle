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

	public static void login(String mail, char[] input) throws Exception {
		if(isPasswordCorrect(mail, input)) {
			view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
			MainController.openMainView(UserDAO.getUserByMail(mail));
		}
		else {
			throw new Exception(BAD_LOGIN);
		}
	}

	public static boolean isPasswordCorrect(String mail, char[] input) {
		User user = UserDAO.getUserByMail(mail);
		if(user != null) {
			String inputStr = new String(input);
			String hash = user.getHashedPwd();
			return BCrypt.checkpw(inputStr, hash);
		}
		else {
			return false;
		}
	}

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
