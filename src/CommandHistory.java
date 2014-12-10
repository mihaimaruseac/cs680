import java.util.Stack;

public class CommandHistory {
	public final int MAX_COMMAND_SIZE = 10;
	Stack<Command> commands;

	public CommandHistory() {
		commands = new Stack<Command>();
	}

	public void push(Command c) {
		commands.push(c);
		if (commands.size() > MAX_COMMAND_SIZE)
			commands.remove(0);
	}

	public Command peek() {
		return commands.peek();
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
