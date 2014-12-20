public class ReadFileCommand extends FileContentsCommand {
	public ReadFileCommand(String file) {
		super(file, "cat");
		permRequired = FSPermissionType.PERMISSION_READ;
	}

	@Override
	protected void workOn(File file) {
		FileSystem.getInstance().showFileContents(file);
	}
}
