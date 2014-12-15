public class ExitCommand extends Command {
	public ExitCommand() {
	}

	@Override
	public String getCommandLine() {
		return "exit";
	}

	@Override
	public void execute() throws InvalidCommandException {
		Shell.getInstance().stopShell();
	}
}
