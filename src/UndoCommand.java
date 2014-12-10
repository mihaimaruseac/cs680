public class UndoCommand extends Command {
	Command cmd;

	public UndoCommand(Command cmd) {
		this.cmd = cmd;
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
		cmd.executeUndo();
	}
}
