public class WriteFileCommand extends EditFileCommand {
	public WriteFileCommand(String file) {
		super(file, "edit");
	}

	@Override
	protected void workWithText(File file, String text) {
		FileSystem.getInstance().editFileContents(file, text);
	}
}
