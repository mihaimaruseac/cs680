import java.util.ArrayList;
import java.util.Arrays;

public class HelpCommand extends Command {
	String []commands;
	String []cmndArgs;
	String []helpText;

	String []args;

	public HelpCommand(String[] args) {
		setup();
		this.args = args;
	}

	@Override
	public String getCommandLine() {
		return "help";
	}

	@Override
	public boolean goesToHistory() {
		return false;
	}

	@Override
	public void execute() throws CommandHelpNotFoundException {
		if (args.length == 0)
			TUIDisplay.arrayDisplayText(commands);
		else {
			ArrayList<ArrayList<String>> columns = new ArrayList<ArrayList<String>>();
			ArrayList<String> notFounds = new ArrayList<String>();

			for (int i = 0; i < 3; i++)
				columns.add(new ArrayList<String>());

			for (String arg : args) {
				int i = Arrays.binarySearch(commands, arg);
				if (i < 0) {
					notFounds.add(arg);
				} else {
					columns.get(0).add(commands[i]);
					columns.get(1).add(cmndArgs[i]);
					columns.get(2).add(helpText[i]);
				}
			}

			TUIDisplay.columnDisplayText(columns);
			if (notFounds.size() > 0)
				showErrors(notFounds);
		}
	}

	private void showErrors(ArrayList<String> notFounds) throws CommandHelpNotFoundException {
		String cnfHelp = ": command help not found\n";
		String err = "";

		for (String cnf : notFounds)
			err += cnf + cnfHelp;

		throw new CommandHelpNotFoundException(err);
	}

	private void setup() {
		commands = new String[] {
			"exit",
			"help",
		};

		cmndArgs = new String[] {
			"",
			"[<cmd_list>]",
		};

		helpText = new String[] {
			"leave shell, terminate program",
			"show help message for all/argument commands",
		};

		/*
		System.out.println("\thistory\t\t\t\tshow command history");
		System.out.println("\tredo\t\t\t\tredo last command");
		System.out.println("\tundo\t\t\t\ttry to undo last command");
		System.out.println("\tcd [<dir>]\t\t\tchange directory");
		System.out.println("\tdir [<paths>]\t\t\tshow detailed contents of paths");
		System.out.println("\tls [<paths>]\t\t\tshow summary contents of paths");
		System.out.println("\tln <src_path> <dst_path>\tmake a symbolic link");
		System.out.println("\tmkdir <dir> [<dirs>]\t\tcreate directory in current directory");
		System.out.println("\tpwd\t\t\t\tshow current directory");
		System.out.println("\trm <file> [<files>]\t\tremove files in current directory");
		System.out.println("\trmdir <dir> [<dirs>]\t\tremove empty directories in current directory");
		System.out.println("\tsort <method> <directory>\tdisplays detailed info of directory contents in sorted order");
		System.out.println("\ttouch <file> [<files>]\t\tcreate files in current directory");
		System.out.println("\tchuser <user>\t\t\tchange current user");
		System.out.println("\tchown <user> <path> [<paths>]\tchange owner of filesystem element(s)");
		*/
	}
}
