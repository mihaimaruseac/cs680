public class PWDCommand extends Command {
	public PWDCommand() {
	}

	@Override
	public String getCommandLine() {
		return "pwd";
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		System.out.println(fs.getName(fs.getCurrent()));
	}
}
