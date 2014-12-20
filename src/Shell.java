import java.io.IOError;
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
		TUIDisplay.simpleDisplayText("Welcome to MMShell. Type exit or press ^D to exit. Type help [<cmd>] for help");
		loop();
	}

	private void parseAndExecute(String cmd) throws MultipleExceptionsException {
		MultipleExceptionsException e = null;
		Command c = null;

		try {
			c = CommandFactory.getCommand(cmd);
		} catch (InvalidCommandException ice) {
			e = new MultipleExceptionsException();
			e.addException(ice);
			throw e;
		}

		if (c.goesToHistory())
			CommandHistory.getInstance().push(c);

		c.execute();
	}

	private void loop() {
		FileSystem fs = FileSystem.getInstance();
		while (onGoing) {
			String prompt = "[" + fs.getUser().getName() + " " + fs.getCurrent().getName() + "]> ";
			System.console().printf(prompt);
			System.console().flush();

			try {
				String line = System.console().readLine();
				if (line == null)
					break;
				else if (line.length() != 0)
					parseAndExecute(line);
			} catch (IOError e) {
				TUIDisplay.simpleDisplayText(e.getMessage());
			} catch (MultipleExceptionsException e) {
				TUIDisplay.simpleDisplayText(e.getMessage());
			}
		}
	}

	public static void main(String args[]) {
		ArrayList<User> users = new ArrayList<User>();

		users.add(new User("root", "TODO"));

		FileSystem.getInstance().setUpUsers(users);
		FileSystem.getInstance().setUp();
		Shell.getInstance().run();
	}
}
