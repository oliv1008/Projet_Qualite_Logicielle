package controller;

import java.awt.event.WindowEvent;

import misc.BCrypt;
import view.login.LoginWindow;

public class LoginController {

	public final static String BAD_LOGIN = "bad login";
	
	private static LoginWindow view;
	
	public LoginController() {
		view = new LoginWindow();
	}
	
	public static void login(String mail, char[] input) throws Exception {
		if(isPasswordCorrect(mail, input)) {
			view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
			MainController.openMainView();
			MainController.setTitle("Logged as <" + mail + ">");
		}
		else {
			throw new Exception(BAD_LOGIN);
		}
	}
	
	public static boolean isPasswordCorrect(String mail, char[] input) {
		String inputStr = new String(input);
		String hash = UserDAO.getHashByMail(mail);
		return BCrypt.checkpw(inputStr, hash);
	}
}
