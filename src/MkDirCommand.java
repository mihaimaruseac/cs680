import java.lang.StringBuilder;
import java.util.ArrayList;

public class MkDirCommand extends Command {
	ArrayList<String> dirs;
	String cmdLine;

	public MkDirCommand(String[] args) {
		StringBuilder sb = new StringBuilder("mkdir");
		dirs = new ArrayList<String>();

		for (String s: args) {
			dirs.add(s);
			sb.append(" " + s);
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
		String owner = fs.getUser();

		/*
		try {
			for (String name : dirs)
				fs.addChild(current, new Directory(name, current, owner));
		} catch (ElementExistsException e) {
			throw new InvalidCommandException(e.getMessage());
		}
		*/
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws InvalidCommandException {
		/*
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();

		for (String name : dirs)
			fs.removeDirectory(current, name);
		*/
	}
}
