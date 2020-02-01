package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import model.Item;
import model.Shop;

class TestShop {

	@Test
	void testHashCode() {
		Shop s1 = new Shop("shop", "address");
		assertEquals(s1.hashCode(), 28);
	}

	@Test
	void testEqualsObject() {
		Shop s1 = new Shop("shop", "address");
		Shop s2 = new Shop("outlet", "address");
		Shop s3 = new Shop("shop", "address");
		
		assertFalse(s1.equals(null));
		assertFalse(s1.equals(s2));
		assertTrue(s1.equals(s3));
	}

}
