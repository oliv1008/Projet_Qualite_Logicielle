package controller;

import java.util.ArrayList;

import model.User;

public class UserDAO {

	public static String getHashByMail(String mail) {
		String hash = "";
		
		ArrayList<User> users = MainController.getUsers();
		for(User u : users) {
			if(u.getMail().equals(mail)) {
				return u.getPassword();
			}
		}
		
		return null;
	}
}
