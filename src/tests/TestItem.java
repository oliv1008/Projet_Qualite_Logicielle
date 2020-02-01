package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import model.Item;

class TestItem {

	@Test
	void testHashCode() {
		Item i1 = new Item("item", "desc", 2.0);
		assertEquals(i1.hashCode(), 320);
	}

	@Test
	void testEqualsObject() {
		Item i1 = new Item("item", "desc", 2.0);
		Item i2 = new Item("object", "desc", 3.0);
		Item i3 = new Item("item", "desc", 2.0);
		
		assertFalse(i1.equals(null));
		assertFalse(i1.equals(i2));
		assertTrue(i1.equals(i3));
	}

}
