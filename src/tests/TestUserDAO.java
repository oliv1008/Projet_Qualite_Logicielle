package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ItemDAO;
import controller.MainController;
import controller.ShopDAO;
import controller.UserDAO;
import misc.CoherenceException;
import misc.PermissionException;
import model.Shop;
import model.User;

class TestUserDAO {

	private static Shop shop1;
	private static Shop shop2;
	private static User uSuperAdmin;
	private static User uAdmin;
	private static User uSeller1;

	@BeforeAll
	public static void init() {
		shop1 = new Shop("Decatest", "142 rue jean boyer");
		shop2 = new Shop("Go Test", "26 rue de junit");

		uSuperAdmin = new User("admin", "admin", shop1, "mailsuperadmin", "password", User.SUPER_ADMIN);
		uAdmin = new User("paul", "pierre", shop1, "mailadmin", "password", User.ADMIN);
		uSeller1 = new User("jean1", "pierre1", shop1, "mailSeller1", "password", User.SELLER);	
	}

	@BeforeEach
	public void resetDatabase() {
		MainController.setCurrentUser(uSuperAdmin);
		ItemDAO.loadItemFile(true);
		ShopDAO.loadShopFile(true);	
		UserDAO.loadUserFile(true);
		ShopDAO.getAllShops().add(shop1);
		ShopDAO.getAllShops().add(shop2);
	}

	@Test
	void testGetAllUsers() {
		assertNotNull(UserDAO.getAllUsers());
		assertFalse(UserDAO.getAllUsers().isEmpty());
	}

