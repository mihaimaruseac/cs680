public class RedoCommand extends Command {
	public RedoCommand() {
		cmdLine = "redo";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		Command c = CommandHistory.getInstance().peek();

		if (c == null) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new EmptyHistoryException());
			throw up;
		}

		c.executeCommand();
	}
}
