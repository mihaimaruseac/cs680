public class UserRevokeCommand extends EditUserCommand {
	UserPermissionType perm;

	public UserRevokeCommand(String user, UserPermissionType perm) {
		super(user, "urevoke " + perm);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		if (fs.isAllowed(UserPermissionType.PERMISSION_REVOKE) && fs.isAllowed(perm))
			fs.revoke(u, perm);
		else
			throw new MultipleExceptionsException(new AccessDeniedException("Illegal revocation action"));
	}
}
