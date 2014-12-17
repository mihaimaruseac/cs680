public class ChUserCommand extends Command {
	String user;
	String lastUser;
	String cmdLine;

	public ChUserCommand(String user) {
		this.user = user;
		this.lastUser = FileSystem.getInstance().getUser();
		cmdLine = "chuser " + user;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem.getInstance().setUser(user);
	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().setUser(lastUser);
	}
}
