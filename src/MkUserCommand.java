public class MkUserCommand extends Command {
	String userName;

	public MkUserCommand(String user) {
		cmdLine = "mkuser " + user;
		userName = user;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		try {
			User u = fs.getUserByName(userName);
		} catch (UserNotFoundException e) {
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

			fs.addUser(userName, password);
			return; /* don't throw the next exception */
		}

		MultipleExceptionsException up = new MultipleExceptionsException();
		up.addException(new UserExistsException(userName));
		throw up;
	}
}
