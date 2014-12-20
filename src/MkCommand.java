import java.lang.StringBuilder;
import java.util.ArrayList;

public abstract class MkCommand extends Command {
	ArrayList<String> paths;
	String rm;

	public MkCommand(String[] args, String base, String rm) {
		StringBuilder sb = new StringBuilder(base);
		this.rm = rm;
		paths = new ArrayList<String>();

		for (String s: args) {
			paths.add(s);
			sb.append(" " + s);
		}

		cmdLine = sb.toString();
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = new MultipleExceptionsException();

		for (String name : paths) {
			Directory current = fs.getCurrent();

			int sepIx = name.lastIndexOf("/");
			String dirName, newName;
			FSElement parentDir = null;

			if (sepIx == -1) {
				dirName = ".";
				newName = name;
			} else {
				dirName = name.substring(0, sepIx);
				newName = name.substring(sepIx + 1);
			}

			try {
				parentDir = fs.resolvePath(dirName);

				if (fs.isLeaf(parentDir))
					throw new InvalidArgumentsCommandException(parentDir.getName() + ": not a directory");

				if (!fs.isAllowed(parentDir, FSPermissionType.PERMISSION_WRITE))
					throw new AccessDeniedException("Cannot access " + parentDir.getName() + " for writing");

				extraPathSetup();
				fs.setCurrent((Directory)parentDir);

				try {
					create(newName);
				} catch (ElementExistsException e) {
					up.addException(e);
				} finally {
					fs.setCurrent(current);
				}
			} catch (InvalidCommandException e) {
				up.addException(e);
			}
		}

		if (up.isException())
			throw up;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	protected void executeUndo() throws MultipleExceptionsException {
		String cmd = rm;

		for (String name: paths)
			cmd += " " + name;

		try {
			Command c = CommandFactory.getCommand(cmd);
			c.execute();
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}

	protected void extraPathSetup() throws InvalidPathException {}
	protected abstract void create(String name) throws ElementExistsException;
}
