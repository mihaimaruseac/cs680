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
		ArrayList<FSElement> elements = getElementsToDisplay();

		if (detailed)
			showDetailedInfo(elements);
		else
			showShortInfo(elements);
	}

	private void showDetailedInfo(ArrayList<FSElement> elements) {
		// TODO:
	}

	private void showShortInfo(ArrayList<FSElement> elements) {
		// TODO:
	}

	private ArrayList<FSElement> getElementsToDisplay() throws InvalidPathException {
		ArrayList<FSElement> els = new ArrayList<FSElement>();
		FileSystem fs = FileSystem.getInstance();

		if (paths.size() == 0)
			addContentsOfDir(els, fs.getCurrent());
		else
			for (String path: paths) {
				FSElement element = fs.resolvePath(fs.getCurrent(), path);
				if (fs.isLeaf(element))
					els.add(element);
				else
					addContentsOfDir(els, (Directory)element);
			}

		return els;
	}

	private void addContentsOfDir(ArrayList<FSElement> els, Directory dir) {
		els.addAll(FileSystem.getInstance().getChildren(dir));
	}
}
