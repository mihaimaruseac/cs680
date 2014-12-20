public class UndoCommand extends Command {
	public UndoCommand() {
		cmdLine = "undo";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		Command c = CommandHistory.getInstance().peek();

		if (c == null)
			throw new MultipleExceptionsException(new EmptyHistoryException());

		c.executeUndoCommand();
	}
}
