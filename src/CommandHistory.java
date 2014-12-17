import java.util.Stack;

public class CommandHistory {
	public final static int MAX_COMMAND_SIZE = 10;
	private static CommandHistory instance = null;
	Stack<Command> commands;

	private CommandHistory() {
		commands = new Stack<Command>();
	}

	public static CommandHistory getInstance() {
		if (instance == null)
			instance = new CommandHistory();
		return instance;
	}

	public void push(Command c) {
		commands.push(c);
		if (commands.size() > MAX_COMMAND_SIZE)
			commands.remove(0);
	}

	public Command peek() {
		if (commands.size() > 0)
			return commands.peek();
		return null;
	}

	public void printCommands() {
		for (Command c : commands) {
			if (c.canUndo())
				System.out.print(" [U] ");
			else
				System.out.print(" [ ] ");
			System.out.println(c.getCommandLine());
		}
	}
}
