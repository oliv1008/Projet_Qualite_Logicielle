package controller;

import java.awt.event.WindowEvent;
import java.util.Arrays;

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
		}
		else {
			throw new Exception(BAD_LOGIN);
		}
	}
	
	public static boolean isPasswordCorrect(String mail, char[] input) {
	    boolean isCorrect = true;
	    
	    char[] correctPassword = {};	// récuperer le mot de passe avec le DAO et le déchiffrer

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals(input, correctPassword);
	    }

	    // Zero out the password.		<--- vérifier si cette méthode efface pas le mdp dans la BDD
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}
}
