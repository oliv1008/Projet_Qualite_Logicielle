package model;

public class User {

		private int userID;
		private String mail;
		private String firstName;
		private String lastName;
		private String login;
		private String password;
		private int privilegeLevel;
		private Shop shop;
		
		/**
		 * @return the userID
		 */
		public int getUserID() {
			return userID;
		}
		/**
		 * @param userID the userID to set
		 */
		public void setUserID(int userID) {
			this.userID = userID;
		}
		/**
		 * @return the mail
		 */
		public String getMail() {
			return mail;
		}
		/**
		 * @param mail the mail to set
		 */
		public void setMail(String mail) {
			this.mail = mail;
		}
		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}
		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}
		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		/**
		 * @return the login
		 */
		public String getLogin() {
			return login;
		}
		/**
		 * @param login the login to set
		 */
		public void setLogin(String login) {
			this.login = login;
		}
		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		/**
		 * @return the privilegeLevel
		 */
		public int getPrivilegeLevel() {
			return privilegeLevel;
		}
		/**
		 * @param privilegeLevel the privilegeLevel to set
		 */
		public void setPrivilegeLevel(int privilegeLevel) {
			this.privilegeLevel = privilegeLevel;
		}
		/**
		 * @return the shop
		 */
		public Shop getShop() {
			return shop;
		}
		/**
		 * @param shop the shop to set
		 */
		public void setShop(Shop shop) {
			this.shop = shop;
		}
}
