package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import model.Shop;
import model.User;

class TestUser {

	@Test
	void testHashCode() {
		User u1 = new User("jean", "pierre", new Shop("shop", "address"), "jp@mail.fr", "blabla", User.SELLER);
		assertEquals(u1.hashCode(), 304);
	}

	@Test
	void testEqualsObject() {
		User u1 = new User("jean", "pierre", new Shop("shop", "address"), "mail", "blabla", User.SELLER);
		User u2 = new User("george", "pierre", new Shop("shop", "address"), "mail", "blabla", User.SELLER);
		User u3 = new User("jean", "pierre", new Shop("shop", "address"), "mail", "blabla", User.SELLER);
		
		assertFalse(u1.equals(null));
		assertFalse(u1.equals(u2));
		assertTrue(u1.equals(u3));
	}

}
