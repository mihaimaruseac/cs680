public abstract class Command {
	public abstract void execute() throws InvalidCommandException;
	public abstract String getCommandLine();

	public boolean canUndo() {
		return false;
	}

	public boolean goToHistory() {
		return true;
	}

	public void executeUndo() throws InvalidCommandException {
		throw new InvalidCommandException("Command cannot be undone. Sorry");
	}
}
