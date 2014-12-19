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
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

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
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			if (fs.isLeaf(parentDir)) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(new InvalidArgumentsCommandException(parentDir.getName() + ": not a directory"));
				continue;
			}

			try {
				extraPathSetup();
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			fs.setCurrent((Directory)parentDir);

			try {
				create(newName);
			} catch (ElementExistsException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
			} finally {
				fs.setCurrent(current);
			}
		}

		if (up != null)
			throw up;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		String cmd = rm;

		for (String name: paths)
			cmd += " " + name;

		try {
			Command c = CommandFactory.getCommand(cmd);
			c.execute();
		} catch (InvalidCommandException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}
	}

	protected void extraPathSetup() throws InvalidPathException {}
	protected abstract void create(String name) throws ElementExistsException;
}
