import java.util.Arrays;

public abstract class CommandFactory {
	public static Command getCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";
		String[] tokens = cmd.split(delims);

		switch(tokens[0]) {
			case "append": return buildAppendCommand(tokens);
			case "cat": return buildCatCommand(tokens);
			case "cd": return buildCdCommand(tokens);
			case "chown": return buildChOwnCommand(tokens);
			case "chuser": return buildChUserCommand(tokens);
			case "dir": return buildDirCommand(tokens);
			case "edit": return buildWriteCommand(tokens);
			case "exit": return buildExitCommand(tokens);
			case "help": return buildHelpCommand(tokens);
			case "history": return buildHistoryCommand(tokens);
			case "ln": return buildLnCommand(tokens);
			case "ls": return buildLsCommand(tokens);
			case "mkdir": return buildMkDirCommand(tokens);
			case "pwd": return buildPWDCommand(tokens);
			case "redo": return buildRedoCommand(tokens);
			case "rm": return buildRmFileCommand(tokens);
			case "rmdir": return buildRmDirCommand(tokens);
			case "sort": return buildSortCommand(tokens);
			case "touch": return buildMkFileCommand(tokens);
			case "undo": return buildUndoCommand(tokens);
		}

		throw new InvalidCommandException(tokens[0] + ": no such command");
	}

	private static ReadFileCommand buildCatCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new ReadFileCommand(tokens[1]);
	}

	private static WriteFileCommand buildWriteCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new WriteFileCommand(tokens[1]);
	}

	private static AppendFileCommand buildAppendCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new AppendFileCommand(tokens[1]);
	}

	private static CdCommand buildCdCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			return new CdCommand();
		return new CdCommand(tokens[1]);
	}

	private static ChOwnCommand buildChOwnCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 2)
			throw new InvalidArgumentsCommandException("Must have at least two arguments: user and one path");
		return new ChOwnCommand(tokens);
	}

	private static ChUserCommand buildChUserCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length != 2)
			throw new InvalidArgumentsCommandException("Must have exactly one argument for the new user");
		return new ChUserCommand(tokens[1]);
	}

	private static DirCommand buildDirCommand(String[] tokens) {
		return new DirCommand(tokens);
	}

	private static SortCommand buildSortCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 1)
			throw new InvalidArgumentsCommandException("Must have at least the sorting method argument");
		return new SortCommand(tokens[1], Arrays.copyOfRange(tokens, 2, tokens.length));
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

	private static LsCommand buildLsCommand(String[] tokens) {
		return new LsCommand(tokens);
	}

	private static MkLinkCommand buildLnCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length != 3)
			throw new InvalidArgumentsCommandException("Must have exactly two arguments: source and destination paths");
		return new MkLinkCommand(tokens[1], tokens[2]);
	}

	private static MkDirCommand buildMkDirCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one directory name argument");
		return new MkDirCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static MkFileCommand buildMkFileCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one file name argument");
		return new MkFileCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static PWDCommand buildPWDCommand(String[] tokens) {
		return new PWDCommand();
	}

	private static RedoCommand buildRedoCommand(String[] tokens) {
		return new RedoCommand();
	}

	private static RmDirCommand buildRmDirCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one directory name argument");
		return new RmDirCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static RmFileCommand buildRmFileCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one file name argument");
		return new RmFileCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static UndoCommand buildUndoCommand(String[] tokens) {
		return new UndoCommand();
	}
}
