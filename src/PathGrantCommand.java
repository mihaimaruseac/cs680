public class PathGrantCommand extends GrantCommand {
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
			fs.grant(u, perm, e);
		} catch (InvalidPathException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}
	}
}