	@Test
	void testAddUser() {
		// We create a password for the tests
		char[] pwd = (new String("password")).toCharArray();
		
		// Incorrect fields ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			UserDAO.addUser("jean02", "pierre", null, "mail", pwd, User.ADMIN);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// A seller can't add an user ---> should fail
		MainController.setCurrentUser(uSeller1);
		try {
			UserDAO.addUser("jean", "pierre", shop1, "jp@mail.fr", pwd, User.SELLER);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't add an user in another show than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.addUser("jean", "pierre", shop2, "jp@mail.fr", pwd, User.SELLER);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't add an user with a bigger privilege than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.addUser("jean", "pierre", shop1, "jp@mail.fr", pwd, User.SUPER_ADMIN);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uAdmin);
		try {
			User u = new User("jean", "pierre", shop1, "jp@mail.fr", "password", User.SELLER);
			UserDAO.addUser(u.getFirstName(), u.getLastName(), u.getShop(), u.getMail(), pwd, u.getPrivilege());
			assertTrue(UserDAO.getAllUsers().contains(u));
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testDeleteUser() {
		// We add two user for the tests
		char[] pwd = (new String("password")).toCharArray();
		User u1 = new User("jean", "pierre", shop2, "jp@mail.fr", "password", User.SELLER);
		User u2 = new User("paul", "theo", shop1, "pt@mail.fr", "password", User.ADMIN);
		try {
			UserDAO.addUser(u1.getFirstName(), u1.getLastName(), u1.getShop(), u1.getMail(), pwd, u1.getPrivilege());
			UserDAO.addUser(u2.getFirstName(), u2.getLastName(), u2.getShop(), u2.getMail(), pwd, u2.getPrivilege());
		} catch(Exception e) {}

		// Incorrect fields ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			UserDAO.deleteUser(null);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// A seller can't delete an user ---> should fail
		MainController.setCurrentUser(uSeller1);
		try {
			UserDAO.deleteUser(u1);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// You can't delete the "admin" user ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			UserDAO.deleteUser(UserDAO.getUserByMail("admin"));
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't delete an user with a bigger or equal privilege than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.deleteUser(u2);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't delete an user in another shop than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.deleteUser(u1);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSuperAdmin);
		try {
			UserDAO.deleteUser(u1);
			assertFalse(UserDAO.getAllUsers().contains(u1));
		}
		catch(PermissionException | CoherenceException e) {
			fail("Should have worked");
		}
	}

	@Test
	void testDeleteOwnAccount() {
		// You can delete the "admin" account ---> should fail
		MainController.setCurrentUser(UserDAO.getUserByMail("admin"));
		try {
			UserDAO.deleteOwnAccount();
			fail("Should throw PermissionException");
		}
		catch(PermissionException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(UserDAO.getUserByMail("tom.suchel@gmail.com"));
		try {
			UserDAO.deleteOwnAccount();
		}
		catch(PermissionException e) {
			fail("Should have worked");
		}
		catch(NullPointerException e) {
			// we catch this exception because the method try to close the main window, which doesn't exist in the test phase
		}
	}

	@Test
	void testModifyUser() {
		// We add an user for the tests
		char[] pwd = (new String("password")).toCharArray();
		User u = new User("jean", "pierre", shop1, "jp@mail.fr", "password", User.SELLER);
		try {
			UserDAO.addUser(u.getFirstName(), u.getLastName(), u.getShop(), u.getMail(), pwd, u.getPrivilege());
		} catch(Exception e) {}

		// Incorrect fields ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			UserDAO.modifyUser(u, "jean02", "pierre", null, "mail", pwd, User.ADMIN);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// A seller can't modify an user ---> should fail
		MainController.setCurrentUser(uSeller1);
		try {
			UserDAO.modifyUser(u, "jean", "pierre", shop1, "jp@mail.fr", pwd, User.SELLER);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't modify an user in another show than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.modifyUser(u, "jean", "pierre", shop2, "jp@mail.fr", pwd, User.SELLER);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// An admin can't modify an user with a bigger privilege than its own ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			UserDAO.modifyUser(u, "jean", "pierre", shop1, "jp@mail.fr", pwd, User.SUPER_ADMIN);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uAdmin);
		try {
			User newU = new User("paul", "hugo", shop1, "ph@mail.fr", "password", User.ADMIN);
			UserDAO.modifyUser(u, newU.getFirstName(), newU.getLastName(), newU.getShop(), newU.getMail(), pwd, newU.getPrivilege());
			assertFalse(UserDAO.getAllUsers().contains(u));
			assertTrue(UserDAO.getAllUsers().contains(newU));
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testGetUserByMail() {
		assertEquals(UserDAO.getUserByMail(null), null);				// if mail is null		---> should return null
		assertEquals(UserDAO.getUserByMail("unknow@mail.fr"), null);	// if mail is unknow	---> should return null

		// We add an user for the test
		String strPwd = "password";
		char[] chrPwd = strPwd.toCharArray();
		User u = new User("Jean", "Pierre", shop1, "jp@mail.fr", strPwd, User.ADMIN);
		try {
			UserDAO.addUser(u.getFirstName(), u.getLastName(), u.getShop(), u.getMail(), chrPwd, u.getPrivilege());
		} catch (Exception e) {}

		assertEquals(UserDAO.getUserByMail("jp@mail.fr"), u);	// if mail is correct	---> return the corresponding user
	}

	@Test
	void testIsNameValid() {
		assertEquals(UserDAO.isNameValid(null), false);			// name can't be null	---> should fail	
		assertEquals(UserDAO.isNameValid(""), false);			// name can't be empty	---> should fail
		assertEquals(UserDAO.isNameValid("Pierre42"), false);	// bad formatted name	---> should fail
		assertEquals(UserDAO.isNameValid("Jean"), true);		// correct name			---> should work
	}

	@Test
	void testIsShopValid() {
		assertEquals(UserDAO.isShopValid(null), false);				// shop can't be null			---> should fail
		Shop unknow = new Shop("Unknow shop", "Unknown address");
		assertEquals(UserDAO.isShopValid(unknow), false);			// shop isn't in the database	---> should fail
		assertEquals(UserDAO.isShopValid(shop1), true);				// correct shop					---> should work
	}

	@Test
	void testIsMailValid() {
		assertEquals(UserDAO.isMailValid(null), false);						// mail can't be null 	---> should fail
		
		// On ajoute un utilisateur pour les tests
		try {
			UserDAO.addUser("tom", "suchel", shop1, "tom.suchel@gmail.com", (new String("pwd")).toCharArray(), User.ADMIN);
		} catch (Exception e) {}
		assertEquals(UserDAO.isMailValid("tom.suchel@gmail.com"), false);	// mail already taken	---> should fail
		
		assertEquals(UserDAO.isMailValid("a bad mail"), false);				// bad formatted mail	---> should fail
		assertEquals(UserDAO.isMailValid("almost_good@mail"), false);		// bad formatted mail	---> should fail
		assertEquals(UserDAO.isMailValid("good@mail.fr"), true);			// correct mail			---> should fail
	}

	@Test
	void testIsPasswordValid() {
		assertEquals(UserDAO.isPasswordValid(null), false);		// password can't be null	---> should fail
		char[] pwd1 = {};
		assertEquals(UserDAO.isPasswordValid(pwd1), false);		// password can't be empty	---> should fail
		char[] pwd2 = (new String("t5X1@k=")).toCharArray();
		assertEquals(UserDAO.isPasswordValid(pwd2), true);		// correct password			---> should work
	}

	@Test
	void testIsPrivilegeValid() {
		assertEquals(UserDAO.isPrivilegeValid(0), false);	// privilege can't be < 1			---> should fail
		assertEquals(UserDAO.isPrivilegeValid(5), false);	// privilege can't be > 4			---> should fail
		assertEquals(UserDAO.isPrivilegeValid(3), true);	// privilege can be between 1 and 4	---> should work
	}

}
