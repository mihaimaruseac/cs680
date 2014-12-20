public class PathRevokeCommand extends EditUserCommand {
	FSPermissionType perm;
	String path;

	public PathRevokeCommand(String user, FSPermissionType perm, String path) {
		super(user, "prevoke " + perm + " " + path);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		try {
			FileSystem fs = FileSystem.getInstance();
			FSElement e = fs.resolvePath(path);

			if (fs.isAllowed(UserPermissionType.PERMISSION_REVOKE) && fs.isAllowed(e, perm))
				fs.revoke(u, perm, e);
			else
				throw new MultipleExceptionsException(new AccessDeniedException("Illegal revocation action"));
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}
}
