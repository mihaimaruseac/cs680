public class LsUserCommand extends Command {
	public LsUserCommand() {
		cmdLine = "lsusers";
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem.getInstance().listUsers();
	}
}
