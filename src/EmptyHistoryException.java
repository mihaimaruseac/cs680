public class EmptyHistoryException extends InvalidCommandException {
	public EmptyHistoryException() {
		super("Cannot execute command: history is empty");
	}
}
