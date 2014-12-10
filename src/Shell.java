import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Shell {
	private boolean onGoing;
	private CommandHistory ch;

	public Shell() {
		onGoing = true;
		ch = new CommandHistory();
	}

	public void run() {
		System.out.println("Welcome to MMShell. Type exit or press ^D to exit. Type help for help");
		loop();
	}

	public void parseAndExecute(String cmd) throws InvalidCommandException {
		Command c = parseCommand(cmd);
		c.execute();
		if (c.goToHistory())
			ch.push(c);
	}

	public void stopShell() {
		onGoing = false;
	}

	private void loop() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (onGoing) {
			System.out.println("User: " + FileSystem.getInstance().getUser() + ", current dir: " + FileSystem.getInstance().getCurrent().getName() + ", type command below, help for help");
			try {
				String line = in.readLine();
				if (line == null)
					break;
				else if (line.length() != 0)
					parseAndExecute(line);
			} catch (Exception e) {
				System.out.println(e);
				//e.printStackTrace();
			}
		}
	}

	public Command parseCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";

		String[] tokens = cmd.split(delims);
		switch(tokens[0]) {
			case "exit": return new ExitCommand(this);
			case "help": return new HelpCommand();
			case "pwd": return new PWDCommand();
			case "ls": return new LsCommand(tokens);
			case "dir": return new LsCommand(tokens, true);
			case "mkdir": return new MkDirCommand(tokens);
			case "rmdir": return new RmDirCommand(tokens);
			case "touch": return new MkFileCommand(tokens);
			case "rm": return new RmFileCommand(tokens);
			case "history": return new HistoryCommand(ch);
			case "redo": return new RedoCommand(ch.peek());
			case "undo": return new UndoCommand(ch.peek());

			case "cd": {
					   if (tokens.length == 1)
						   return new CdCommand();
					   return new CdCommand(tokens[1]);
			}

			case "chuser": {
					   if (tokens.length != 2)
						   throw new InvalidCommandException("Must have exactly one argument for the new user");
					   return new ChUserCommand(tokens[1]);
			}

			case "chown": {
					   if (tokens.length <= 2)
						   throw new InvalidCommandException("Must have at least two arguments: user and one path");
					   return new ChOwnCommand(tokens);
			}

			case "ln": {
					   if (tokens.length != 3)
						   throw new InvalidCommandException("Must have exactly two arguments: source and destination paths");
					   return new LnCommand(tokens[1], tokens[2]);
			}

			case "sort": {
					   if (tokens.length != 3)
						   throw new InvalidCommandException("Must have exactly two arguments: method and directory");
					   return new SortCommand(tokens[1], tokens[2]);
			}
		}

		throw new InvalidCommandException(tokens[0] + ": no such command");
	}

	public static void main(String args[]) {
		new Shell().run();
	}
}
