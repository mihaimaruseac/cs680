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
		FileSystem fs = FileSystem.getInstance();

		try {
			User user = fs.getUserByName(userName);

			Console.defaultConsole().printf("Password: ");
			Console.defaultConsole().flush();
			String password = new String(Console.defaultConsole().readPassword());

			if (fs.checkPassword(user, password))
				fs.setUser(user);
			else {
				throw new PasswordsDontMatchException();
			}
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}

	@Override
	protected void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().logOutUser();
	}
}
