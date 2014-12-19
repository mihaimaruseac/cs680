public class PWDCommand extends Command {
	public PWDCommand() {
		cmdLine = "pwd";
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		TUIDisplay.simpleDisplayText(fs.getName(fs.getCurrent()));
	}
}
