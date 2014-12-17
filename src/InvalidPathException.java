public class InvalidPathException extends InvalidCommandException {
	public InvalidPathException(String message) {
		super(message + ": no such path(s)");
	}
}
