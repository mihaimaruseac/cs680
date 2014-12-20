import java.util.ArrayList;
import java.util.Collections;

public class HelpCommand extends Command {
	ArrayList<String> commands;
	ArrayList<String> cmndArgs;
	ArrayList<String> helpText;

	String []args;

	public HelpCommand(String[] args) {
		setup();
		this.args = args;
		cmdLine = "help";

		for (String s : args)
			cmdLine += " " + s;
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		if (args.length == 0)
			TUIDisplay.arrayDisplayText(commands);
		else {
			ArrayList<ArrayList<String>> columns = new ArrayList<ArrayList<String>>();
			ArrayList<String> notFounds = new ArrayList<String>();

			for (int i = 0; i < 3; i++)
				columns.add(new ArrayList<String>());

			for (String arg : args) {
				int i = Collections.binarySearch(commands, arg);
				if (i < 0) {
					notFounds.add(arg);
				} else {
					columns.get(0).add(commands.get(i));
					columns.get(1).add(cmndArgs.get(i));
					columns.get(2).add(helpText.get(i));
				}
			}

			TUIDisplay.columnDisplayText(columns);
			if (notFounds.size() > 0) {
				MultipleExceptionsException up = new MultipleExceptionsException();
				for (String cnf : notFounds)
					up.addException(new CommandHelpNotFoundException(cnf));
				throw up;
			}
		}
	}

	private void setup() {
		commands = new ArrayList<String>();
		cmndArgs = new ArrayList<String>();
		helpText = new ArrayList<String>();

		for (String s : new String[] {
			"append@<file>@append text to file",
			"cat@<file>@show contents of file",
			"cd@<dir>@change current directory",
			"chown@<user> <path> [<path_list>]@change owner of filesystem element(s)",
			"chuser@<user>@change current user",
			"dir@[<paths>]@show detailed contents of paths",
			"edit@<file>@replace contents of file",
			"exit@@leave shell, terminate program",
			"help@[<cmd_list>]@show help message for all/argument commands",
			"history@@show command history",
			"ln@<src_path> <dst_path>@make a symbolic link",
			"ls@[<paths>]@show summary contents of paths",
			"lsusers@@list all users in the shell",
			"mkdir@<dir> [<dir_list>]@create directory(ies)",
			"mkuser@<user>@make new user",
			"passwd@[<user>]@change password of (current) user",
			"pgrant@<pperm> <path> <user>@grant path permission to user",
			"pperm@[<path_list>]@show permissions of current user on paths",
			"prevoke@<pperm> <path> <user>@revoke path permission from user",
			"pwd@@show current directory",
			"redo@@redo last command",
			"rm@<file> [<file_list>]@remove file(s)",
			"rmdir@<dir> [<dir_list>]@remove empty directory(ies)",
			"sort@<method> <directory>@displays detailed info in sorted order",
			"touch@<file> [<file_list>]@create file(s)",
			"ugrant@<uperm> <user>@give user permission to user",
			"undo@@undo last command",
			"uperm@<user>@display user-permissions of user",
			"urevoke@<uperm> <user>@revoke user permission from user",
		}) {
			String delim="@";
			String []parts = s.split(delim);
			commands.add(parts[0]);
			cmndArgs.add(parts[1]);
			helpText.add(parts[2]);
		}
	}
}
