public class PWDCommand extends Command {
	public PWDCommand() {
	}

	@Override
	public String getCommandLine() {
		return "pwd";
	}

	@Override
	public void execute() throws InvalidCommandException {
		System.out.println(FileSystem.getInstance().getCurrent().getName());
	}
}
