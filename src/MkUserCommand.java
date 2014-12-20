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
		FileSystem fs = FileSystem.getInstance();

		if (!fs.isAllowed(UserPermissionType.PERMISSION_ROOT)) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new AccessDeniedException("Must have root access to create users"));
			throw up;
		}

		String password = readAndUpdatePassword();
		fs.addUser(userName, password);
	}
}
