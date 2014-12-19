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
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

		for (String path : paths) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
				validateElement(path, element);
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			} catch (InvalidArgumentsCommandException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			fs.remove(element);
		}

		if (up != null)
			throw up;
	}

	protected abstract void validateElement(String path, FSElement element) throws InvalidArgumentsCommandException;
}
