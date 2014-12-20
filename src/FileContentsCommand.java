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
		FSElement fileTarget = null;

		try {
			fileTarget = fs.resolvePath(file);
		} catch (InvalidPathException e) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(e);
			throw up;
		}

		if (!fs.isLeaf(fileTarget)) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new InvalidArgumentsCommandException(fileTarget.getName() + ": not a file"));
			throw up;
		}

		if (!fs.isAllowed(fileTarget, permRequired)) {
			MultipleExceptionsException up = new MultipleExceptionsException();
			up.addException(new AccessDeniedException("Cannot execute action on " + fileTarget.getName()));
			throw up;
		}

		return (File)fileTarget;
	}
}
