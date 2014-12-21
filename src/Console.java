public class Console {
	private static AbstractConsole console;

	public static void resetConsole() {
		console = (System.console() == null) ? new CharConsole(System.in, System.out) : new RealConsole(System.console());
	}

	public static AbstractConsole getConsole() {
		return console;
	}
}
