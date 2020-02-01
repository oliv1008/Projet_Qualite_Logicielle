package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ItemDAO;
import controller.MainController;
import controller.ShopDAO;
import controller.UserDAO;
import model.Shop;
import model.User;

class TestShopDAO {

	private static User uSuperAdmin;
	private static Shop shop1;
	
	@BeforeAll
	public static void init() {
		shop1 = new Shop("Decatest", "142 rue jean boyer");

		uSuperAdmin = new User("admin", "admin", shop1, "mailsuperadmin", "password", User.SUPER_ADMIN);
	}
	
	@BeforeEach
	public void resetDatabase() {
		MainController.setCurrentUser(uSuperAdmin);
		ItemDAO.loadItemFile(true);
		ShopDAO.loadShopFile(true);	
		UserDAO.loadUserFile(true);
	}

	@Test
	void testGetAllShops() {
		assertNotNull(ShopDAO.getAllShops());
	}

	@Test
	void testGetShopByName() {
		assertEquals(ShopDAO.getShopByName(null), null);				// if name is null		---> should return null
		assertEquals(ShopDAO.getShopByName("unMagasin"), null);			// if name is unknow	---> should return null

		// We add a shop for the test
		Shop s = new Shop("TheShop", "address");
		try {
			ShopDAO.getAllShops().add(s);
		} catch (Exception e) {}

		assertEquals(ShopDAO.getShopByName("TheShop"), s);	// if name is correct	---> return the corresponding shop
	}

}
