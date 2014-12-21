import java.io.PrintWriter;
import java.io.Reader;

public abstract class AbstractConsole {
	public abstract AbstractConsole printf(String fmt, Object... params);
	public abstract String readLine();
	public abstract char[] readPassword();
	public abstract Reader reader();
	public abstract PrintWriter writer();
	public abstract void flush();
}
