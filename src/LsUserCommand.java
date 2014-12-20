public class LsUserCommand extends Command {
	public LsUserCommand() {
		cmdLine = "lsusers";
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem.getInstance().listUsers();
	}
}
