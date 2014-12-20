public class ChUserCommand extends Command {
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
	protected void execute() throws MultipleExceptionsException {
		try {
			User user = FileSystem.getInstance().getUserByName(userName);
			FileSystem.getInstance().setUser(user);
		} catch (UserNotFoundException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}
	}

	@Override
	protected void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().logOutUser();
	}
}
