public class NotUndoableCommandException extends InvalidCommandException {
	public NotUndoableCommandException() {
		super("Cannot undo previous command: not possible to undo");
	}
}

