import java.lang.StringBuilder;
import java.util.ArrayList;

public class RmFileCommand extends Command {
	ArrayList<String> files;
	String cmdLine;

	public RmFileCommand(String[] args) {
		StringBuilder sb = new StringBuilder("rm");
		files = new ArrayList<String>();

		for (String s: args) {
			files.add(s);
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

		for (String path : files) {
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
			*/
	}
}
