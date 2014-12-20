public abstract class Command {
	protected String cmdLine;
	private boolean successfull;

	public final void executeCommand() throws MultipleExceptionsException {
		successfull = false;
		execute();
		successfull = true;
	}

	public final void executeUndoCommand() throws MultipleExceptionsException {
		if (successfull)
			executeUndo();
	}

	public final String getCommandLine() {
		return cmdLine;
	}

	public boolean canUndo() {
		return false;
	}

	public boolean goesToHistory() {
		return true;
	}

	protected void executeUndo() throws MultipleExceptionsException {
		throw new MultipleExceptionsException(new NotUndoableCommandException());
	}

	protected abstract void execute() throws MultipleExceptionsException;
}
