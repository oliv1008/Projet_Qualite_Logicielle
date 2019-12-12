package model;

import java.util.HashMap;

public class Shop {

	private String name;
	private String address;
	private HashMap<Item, Integer> stock;
	
	public Shop() {
		
	}
	
	public Shop(String name, String address, HashMap<Item, Integer> stock) {
		this.name = name;
		this.address = address;
		this.stock = stock;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public HashMap<Item, Integer> getStock() {
		return stock;
	}
	
	
}
