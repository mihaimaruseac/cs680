public class ChUserCommand extends Command {
	User user;
	User lastUser;
	String userName;

	public ChUserCommand(String user) {
		userName = user;
		cmdLine = "chuser " + user;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		user = FileSystem.getInstance().getUserByName(userName);
		lastUser = FileSystem.getInstance().getUser();
		FileSystem.getInstance().setUser(user);
	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().setUser(lastUser);
	}
}
