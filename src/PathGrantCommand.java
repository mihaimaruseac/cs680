public class PathGrantCommand extends EditUserCommand {
	FSPermissionType perm;
	String path;

	public PathGrantCommand(String user, FSPermissionType perm, String path) {
		super(user, "pgrant " + perm + " " + path);
		this.perm = perm;
	}

	@Override
	protected void executeOnUserFound(User u) throws MultipleExceptionsException {
		try {
			FileSystem fs = FileSystem.getInstance();
			FSElement e = fs.resolvePath(path);
			if (fs.isAllowed(UserPermissionType.PERMISSION_GRANT) && fs.isAllowed(e, perm))
				fs.grant(u, perm, e);
			else
				throw new MultipleExceptionsException(new AccessDeniedException("Illegal grant action"));
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}
}
