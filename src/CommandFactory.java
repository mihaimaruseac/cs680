import java.util.Arrays;

public abstract class CommandFactory {
	public static Command getCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";
		String[] tokens = cmd.split(delims);

		switch(tokens[0]) {
			case "chuser": return buildChUserCommand(tokens);
			case "dir": return buildLsCommand(tokens, true);
			case "exit": return buildExitCommand(tokens);
			case "help": return buildHelpCommand(tokens);
			case "history": return buildHistoryCommand(tokens);
			case "ls": return buildLsCommand(tokens, false);
			case "mkdir": return buildMkDirCommand(tokens);
			case "pwd": return buildPWDCommand(tokens);
			case "redo": return buildRedoCommand(tokens);
			case "undo": return buildUndoCommand(tokens);
			/*
			case "rmdir": return new RmDirCommand(tokens);
			case "touch": return new MkFileCommand(tokens);
			case "rm": return new RmFileCommand(tokens);

			case "cd": {
					   if (tokens.length == 1)
						   return new CdCommand();
					   return new CdCommand(tokens[1]);
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

	private static ChUserCommand buildChUserCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length != 2)
			throw new InvalidArgumentsCommandException("Must have exactly one argument for the new user");
		return new ChUserCommand(tokens[1]);
	}

	private static ExitCommand buildExitCommand(String[] tokens) {
		return new ExitCommand();
	}

	private static HelpCommand buildHelpCommand(String[] tokens) {
		return new HelpCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static HistoryCommand buildHistoryCommand(String[] tokens) {
		return new HistoryCommand();
	}

	private static LsCommand buildLsCommand(String[] tokens, boolean detailed) {
		return new LsCommand(tokens, detailed);
	}

	private static MkDirCommand buildMkDirCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one directory name argument");
		return new MkDirCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static PWDCommand buildPWDCommand(String[] tokens) {
		return new PWDCommand();
	}

	private static RedoCommand buildRedoCommand(String[] tokens) {
		return new RedoCommand();
	}

	private static UndoCommand buildUndoCommand(String[] tokens) {
		return new UndoCommand();
	}
}
