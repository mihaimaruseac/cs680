public class PathRevokeCommand extends PermCommand {
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
			else {
				MultipleExceptionsException up = new MultipleExceptionsException();
				up.addException(new AccessDeniedException("Illegal revocation action"));
				throw up;
			}
		} catch (InvalidPathException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}
	}
}
