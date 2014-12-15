import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Shell {
	private static Shell instance = null;
	private boolean onGoing;

	private Shell() {
		onGoing = true;
	}

	public static Shell getInstance() {
		if (instance == null)
			instance = new Shell();
		return instance;
	}

	public void stopShell() {
		onGoing = false;
	}

	private void run() {
		System.out.println("Welcome to MMShell. Type exit or press ^D to exit. Type help for help");
		loop();
	}

	private void parseAndExecute(String cmd) throws InvalidCommandException {
		Command c = CommandFactory.getInstance().getCommand(cmd);
		c.execute();
		if (c.goToHistory())
			CommandHistory.getInstance().push(c);
	}

	private void loop() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (onGoing) {
			System.out.println("User: " + FileSystem.getInstance().getUser()
					+ ", current dir: " + FileSystem.getInstance().getCurrent().getName()
					+ ", type command below, help for help");
			try {
				String line = in.readLine();
				if (line == null)
					break;
				else if (line.length() != 0)
					parseAndExecute(line);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String args[]) {
		Shell.getInstance().run();
	}
}
