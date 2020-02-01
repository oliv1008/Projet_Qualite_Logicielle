package misc;

/**
 * Exception used when the current user doesn't have the permissions to do an action
 */
public class PermissionException extends Exception {

	public PermissionException(String message) {
		super(message);
	}

}
