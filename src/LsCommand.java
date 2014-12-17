import java.lang.StringBuilder;
import java.util.ArrayList;

public class LsCommand extends Command {
	ArrayList<String> paths;
	String cmdLine;
	boolean detailed;

	public LsCommand(String[] args) {
		this(args, false);
	}

	public LsCommand(String[] args, boolean detailed) {
		this.detailed = detailed;

		StringBuilder sb = new StringBuilder(detailed?"dir":"ls");
		paths = new ArrayList<String>();

		for (int i = 1; i < args.length; i++) {
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
		/*
		FileSystem fs = FileSystem.getInstance();

		if (paths.size() == 0)
			fs.showElement(fs.getCurrent(), detailed);
		else
			for (String path : paths) {
				FSElement element = fs.resolvePath(fs.getCurrent(), path);
				fs.showElement(element, detailed);
			}
		*/
	}
}
