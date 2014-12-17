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
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

		for (String path : dirs) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			if (fs.isLeaf(element)) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(new InvalidArgumentsCommandException(path + ": not a directory"));
				continue;
			}

			if (!fs.isEmptyDir((Directory)element)) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(new InvalidArgumentsCommandException(path + ": not an empty directory"));
				continue;
			}

			fs.remove(element);
		}

		if (up != null)
			throw up;
	}
}
