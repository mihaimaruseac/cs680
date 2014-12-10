public class RedoCommand extends Command {
	Command cmd;

	public RedoCommand(Command cmd) {
		this.cmd = cmd;
	}

	@Override
	public String getCommandLine() {
		return "redo";
	}

	@Override
	public void execute() throws InvalidCommandException {
		cmd.execute();
	}
}
