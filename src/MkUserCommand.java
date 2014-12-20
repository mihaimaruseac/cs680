public class MkUserCommand extends EditUserCommand {
	public MkUserCommand(String user) {
		super(user, "mkuser");
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		MultipleExceptionsException up = new MultipleExceptionsException();
		up.addException(new UserExistsException(userName));
		throw up;
	}

	@Override
	protected void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException {
		String password = readAndUpdatePassword();
		FileSystem.getInstance().addUser(userName, password);
	}
}
