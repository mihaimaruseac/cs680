public class UserNotFoundException extends InvalidCommandException {
	public UserNotFoundException(String message) {
		super(message + ": no such user");
	}
}
