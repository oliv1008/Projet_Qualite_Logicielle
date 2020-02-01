package model;

/**
 * Represent a line of data in the table model (Shop | Item | Quantity)
 */
public class LineData {

	/*===== ATTRIBUTES =====*/
	private Shop shop;
	private Item item;
	
	/*===== BUILDER =====*/
	public LineData(Shop shop, Item item) {
		this.shop = shop;
		this.item = item;
	}

	/*===== GETTERS AND SETTERS =====*/
	public Shop getShop() {
		return shop;
	}

	public Item getItem() {
		return item;
	}

	public Integer getQuantity() {
		return shop.getQuantity(item);
	}
	
	public void setQuantity(Integer quantity) {
		shop.setQuantity(item, quantity);
	}
		
}
