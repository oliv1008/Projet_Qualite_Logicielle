package model;

import java.io.Serializable;

public class Item implements Serializable {

	/*===== ATTRIBUTES =====*/
	private String name;
	private String description;
	private double price;

	/*===== BUILDER =====*/
	public Item(String name, String description, double price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	/*===== GETTERS AND SETTERS =====*/
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	/*===== METHODS =====*/
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) 			return false;
	    if (!(obj instanceof Item)) return false;
	    if (obj == this) 			return true;
	    return this.hashCode() == ((Item) obj).hashCode();

	}
	
	// Trouver une meilleure façon de faire ça (genre un ID ?)
	@Override
	public int hashCode() {
	    return (int) (name.length() * description.length() * price*10);
	}

}
