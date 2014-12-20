import java.io.IOError;
import java.lang.StringBuilder;

public abstract class EditFileCommand extends FileContentsCommand {
	public EditFileCommand(String file, String base) {
		super(file, base);
	}

	@Override
	protected void workOn(File file) {
		String text = getUserText();
		workWithText(file, text);
	}

	protected abstract void workWithText(File file, String text);

	private String getUserText() {
		StringBuilder sb = new StringBuilder();
		TUIDisplay.simpleDisplayText("Write text below. End with empty line or ^D");

		while (true) {
			try {
				String line = System.console().readLine();
				if (line == null || line.length() == 0)
					break;

				sb.append(line);
				sb.append("\n");
			} catch (IOError e) {
				TUIDisplay.simpleDisplayText(e.getMessage());
			}
		}

		String finalString = sb.toString();

		return finalString.substring(0, finalString.length() - 1);
	}
}
