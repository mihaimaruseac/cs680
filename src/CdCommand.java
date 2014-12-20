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
		FSElement now = null;

		try {
			now = fs.resolvePath(path);
		} catch (InvalidPathException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}

		if (now.isLeaf()) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new InvalidArgumentsCommandException(path + ": not a directory"));
			throw up;
		}

		dir = (Directory)now;
		lastDir = fs.getCurrent();

		FileSystem.getInstance().setCurrent(dir);
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
