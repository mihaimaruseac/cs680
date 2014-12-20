public class ExitCommand extends Command {
	public ExitCommand() {
		cmdLine = "exit";
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem.getInstance().logOutUser();
	}
}
