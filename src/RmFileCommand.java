public class RmFileCommand extends RmCommand {
	public RmFileCommand(String[] args) {
		super(args, "rm");
	}

	@Override
	protected void validateElement(String path, FSElement element) throws InvalidArgumentsCommandException {
		FileSystem fs = FileSystem.getInstance();
		if (!fs.isLeaf(element)) {
			throw new InvalidArgumentsCommandException(path + ": not a file");
		}
	}
}
