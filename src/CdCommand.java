public class CdCommand extends Command {
	Directory dir;
	Directory lastDir;
	String cmdLine;
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
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		FSElement now = fs.resolvePath(path);

		if (now.isLeaf())
			throw new InvalidArgumentsCommandException(path + ": not a directory");

		dir = (Directory)now;
		lastDir = fs.getCurrent();

		FileSystem.getInstance().setCurrent(dir);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws InvalidCommandException {
		FileSystem.getInstance().setCurrent(lastDir);
	}
}
