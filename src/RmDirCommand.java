import java.lang.StringBuilder;
import java.util.ArrayList;

public class RmDirCommand extends Command {
	ArrayList<String> dirs;
	String cmdLine;

	public RmDirCommand(String[] args) {
		StringBuilder sb = new StringBuilder("rmdir");
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
	public void execute() throws InvalidPathException {
		FileSystem fs = FileSystem.getInstance();
		String errors = "";

		for (String path : dirs) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
			} catch (InvalidPathException e) {
				errors += path + " ";
				continue;
			}

			fs.remove(element);
		}

		if (errors.length() > 0)
			throw new InvalidPathException(errors);
	}
}
