public class PWDCommand extends Command {
	public PWDCommand() {
		cmdLine = "pwd";
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		System.out.println(fs.getName(fs.getCurrent()));
	}
}
