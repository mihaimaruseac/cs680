import java.util.Arrays;

public abstract class CommandFactory {
	public static Command getCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";
		String[] tokens = cmd.trim().split(delims);

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
			case "lsusers": return buildLsUserCommand(tokens);
			case "mkdir": return buildMkDirCommand(tokens);
			case "mkuser": return buildMkUserCommand(tokens);
			case "passwd": return buildPasswdCommand(tokens);
			case "pgrant": return buildPathGrantCommand(tokens);
			case "pperm": return buildPathPermsCommand(tokens);
			case "prevoke": return buildPathRevokeCommand(tokens);
			case "pwd": return buildPWDCommand(tokens);
			case "redo": return buildRedoCommand(tokens);
			case "rm": return buildRmFileCommand(tokens);
			case "rmdir": return buildRmDirCommand(tokens);
			case "sort": return buildSortCommand(tokens);
			case "touch": return buildMkFileCommand(tokens);
			case "ugrant": return buildUserGrantCommand(tokens);
			case "undo": return buildUndoCommand(tokens);
			case "uperm": return buildUserPermsCommand(tokens);
			case "urevoke": return buildUserRevokeCommand(tokens);
		}

		throw new InvalidCommandException(tokens[0] + ": no such command");
	}

	private static ReadFileCommand buildCatCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new ReadFileCommand(tokens[1]);
	}

	private static WriteFileCommand buildWriteCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new WriteFileCommand(tokens[1]);
	}

	private static AppendFileCommand buildAppendCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Must have a file argument");
		return new AppendFileCommand(tokens[1]);
	}

	private static CdCommand buildCdCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			return new CdCommand();
		return new CdCommand(tokens[1]);
	}

	private static ChOwnCommand buildChOwnCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length < 3)
			throw new InvalidArgumentsCommandException("Must have at least two arguments: user and one path");
		return new ChOwnCommand(tokens);
	}

	private static ChUserCommand buildChUserCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Must have exactly one argument for the new user");
		return new ChUserCommand(tokens[1]);
	}

	private static DirCommand buildDirCommand(String[] tokens) {
		return new DirCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static SortCommand buildSortCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
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
		return new LsCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static LsUserCommand buildLsUserCommand(String[] tokens) {
		return new LsUserCommand();
	}

	private static MkLinkCommand buildLnCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length < 3)
			throw new InvalidArgumentsCommandException("Must have exactly two arguments: source and destination paths");
		return new MkLinkCommand(tokens[1], tokens[2]);
	}

	private static MkDirCommand buildMkDirCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one directory name argument");
		return new MkDirCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static MkUserCommand buildMkUserCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need exactly one username argument");
		return new MkUserCommand(tokens[1]);
	}

	private static MkFileCommand buildMkFileCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need at least one file name argument");
		return new MkFileCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
	}

	private static PasswdCommand buildPasswdCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			return new PasswdCommand();
		return new PasswdCommand(tokens[1]);
	}

	private static PathGrantCommand buildPathGrantCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 3)
			throw new InvalidArgumentsCommandException("Need exactly a path permission, a path and a user");

		FSPermissionType p = null;
		switch(tokens[1]) {
			case "rd": p = FSPermissionType.PERMISSION_READ; break;
			case "wr": p = FSPermissionType.PERMISSION_WRITE; break;
			case "rw": p = FSPermissionType.PERMISSION_READ_WRITE; break;
			default: throw new InvalidArgumentsCommandException("Expected permission string: 'rd', 'wr' or 'rw'");
		}

		return new PathGrantCommand(tokens[3], p, tokens[2]);
	}

	private static PathRevokeCommand buildPathRevokeCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 3)
			throw new InvalidArgumentsCommandException("Need exactly a path permission, a path and a user");

		FSPermissionType p = null;
		switch(tokens[1]) {
			case "rd": p = FSPermissionType.PERMISSION_READ; break;
			case "wr": p = FSPermissionType.PERMISSION_WRITE; break;
			case "rw": p = FSPermissionType.PERMISSION_READ_WRITE; break;
			default: throw new InvalidArgumentsCommandException("Expected permission string: 'rd', 'wr' or 'rw'");
		}

		return new PathRevokeCommand(tokens[3], p, tokens[2]);
	}

	private static FsPermsCommand buildPathPermsCommand(String[] tokens) {
		return new FsPermsCommand(Arrays.copyOfRange(tokens, 1, tokens.length));
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

	private static UserGrantCommand buildUserGrantCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length < 3)
			throw new InvalidArgumentsCommandException("Need exactly a user permission and a user");

		UserPermissionType p = null;
		switch(tokens[1]) {
			case "pw": p = UserPermissionType.PERMISSION_PASSWORD; break;
			case "gr": p = UserPermissionType.PERMISSION_GRANT; break;
			case "rv": p = UserPermissionType.PERMISSION_REVOKE; break;
			case "rt": p = UserPermissionType.PERMISSION_ROOT; break;
			default: throw new InvalidArgumentsCommandException("Expected permission string: 'pw', 'gr', 'rv' or 'rt'");
		}

		return new UserGrantCommand(tokens[2], p);
	}

	private static UserRevokeCommand buildUserRevokeCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length < 3)
			throw new InvalidArgumentsCommandException("Need exactly a user permission and a user");

		UserPermissionType p = null;
		switch(tokens[1]) {
			case "pw": p = UserPermissionType.PERMISSION_PASSWORD; break;
			case "gr": p = UserPermissionType.PERMISSION_GRANT; break;
			case "rv": p = UserPermissionType.PERMISSION_REVOKE; break;
			case "rt": p = UserPermissionType.PERMISSION_ROOT; break;
			default: throw new InvalidArgumentsCommandException("Expected permission string: 'pw', 'gr', 'rv' or 'rt'");
		}

		return new UserRevokeCommand(tokens[2], p);
	}

	private static UndoCommand buildUndoCommand(String[] tokens) {
		return new UndoCommand();
	}

	private static UserPermsCommand buildUserPermsCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length == 1)
			throw new InvalidArgumentsCommandException("Need one user name argument");
		return new UserPermsCommand(tokens[1]);
	}
}
