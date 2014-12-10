public class HelpCommand extends Command {
	public HelpCommand() {
	}

	@Override
	public String getCommandLine() {
		return "help";
	}

	@Override
	public boolean goToHistory() {
		return false;
	}

	@Override
	public void execute() throws InvalidCommandException {
		System.out.println("Possible shell commands:");
		System.out.println("\texit\t\t\t\tleave shell");
		System.out.println("\thelp\t\t\t\tshow help (this message)");
		System.out.println("\thistory\t\t\t\tshow command history");
		System.out.println("\tredo\t\t\t\tredo last command");
		System.out.println("\tundo\t\t\t\ttry to undo last command");
		System.out.println("\t----");
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
		System.out.println("\t----");
		System.out.println("\tchuser <user>\t\t\tchange current user");
		System.out.println("\tchown <user> <path> [<paths>]\tchange owner of filesystem element(s)");
	}
}
