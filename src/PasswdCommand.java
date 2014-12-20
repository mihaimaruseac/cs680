public class PasswdCommand extends EditUserCommand {
	boolean self;

	public PasswdCommand() {
		this(FileSystem.getInstance().getUser().getName());
		self = true;
	}

	public PasswdCommand(String user) {
		super(user, "passwd");
		self = false;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		if (!self && !fs.isAllowed(UserPermissionType.PERMISSION_ROOT))
			throw new MultipleExceptionsException(new AccessDeniedException("Can only change own password"));

		String password = readAndUpdatePassword();
		fs.changePassword(u, password);
	}
}
