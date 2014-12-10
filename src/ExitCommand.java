public class ExitCommand extends Command {
	Shell s;

	public ExitCommand(Shell s) {
		this.s = s;
	}

	@Override
	public String getCommandLine() {
		return "exit";
	}

	@Override
	public void execute() throws InvalidCommandException {
		s.stopShell();
	}
}
