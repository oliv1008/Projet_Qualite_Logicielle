package model;

public class User {

	public static final int RESTRICTED_SELLER = 1;
	public static final int SELLER = 2;
	public static final int ADMIN = 3;
	public static final int SUPER_ADMIN = 4;
	
	private String firstName;
	private String lastName;
	private Shop shop;
	private String mail;
	private String password;
	private int privilege;
	
	public User(String firstName, String lastName, Shop shop, String mail, String password, int privilege) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.shop = shop;
		this.mail = mail;
		this.password = password;
		this.privilege = privilege;
	}

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

	public String getPassword() {
		return password;
	}

	public int getPrivilege() {
		return privilege;
	}
	
	
	
	
	
	
}
