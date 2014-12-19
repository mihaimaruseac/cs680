import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		TUIDisplay.simpleDisplayText("Write text below. End with empty line or ^D");

		while (true) {
			try {
				String line = in.readLine();
				if (line == null || line.length() == 0)
					break;

				sb.append(line);
				sb.append("\n");
			} catch (IOException e) {
				TUIDisplay.simpleDisplayText(e.getMessage());
			}
		}

		return sb.toString();
	}
}
