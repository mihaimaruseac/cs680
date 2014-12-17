public class ExitCommand extends Command {
	public ExitCommand() {
	}

	@Override
	public String getCommandLine() {
		return "exit";
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		Shell.getInstance().stopShell();
	}
}
