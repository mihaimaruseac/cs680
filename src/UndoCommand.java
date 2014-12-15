public class UndoCommand extends Command {
	public UndoCommand() {
	}

	@Override
	public String getCommandLine() {
		return "undo";
	}

	@Override
	public boolean goToHistory() {
		return false;
	}

	@Override
	public void execute() throws InvalidCommandException {
		CommandHistory.getInstance().peek().executeUndo();
	}
}
