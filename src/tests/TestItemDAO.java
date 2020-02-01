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
import model.Item;
import model.Shop;
import model.User;

class TestItemDAO {

	private static Shop shop1;
	private static Shop shop2;
	private static User uSuperAdmin;
	private static User uAdmin;
	private static User uSeller1;
	private static User uSeller2;
	private static User uRestSeller;

	@BeforeAll
	public static void init() {
		shop1 = new Shop("Decatest", "142 rue jean boyer");
		shop2 = new Shop("Go Test", "26 rue de junit");

		uSuperAdmin = new User("admin", "admin", shop1, "mailsuperadmin", "password", User.SUPER_ADMIN);
		uAdmin = new User("paul", "pierre", shop1, "mailadmin", "password", User.ADMIN);
		uSeller1 = new User("jean1", "pierre1", shop1, "mailSeller1", "password", User.SELLER);
		uSeller2 = new User("jean2", "pierre2", shop2, "mailSeller2", "password", User.SELLER);
		uRestSeller = new User("pierre", "marie", shop1, "mailRestrSeller", "password", User.RESTRICTED_SELLER);	
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
	void testGetAllItems() {
		assertNotNull(ItemDAO.getAllItems());
	}

	@Test
	void testAddItem() {
		// An admin can't add an item ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			ItemDAO.addItem("Objet", "description", 2.50);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// The item fields are incorrects ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.addItem("Ob2jet", "description", 2.512);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSuperAdmin);
		try {
			Item i = new Item("Objet", "description", 2.50);
			ItemDAO.addItem(i.getName(), i.getDescription(), i.getPrice());
			assertTrue(ItemDAO.getAllItems().contains(i));
		}
		catch(Exception e) {
			fail("Should have worked");
		}	
	}

	@Test
	void testDeleteItem() {
		// We add an item for the tests
		Item i = new Item("Objet", "description", 2.50);
		try {
			ItemDAO.addItem(i.getName(), i.getDescription(), i.getPrice());
		} catch(Exception e) {}

		// An admin can't delete an item ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			ItemDAO.deleteItem(i);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// The fields are incoherents (null) ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.deleteItem(null);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.deleteItem(i);
			assertFalse(ItemDAO.getAllItems().contains(i));
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testModifyItem() {
		// We add an item for the tests
		Item i = new Item("Objet", "description", 2.50);
		try {
			ItemDAO.addItem(i.getName(), i.getDescription(), i.getPrice());
		} catch(Exception e) {}

		// An admin can't modify an item ---> should fail
		MainController.setCurrentUser(uAdmin);
		try {
			ItemDAO.modifyItem(i, "newName", "description", 2.50);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// The items fields are incorrects ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.modifyItem(i, "name 12", "description", 2.123);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSuperAdmin);
		try {
			Item newItem = new Item("newName", "newDescription", 3);
			ItemDAO.modifyItem(i, newItem.getName(), newItem.getDescription(), newItem.getPrice());
			assertFalse(ItemDAO.getAllItems().contains(i));
			assertTrue(ItemDAO.getAllItems().contains(newItem));
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testTransferItem() {
		// We add an item form the tests
		Item i = new Item("Objet", "description", 2.50);
		try {
			ItemDAO.addItem(i.getName(), i.getDescription(), i.getPrice());
			ItemDAO.changeStock(shop1, i, 12);
		} catch(Exception e) {}

		// A seller working in shop1 can't transfer an item from shop2 ---> should fail
		MainController.setCurrentUser(uSeller1);
		try {
			ItemDAO.transferItem(i, 5, shop2, shop1);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// A restricted seller can't transfer item ---> should fail
		MainController.setCurrentUser(uRestSeller);
		try {
			ItemDAO.transferItem(i, 5, shop1, shop2);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// The fields are incoherents (20>12) ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.transferItem(i, 20, shop1, shop2);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}
		
		// The fields are incoherents (null) ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.transferItem(null, 5, shop1, shop2);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}
		
		// You can't transfer from shop1 to shop1 ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.transferItem(i, 5, shop1, shop1);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSeller1);
		try {
			ItemDAO.transferItem(i, 5, shop1, shop2);
			assertEquals(shop1.getStock().get(i), 7);
			assertEquals(shop2.getStock().get(i), 5);
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testChangeStock() {
		// We add an item for the tests
		Item i = new Item("Objet", "description", 2.50);
		try {
			ItemDAO.addItem(i.getName(), i.getDescription(), i.getPrice());
		} catch(Exception e) {}

		// A seller from shop1 can't change stocks from shop2 ---> should fail
		MainController.setCurrentUser(uSeller1);
		try {
			ItemDAO.changeStock(shop2, i, 12);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// A restricted seller can't change stocks ---> should fail
		MainController.setCurrentUser(uRestSeller);
		try {
			ItemDAO.changeStock(shop1, i, 12);
			fail("Should throw PermissionException");
		}
		catch(PermissionException | CoherenceException e) {}

		// The fields are incoherents (negative value) ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.changeStock(shop1, i, -5);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}
		
		// The fields are incoherents (null) ---> should fail
		MainController.setCurrentUser(uSuperAdmin);
		try {
			ItemDAO.changeStock(null, i, 3);
			fail("Should throw CoherenceException");
		}
		catch(PermissionException | CoherenceException e) {}

		// This situation is correct ---> should work
		MainController.setCurrentUser(uSeller2);
		try {
			ItemDAO.changeStock(shop2, i, 6);
			assertEquals(shop2.getStock().get(i), 6);
		}
		catch(Exception e) {
			fail("Should have worked");
		}
	}

	@Test
	void testIsNameValid() {
		assertEquals(ItemDAO.isNameValid(null), false);			// name can't be null 			---> should fail
		assertEquals(ItemDAO.isNameValid(""), false);			// name can't be empty			---> should fail
		assertEquals(ItemDAO.isNameValid("T3NN1S"), false);		// name can't contains numbers	---> should fail
		assertEquals(ItemDAO.isNameValid("B@sket"), false);		// name can't contains symbols	---> should fail
		assertEquals(ItemDAO.isNameValid("Arrosoir"), true);	// correct name					---> should work
	}

	@Test
	void testIsDescriptionValid() {
		assertEquals(ItemDAO.isDescriptionValid(null), false);					// description can't be null	---> should fail
		assertEquals(ItemDAO.isDescriptionValid(""), false);					// description can't be empty	---> should fail
		assertEquals(ItemDAO.isDescriptionValid("Good description..."), true);	// correct description			---> should work
	}

	@Test
	void testIsPriceValid() {
		assertEquals(ItemDAO.isPriceValid(-5), false);		// price can't be negative			---> should fail
		assertEquals(ItemDAO.isPriceValid(0), false);		// price can't be zero				---> should fail
		assertEquals(ItemDAO.isPriceValid(3.141), false);	// price can't have more 3 digits	---> should fail
		assertEquals(ItemDAO.isPriceValid(2.42), true);		// correct price					---> should work
		assertEquals(ItemDAO.isPriceValid(16), true);		// correct price					---> should work
	}

}
