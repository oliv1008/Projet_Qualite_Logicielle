package model;

import java.io.Serializable;
import java.util.HashMap;

public class Shop implements Serializable {

	/*===== ATTRIBUTES =====*/
	private String name;
	private String address;
	private HashMap<Item, Integer> stock;
	
	/*===== BUILDERS =====*/
	public Shop(String name, String address) {
		this.name = name;
		this.address = address;
		stock = new HashMap<Item, Integer>();
	}
	
	public Shop(String name, String address, HashMap<Item, Integer> stock) {
		this.name = name;
		this.address = address;
		this.stock = stock;
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public HashMap<Item, Integer> getStock() {
		return stock;
	}
	
	public Integer getQuantity(Item item) {
		return stock.get(item);
	}
	
	public void setQuantity(Item item, Integer newQuantity) {
		stock.put(item, newQuantity);
	}
	
	/*===== METHODS =====*/
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) 			return false;
	    if (!(obj instanceof Shop)) return false;
	    if (obj == this) 			return true;
	    return this.hashCode() == ((Shop) obj).hashCode();

	}
	
	// Trouver une meilleure façon de faire ça (genre un ID ?)
	@Override
	public int hashCode() {
	    return name.length() * address.length();
	}
	
	
}
