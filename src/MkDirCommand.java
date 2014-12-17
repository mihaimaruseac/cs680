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
	public void execute() throws MultipleExceptionsException {
		/*
		FileSystem fs = FileSystem.getInstance();
		String errors = "";

		for (String name : dirs)
			try {
				fs.createDirectory(name);
			} catch (ElementExistsException e) {
				errors += name + " ";
			}

		if (errors.length() > 0)
			throw new ElementExistsException(errors);
			*/
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		/*
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();

		for (String name : dirs)
			fs.removeDirectory(current, name);
		*/
	}
}
