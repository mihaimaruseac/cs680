public class HistoryCommand extends Command {
	public HistoryCommand() {
	}

	@Override
	public String getCommandLine() {
		return "history";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		CommandHistory.getInstance().printCommands();
	}
}
