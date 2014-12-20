import java.lang.StringBuilder;
import java.util.ArrayList;

public abstract class RmCommand extends Command {
	ArrayList<String> paths;

	public RmCommand(String[] args, String base) {
		StringBuilder sb = new StringBuilder(base);
		paths = new ArrayList<String>();

		for (String s: args) {
			paths.add(s);
			sb.append(" " + s);
		}

		cmdLine = sb.toString();
	}

	@Override
	protected void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = new MultipleExceptionsException();

		for (String path : paths) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
				validateElement(path, element);

				if (!fs.isAllowed(element.getParent(), FSPermissionType.PERMISSION_WRITE))
					throw new AccessDeniedException("Cannot access " + element.getParent().getName() + " for writing");

				fs.remove(element);
			} catch (InvalidCommandException e) {
				up.addException(e);
			}
		}

		if (up.isException())
			throw up;
	}

	protected abstract void validateElement(String path, FSElement element) throws InvalidArgumentsCommandException;
}
