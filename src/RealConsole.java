import java.io.Console;
import java.io.PrintWriter;
import java.io.Reader;

class RealConsole extends AbstractConsole {
	private final Console console;

	public RealConsole(Console console) {
		this.console = console;
	}

	@Override
	public AbstractConsole printf(String fmt, Object... params) {
		console.format(fmt, params);
		return this;
	}

	@Override
	public String readLine() {
		return console.readLine();
	}

	@Override
	public char[] readPassword() {
		return console.readPassword();
	}

	@Override
	public void flush() {
		console.flush();
	}
}
