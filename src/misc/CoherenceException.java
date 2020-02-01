package misc;

/**
 * Exception used when a situation is impossible because some parameters are incorrects
 */
public class CoherenceException extends Exception {

	public CoherenceException(String message) {
		super(message);
	}
}
