public class CdCommand extends Command {
	Directory dir;
	Directory lastDir;
	String cmdLine;

	public CdCommand() {
		cmdLine = "cd";
		setup(FileSystem.getInstance().getRoot());
	}

	public CdCommand(String path) throws InvalidCommandException {
		cmdLine = "cd " + path;

		FileSystem fs = FileSystem.getInstance();
		FSElement now = fs.resolvePath(fs.getCurrent(), path);

		if (now.isLeaf())
			throw new InvalidCommandException(path + ": not a directory");

		setup((Directory)now);
	}

	private void setup(Directory dir) {
		this.dir = dir;
		this.lastDir = FileSystem.getInstance().getCurrent();
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem.getInstance().setCurrent(dir);
	}

	public boolean canUndo() {
		return true;
	}

	public void executeUndo() throws InvalidCommandException {
		FileSystem.getInstance().setCurrent(lastDir);
	}
}
