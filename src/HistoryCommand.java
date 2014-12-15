public class HistoryCommand extends Command {
	public HistoryCommand() {
	}

	@Override
	public String getCommandLine() {
		return "history";
	}

	@Override
	public boolean goToHistory() {
		return false;
	}

	@Override
	public void execute() throws InvalidCommandException {
		CommandHistory.getInstance().printCommands();
	}
}
