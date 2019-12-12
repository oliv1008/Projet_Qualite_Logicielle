package model;

public class StockLine {

	private Shop shop;
	private Item item;
	private Integer quantity;
	
	public StockLine(Shop shop, Item item, Integer quantity) {
		this.shop = shop;
		this.item = item;
		this.quantity = quantity;
	}

	public Shop getShop() {
		return shop;
	}

	public Item getItem() {
		return item;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	
}
