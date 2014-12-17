import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChOwnCommand extends Command {
	ArrayList<String> paths;
	ArrayList<FSElement> elements;
	ArrayList<String> owners;
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
		elements = new ArrayList<FSElement>();
		owners = new ArrayList<String>();
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidPathException {
		FileSystem fs = FileSystem.getInstance();
		String errors = "";

		for (String path : paths) {
			FSElement element = null;

			try {
				element = fs.resolvePath(path);
			} catch (InvalidPathException e) {
				errors += path + " ";
				continue;
			}

			elements.add(element);
			owners.add(fs.getOwner(element));
			fs.setOwner(element, user);
		}

		if (errors.length() > 0)
			throw new InvalidPathException(errors);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() {
		FileSystem fs = FileSystem.getInstance();

		for (int i = 0; i < owners.size(); i++)
			fs.setOwner(elements.get(i), owners.get(i));
	}
}
