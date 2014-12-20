public abstract class FileContentsCommand extends Command {
	String file;
	FSPermissionType permRequired;

	public FileContentsCommand(String file, String base) {
		this.file = file;
		cmdLine = base + " " + file;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		File fileTarget = prepareTarget();
		workOn(fileTarget);
	}

	protected abstract void workOn(File fileTarget);

	private File prepareTarget() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();

		try {
			FSElement fileTarget = fs.resolvePath(file);

			if (!fs.isLeaf(fileTarget))
				throw new InvalidArgumentsCommandException(fileTarget.getName() + ": not a file");

			if (!fs.isAllowed(fileTarget, permRequired))
				throw new AccessDeniedException("Cannot execute action on " + fileTarget.getName());

			return (File)fileTarget;
		} catch (InvalidCommandException e) {
			throw new MultipleExceptionsException(e);
		}
	}
}
