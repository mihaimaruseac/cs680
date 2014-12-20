public class MkUserCommand extends EditUserCommand {
	public MkUserCommand(String user) {
		super(user, "mkuser");
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		throw new MultipleExceptionsException(new UserExistsException(userName));
	}

	@Override
	protected void executeOnUserNotFound(UserNotFoundException e) throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		if (!fs.isAllowed(UserPermissionType.PERMISSION_ROOT))
			throw new MultipleExceptionsException (new AccessDeniedException("Must have root access to create users"));

		String password = readAndUpdatePassword();
		fs.addUser(userName, password);
	}
}
