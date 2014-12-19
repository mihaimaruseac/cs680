import java.io.BufferedReader;
import java.io.IOException;
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
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		FileSystem fs = FileSystem.getInstance();
		while (onGoing) {
			TUIDisplay.simpleDisplayText("User: " + fs.getUser().getName()
					+ ", current dir: " + fs.getName(fs.getCurrent())
					+ ", type command below, help for help");
			try {
				String line = in.readLine();
				if (line == null)
					break;
				else if (line.length() != 0)
					parseAndExecute(line);
			} catch (IOException e) {
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
