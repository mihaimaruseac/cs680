public class ExitCommand extends Command {
	public ExitCommand() {
		cmdLine = "exit";
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem.getInstance().logOutUser();
	}
}
