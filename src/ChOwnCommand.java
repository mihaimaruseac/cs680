import java.lang.StringBuilder;
import java.util.ArrayList;

public class ChOwnCommand extends Command {
	ArrayList<String> paths;
	String user;
	String cmdLine;

	public ChOwnCommand(String[] args) throws InvalidCommandException {
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
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();

		for (String path : paths) {
			FSElement element = fs.resolvePath(fs.getCurrent(), path);
			fs.setOwner(element, user);
		}
	}
}
