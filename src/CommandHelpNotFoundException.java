public class CommandHelpNotFoundException extends InvalidCommandException {
	public CommandHelpNotFoundException(String message) {
		super(message + ": command or command help not found");
	}
}
