import java.lang.StringBuilder;
import java.util.ArrayList;

public class RmDirCommand extends Command {
	ArrayList<String> dirs;
	String cmdLine;

	public RmDirCommand(String[] args) throws InvalidCommandException {
		StringBuilder sb = new StringBuilder("rmdir");
		dirs = new ArrayList<String>();

		if (args.length == 1)
			throw new InvalidCommandException("Need at least one directory name argument");

		for (int i = 1; i < args.length; i++) {
			dirs.add(args[i]);
			sb.append(" " + args[i]);
		}

		cmdLine = sb.toString();
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();

		for (String name : dirs)
			fs.removeDirectory(current, name);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();
		String owner = fs.getUser();

		try {
			for (String name : dirs)
				fs.addChild(current, new Directory(name, current, owner));
		} catch (ElementExistsException e) {
			throw new InvalidCommandException(e.getMessage());
		}
	}
}
