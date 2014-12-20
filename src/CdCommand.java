public class CdCommand extends Command {
	Directory dir;
	Directory lastDir;
	String path;

	public CdCommand() {
		this(null);
		cmdLine = "cd";
	}

	public CdCommand(String path) {
		cmdLine = "cd " + path;
		this.path = path;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		try {
			FSElement now = fs.resolvePath(path);

			if (now.isLeaf())
				throw new InvalidArgumentsCommandException(path + ": not a directory");

			dir = (Directory)now;
			lastDir = fs.getCurrent();

			FileSystem.getInstance().setCurrent(dir);
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	protected void executeUndo() throws MultipleExceptionsException {
		FileSystem.getInstance().setCurrent(lastDir);
	}
}
