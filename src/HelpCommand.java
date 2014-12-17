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
	}

	@Override
	public String getCommandLine() {
		String cmdLine = "help";

		for (String s : args)
			cmdLine += " " + s;

		return cmdLine;
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		/*
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
			if (notFounds.size() > 0)
				showErrors(notFounds);
		}
		*/
	}

	private void showErrors(ArrayList<String> notFounds) throws CommandHelpNotFoundException {
		String cnfHelp = ": command help not found\n";
		String err = "";

		for (String cnf : notFounds)
			err += cnf + cnfHelp;

		throw new CommandHelpNotFoundException(err);
	}

	private void setup() {
		commands = new ArrayList<String>();
		cmndArgs = new ArrayList<String>();
		helpText = new ArrayList<String>();

		for (String s : new String[] {
			"cd@<dir>@change current directory",
			"chown@<user> <path> [<path_list>]@change owner of filesystem element(s)",
			"chuser@<user>@change current user",
			"dir@[<paths>]@show detailed contents of paths",
			"exit@@leave shell, terminate program",
			"help@[<cmd_list>]@show help message for all/argument commands",
			"history@@show command history",
			"ls@[<paths>]@show summary contents of paths",
			"mkdir@<dir> [<dir_list>]@create directory(ies) in current directory",
			"pwd@@show current directory",
			"redo@@redo last command",
			"rm@<file> [<file_list>]@remove files in current directory",
			"rmdir@<dir> [<dir_list>]@remove empty directories in current directory",
			"touch@<file> [<file_list>]@create file(s) in current directory",
			"undo@@undo last command",
		}) {
			String delim="@";
			String []parts = s.split(delim);
			commands.add(parts[0]);
			cmndArgs.add(parts[1]);
			helpText.add(parts[2]);
		}

		/*
		System.out.println("\tln <src_path> <dst_path>\tmake a symbolic link");
		System.out.println("\tsort <method> <directory>\tdisplays detailed info of directory contents in sorted order");
		*/
	}
}
