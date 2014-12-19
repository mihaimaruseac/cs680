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
		lastUser = FileSystem.getInstance().getUser();

		try {
			user = FileSystem.getInstance().getUserByName(userName);
			FileSystem.getInstance().setUser(user);
		} catch (UserNotFoundException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}

	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().setUser(lastUser);
	}
}
