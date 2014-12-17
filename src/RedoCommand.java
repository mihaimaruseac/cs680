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
	public void execute() throws InvalidCommandException {
		CommandHistory.getInstance().peek().execute();
	}
}
