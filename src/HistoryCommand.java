public class HistoryCommand extends Command {
	CommandHistory ch;

	public HistoryCommand(CommandHistory ch) {
		this.ch = ch;
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
		ch.printCommands();
	}
}
