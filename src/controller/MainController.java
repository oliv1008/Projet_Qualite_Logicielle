package controller;

import view.main.MainWindow;

public class MainController {

	private static MainWindow view;
	
	public static void main(String[] args) {
		LoginController loginController = new LoginController();
	}
	
	public static void openMainView() {
		view = new MainWindow();
	}
	
}
