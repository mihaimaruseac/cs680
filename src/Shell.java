import java.io.IOError;
import java.util.ArrayList;

public class Shell {
	private Serializer s;

	public Shell(Serializer s) {
		this.s = s;
	}

	private void run() {
		loop();
	}

	private void parseAndExecute(String cmd) throws MultipleExceptionsException {
		Command c = null;

		try {
			c = CommandFactory.getCommand(cmd);
		} catch (InvalidCommandException ice) {
			throw new MultipleExceptionsException(ice);
		}

		if (c.goesToHistory())
			CommandHistory.getInstance().push(c);

		c.executeCommand();
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
			} catch (MultipleExceptionsException e) {
				TUIDisplay.simpleDisplayText(e.getMessage());
			} catch (Exception e) {
				TUIDisplay.simpleDisplayText(e.toString());
				e.printStackTrace();
			} finally {
				s.serialize();
			}
		}
	}

	public static void main(String args[]) {
		try {
			Serializer s = new Serializer();
			try {
				s.deserialize();
			} catch (Exception e) {
				e.printStackTrace();
				TUIDisplay.simpleDisplayText("Cannot initialize from serialized state, will reset");

				ArrayList<User> users = new ArrayList<User>();

				User root = new User("root", "TODO");
				root.addPermission(UserPermissionType.PERMISSION_ROOT);
				users.add(root);

				FileSystem.getInstance().setUpUsers(users);
				FileSystem.getInstance().setUp();
			}

			new Shell(s).run();
		} catch (Exception e) {
			TUIDisplay.simpleDisplayText("Impossible to start application. Check key configuration and serialized file");
		}
	}
}
