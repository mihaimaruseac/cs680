public class PasswdCommand extends EditUserCommand {
	public PasswdCommand() {
		this(FileSystem.getInstance().getUser().getName());
	}

	public PasswdCommand(String user) {
		super(user, "passwd");
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		String password = readAndUpdatePassword();
		FileSystem.getInstance().changePassword(u, password);
	}

	@Override
	protected void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException {
		MultipleExceptionsException up = new MultipleExceptionsException();
		up.addException(e);
		throw up;
	}
}
