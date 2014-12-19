public class AppendFileCommand extends EditFileCommand {
	public AppendFileCommand(String file) {
		super(file, "append");
	}

	@Override
	protected void workWithText(File file, String text) {
		FileSystem.getInstance().appendFileContents(file, text);
	}
}
