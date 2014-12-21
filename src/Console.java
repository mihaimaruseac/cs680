import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Console {
	private static AbstractConsole defaultConsole = (System.console() == null) ? emulatedConsole(System.in, System.out) : new RealConsole(System.console());
	private static AbstractConsole activeConsole = defaultConsole;

	public static AbstractConsole getConsole() {
		return defaultConsole;
	}

	public static void setConsole() {
		activeConsole = defaultConsole;
	}

	public static void setConsole(InputStream in, OutputStream out) {
		activeConsole = emulatedConsole(in, out);
	}

	public static AbstractConsole emulatedConsole(InputStream in, OutputStream out) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		PrintWriter writer = new PrintWriter(out, true);
		return new CharConsole(reader, writer);
	}
}
