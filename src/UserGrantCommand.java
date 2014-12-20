public class UserGrantCommand extends PermCommand {
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
		else {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new AccessDeniedException("Illegal grant action"));
			throw up;
		}
	}
}
