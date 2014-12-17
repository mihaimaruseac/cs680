public abstract class Command {
	public abstract void execute() throws MultipleExceptionsException;
	public abstract String getCommandLine();

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
