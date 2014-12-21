import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

class CharConsole extends AbstractConsole {
	private final BufferedReader reader;
	private final PrintWriter writer;

	public CharConsole(InputStream in, OutputStream out) {
		this.reader = new BufferedReader(new InputStreamReader(in));
		this.writer = new PrintWriter(out, true);
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
