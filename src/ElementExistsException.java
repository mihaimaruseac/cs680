public class ElementExistsException extends InvalidCommandException {
	public ElementExistsException(String message) {
		super(message + ": element exists");
	}
}
