public abstract class EditUserCommand extends Command {
	String userName;

	public EditUserCommand(String user, String base) {
		cmdLine = base + " " + user;
		userName = user;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		try {
			User u = fs.getUserByName(userName);
			executeOnUserFound(u);
		} catch (UserNotFoundException e) {
			executeOnUserNotFound(e);
		}
	}

	protected abstract void executeOnUserFound(User u) throws MultipleExceptionsException;
	protected abstract void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException;

	protected String readAndUpdatePassword() throws MultipleExceptionsException {
		System.console().printf("Password: ");
		System.console().flush();
		String password = new String(System.console().readPassword());
		System.console().printf("Confirm password: ");
		System.console().flush();
		String cfPassword = new String(System.console().readPassword());

		if (!password.equals(cfPassword)) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new PasswordsDontMatchException());
			throw up;
		}

		return password;
	}
}