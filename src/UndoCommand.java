public class UndoCommand extends Command {
	public UndoCommand() {
	}

	@Override
	public String getCommandLine() {
		return "undo";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws InvalidCommandException {
		Command c = CommandHistory.getInstance().peek();
		if (c == null)
			throw new EmptyHistoryException();
		c.executeUndo();
	}
}
