package model;

import java.io.Serializable;

import misc.BCrypt;

/**
 * Model for the users
 */
public class User implements Serializable {

	/*===== CONSTANTS =====*/
	public static final int RESTRICTED_SELLER = 1;
	public static final int SELLER = 2;
	public static final int ADMIN = 3;
	public static final int SUPER_ADMIN = 4;
	
	/*===== ATTRIBUTES =====*/
	private String firstName;
	private String lastName;
	private Shop shop;
	private String mail;
	private String hashedPwd;
	private int privilege;
	
	/*===== BUILDER =====*/
	public User(String firstName, String lastName, Shop shop, String mail, String password, int privilege) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.shop = shop;
		this.mail = mail;
		setHashedPwd(password);
		this.privilege = privilege;
	}

	/*===== GETTERS AND SETTERS =====*/
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Shop getShop() {
		return shop;
	}

	public String getMail() {
		return mail;
	}

	public String getHashedPwd() {
		return hashedPwd;
	}

	public int getPrivilege() {
		return privilege;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setHashedPwd(String password) {
		this.hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt(5));
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	/*===== METHODS =====*/
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) 			return false;
	    if (!(obj instanceof User)) return false;
	    if (obj == this) 			return true;
	    return this.hashCode() == ((User) obj).hashCode();
	}
	
	@Override
	public int hashCode() {
	    return firstName.length() * lastName.length() + shop.hashCode() * mail.length();
	}
		
}
