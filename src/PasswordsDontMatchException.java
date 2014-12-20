public class PasswordsDontMatchException extends InvalidCommandException {
	public PasswordsDontMatchException() {
		super("Passwords don't match");
	}
}
