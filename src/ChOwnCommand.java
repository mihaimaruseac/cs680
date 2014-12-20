import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChOwnCommand extends Command {
	ArrayList<String> paths;
	String user;

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
	protected void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

		for (String path : paths) {
			try {
				FSElement element = fs.resolvePath(path);
				User u = fs.getUserByName(user);
				fs.setOwner(element, u);
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();

				up.addException(e);
			} catch (UserNotFoundException e) {
				if (up == null)
					up = new MultipleExceptionsException();

				up.addException(e);
			}
		}

		if (up != null)
			throw up;
	}
}
