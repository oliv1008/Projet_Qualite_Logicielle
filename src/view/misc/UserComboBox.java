package view.misc;

import java.util.ArrayList;

import javax.swing.JComboBox;

import controller.UserDAO;
import model.User;

public class UserComboBox extends JComboBox<User> {
	
	public UserComboBox() {
		super();
		reload();
	}
	
	public void reload() {
		removeAllItems();
		ArrayList<User> users = UserDAO.getAllUsers();
		addItem(null);
		for(User u : users) {
			addItem(u);
		}
	}

}
