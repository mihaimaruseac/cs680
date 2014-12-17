public class ElementExistsException extends InvalidCommandException {
	public ElementExistsException(String message) {
		super(message + ": existing element(s)");
	}
}
