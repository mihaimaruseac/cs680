import java.util.Arrays;

public abstract class CommandFactory {
	public static Command getCommand(String cmd) throws InvalidCommandException {
		String delims = "[ ]+";
		String[] tokens = cmd.trim().split(delims);

		if (tokens[0].equals("append")) return buildAppendCommand(tokens);
		else if (tokens[0].equals("cat")) return buildCatCommand(tokens);
		else if (tokens[0].equals("cd")) return buildCdCommand(tokens);
		else if (tokens[0].equals("chown")) return buildChOwnCommand(tokens);
		else if (tokens[0].equals("chuser")) return buildChUserCommand(tokens);
		else if (tokens[0].equals("dir")) return buildDirCommand(tokens);
		else if (tokens[0].equals("edit")) return buildWriteCommand(tokens);
		else if (tokens[0].equals("exit")) return buildExitCommand(tokens);
		else if (tokens[0].equals("help")) return buildHelpCommand(tokens);
		else if (tokens[0].equals("history")) return buildHistoryCommand(tokens);
		else if (tokens[0].equals("ln")) return buildLnCommand(tokens);
		else if (tokens[0].equals("ls")) return buildLsCommand(tokens);
		else if (tokens[0].equals("lsusers")) return buildLsUserCommand(tokens);
		else if (tokens[0].equals("mkdir")) return buildMkDirCommand(tokens);
		else if (tokens[0].equals("mkuser")) return buildMkUserCommand(tokens);
		else if (tokens[0].equals("passwd")) return buildPasswdCommand(tokens);
		else if (tokens[0].equals("pgrant")) return buildPathGrantCommand(tokens);
		else if (tokens[0].equals("pperm")) return buildPathPermsCommand(tokens);
		else if (tokens[0].equals("prevoke")) return buildPathRevokeCommand(tokens);
		else if (tokens[0].equals("pwd")) return buildPWDCommand(tokens);
		else if (tokens[0].equals("redo")) return buildRedoCommand(tokens);
		else if (tokens[0].equals("rm")) return buildRmFileCommand(tokens);
		else if (tokens[0].equals("rmdir")) return buildRmDirCommand(tokens);
		else if (tokens[0].equals("sort")) return buildSortCommand(tokens);
		else if (tokens[0].equals("touch")) return buildMkFileCommand(tokens);
		else if (tokens[0].equals("ugrant")) return buildUserGrantCommand(tokens);
		else if (tokens[0].equals("undo")) return buildUndoCommand(tokens);
		else if (tokens[0].equals("uperm")) return buildUserPermsCommand(tokens);
		else if (tokens[0].equals("urevoke")) return buildUserRevokeCommand(tokens);
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
		if (tokens[1].equals("rd")) p = FSPermissionType.PERMISSION_READ;
		else if (tokens[1].equals("wr")) p = FSPermissionType.PERMISSION_WRITE;
		else if (tokens[1].equals("rw")) p = FSPermissionType.PERMISSION_READ_WRITE;
		else throw new InvalidArgumentsCommandException("Expected permission string: 'rd', 'wr' or 'rw'");
		return new PathGrantCommand(tokens[3], p, tokens[2]);
	}

	private static PathRevokeCommand buildPathRevokeCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length <= 3)
			throw new InvalidArgumentsCommandException("Need exactly a path permission, a path and a user");

		FSPermissionType p = null;
		if (tokens[1].equals("rd")) p = FSPermissionType.PERMISSION_READ;
		else if (tokens[1].equals("wr")) p = FSPermissionType.PERMISSION_WRITE;
		else if (tokens[1].equals("rw")) p = FSPermissionType.PERMISSION_READ_WRITE;
		else throw new InvalidArgumentsCommandException("Expected permission string: 'rd', 'wr' or 'rw'");
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
		if (tokens[1].equals("pw")) p = UserPermissionType.PERMISSION_PASSWORD;
		else if (tokens[1].equals("gr")) p = UserPermissionType.PERMISSION_GRANT;
		else if (tokens[1].equals("rv")) p = UserPermissionType.PERMISSION_REVOKE;
		else if (tokens[1].equals("rt")) p = UserPermissionType.PERMISSION_ROOT;
		else throw new InvalidArgumentsCommandException("Expected permission string: 'pw', 'gr', 'rv' or 'rt'");
		return new UserGrantCommand(tokens[2], p);
	}

	private static UserRevokeCommand buildUserRevokeCommand(String[] tokens) throws InvalidArgumentsCommandException {
		if (tokens.length < 3)
			throw new InvalidArgumentsCommandException("Need exactly a user permission and a user");

		UserPermissionType p = null;
		if (tokens[1].equals("pw")) p = UserPermissionType.PERMISSION_PASSWORD;
		else if (tokens[1].equals("gr")) p = UserPermissionType.PERMISSION_GRANT;
		else if (tokens[1].equals("rv")) p = UserPermissionType.PERMISSION_REVOKE;
		else if (tokens[1].equals("rt")) p = UserPermissionType.PERMISSION_ROOT;
		else throw new InvalidArgumentsCommandException("Expected permission string: 'pw', 'gr', 'rv' or 'rt'");
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
