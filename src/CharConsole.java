import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

class CharConsole extends AbstractConsole {
	private final BufferedReader reader;
	private final PrintWriter writer;

	public CharConsole(BufferedReader reader, PrintWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	@Override
	public CharConsole printf(String fmt, Object... params) {
		writer.printf(fmt, params);
		return this;
	}

	@Override
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}

	@Override
	public char[] readPassword() {
		return readLine().toCharArray();
	}

	@Override
	public void flush() {
		writer.flush();
	}
}
