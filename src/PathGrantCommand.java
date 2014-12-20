public class PathGrantCommand extends PermCommand {
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
			else {
				MultipleExceptionsException up = new MultipleExceptionsException();
				up.addException(new AccessDeniedException("Illegal grant action"));
				throw up;
			}
		} catch (InvalidPathException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}
	}
}
