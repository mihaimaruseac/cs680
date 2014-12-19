public class UndoCommand extends Command {
	public UndoCommand() {
		cmdLine = "undo";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		Command c = CommandHistory.getInstance().peek();

		if (c == null) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new EmptyHistoryException());
			throw up;
		}

		c.executeUndo();
	}
}
