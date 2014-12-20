public class UserRevokeCommand extends PermCommand {
	UserPermissionType perm;

	public UserRevokeCommand(String user, UserPermissionType perm) {
		super(user, "urevoke " + perm);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem.getInstance().revoke(u, perm);
	}
}
