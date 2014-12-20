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
		MultipleExceptionsException up = new MultipleExceptionsException();

		for (String path : paths) {
			try {
				FSElement element = fs.resolvePath(path);
				User u = fs.getUserByName(user);
				if (!fs.isAllowedChOwn(u, element))
					throw new AccessDeniedException("Not allowed to change ownership for " + path);
				fs.setOwner(element, u);
			} catch (InvalidCommandException e) {
				up.addException(e);
			}
		}

		if (up.isException())
			throw up;
	}
}
