public abstract class Command {
	String cmdLine;

	public abstract void execute() throws MultipleExceptionsException;

	public final String getCommandLine() {
		return cmdLine;
	}

	public boolean canUndo() {
		return false;
	}

	public boolean goesToHistory() {
		return true;
	}

	public void executeUndo() throws MultipleExceptionsException {
		MultipleExceptionsException e = new MultipleExceptionsException();
		e.addException(new NotUndoableCommandException());
		throw e;
	}
}
