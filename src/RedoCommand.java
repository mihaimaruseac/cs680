public class RedoCommand extends Command {
	public RedoCommand() {
	}

	@Override
	public String getCommandLine() {
		return "redo";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		/*
		Command c = CommandHistory.getInstance().peek();
		if (c == null)
			throw new EmptyHistoryException();
		c.execute();
		*/
	}
}
