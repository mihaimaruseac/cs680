public class UserExistsException extends InvalidCommandException {
	public UserExistsException(String message) {
		super(message + ": user already exists");
	}
}
