public class HistoryCommand extends Command {
	public HistoryCommand() {
		cmdLine = "history";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		CommandHistory.getInstance().printCommands();
	}
}
