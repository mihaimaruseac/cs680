import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChOwnCommand extends Command {
	ArrayList<String> paths;
	String user;
	String cmdLine;

	public ChOwnCommand(String[] args) {
		user = args[1];
		StringBuilder sb = new StringBuilder("chown " + user);
		paths = new ArrayList<String>();

		for (int i = 2; i < args.length; i++) {
			paths.add(args[i]);
			sb.append(" " + args[i]);
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

		for (String path : paths) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();

				up.addException(e);
				continue;
			}

			fs.setOwner(element, user);
		}

	}
}
