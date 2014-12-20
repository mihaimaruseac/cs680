public class UserGrantCommand extends GrantCommand {
	UserPermissionType perm;

	public UserGrantCommand(String user, UserPermissionType perm) {
		super(user, "ugrant " + perm);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		FileSystem.getInstance().grant(u, perm);
	}
}
