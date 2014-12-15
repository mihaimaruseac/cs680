import java.util.Arrays;

public class CommandFactory {
	private static CommandFactory instance = null;

	private CommandFactory() {
	}

	public static CommandFactory getInstance() {
		if (instance == null)
			instance = new CommandFactory();
		return instance;
	}

	public Command getCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";
		String[] tokens = cmd.split(delims);

		switch(tokens[0]) {
			case "exit": return buildExitCommand(tokens);
			case "help": return buildHelpCommand(tokens);
			/*
			case "pwd": return new PWDCommand();
			case "ls": return new LsCommand(tokens);
			case "dir": return new LsCommand(tokens, true);
			case "mkdir": return new MkDirCommand(tokens);
			case "rmdir": return new RmDirCommand(tokens);
			case "touch": return new MkFileCommand(tokens);
			case "rm": return new RmFileCommand(tokens);
			case "history": return new HistoryCommand();
			case "redo": return new RedoCommand();
			case "undo": return new UndoCommand();

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
			*/
		}

		throw new InvalidCommandException(tokens[0] + ": no such command");
	}

	private ExitCommand buildExitCommand(String[] tokens) {
		return new ExitCommand();
	}

	private HelpCommand buildHelpCommand(String[] tokens) {
		return new HelpCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}
}
