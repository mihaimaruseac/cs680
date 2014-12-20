import java.io.IOError;
import java.util.ArrayList;

public class Shell {
	private static Shell instance = null;

	private Shell() {
	}

	public static Shell getInstance() {
		if (instance == null)
			instance = new Shell();
		return instance;
	}

	private void run() {
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
		while (fs.hasMoreUsers()) {
			String prompt = "[" + fs.getUser().getName() + " " + fs.getCurrent().getName() + "]> ";
			System.console().printf(prompt);
			System.console().flush();

			try {
				String line = System.console().readLine();
				if (line == null) {
					line = "exit";
					TUIDisplay.simpleDisplayText("");
				}

				if (line.length() != 0)
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

		User root = new User("root", "TODO");
		root.addPermission(UserPermissionType.PERMISSION_ROOT);
		users.add(root);

		FileSystem.getInstance().setUpUsers(users);
		FileSystem.getInstance().setUp();
		Shell.getInstance().run();
	}
}
