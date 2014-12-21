import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Console {
	private static AbstractConsole defaultConsole = (System.console() == null) ? emulatedConsole(System.in, System.out) : new RealConsole(System.console());

	public static AbstractConsole defaultConsole() {
		return defaultConsole;
	}

	public static AbstractConsole emulatedConsole(InputStream in, OutputStream out) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		PrintWriter writer = new PrintWriter(out, true);
		return new CharConsole(reader, writer);
	}
}
