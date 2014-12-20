public class UserGrantCommand extends EditUserCommand {
	UserPermissionType perm;

	public UserGrantCommand(String user, UserPermissionType perm) {
		super(user, "ugrant " + perm);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		if (fs.isAllowed(UserPermissionType.PERMISSION_GRANT) && fs.isAllowed(perm))
			fs.grant(u, perm);
		else
			throw new MultipleExceptionsException(new AccessDeniedException("Illegal grant action"));
	}
}
