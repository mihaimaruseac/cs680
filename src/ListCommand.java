import java.lang.StringBuilder;
import java.util.ArrayList;

public abstract class ListCommand extends Command {
	ArrayList<String> paths;
	String cmdLine;
	MultipleExceptionsException up;

	public ListCommand(String[] args, String base) {
		StringBuilder sb = new StringBuilder(base);
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
	public void execute() throws MultipleExceptionsException {
		ArrayList<FSElement> elements = getElementsToDisplay();
		showInfo(elements);

		if (up != null)
			throw up;
	}

	protected abstract void showInfo(ArrayList<FSElement> elements);

	private ArrayList<FSElement> getElementsToDisplay() {
		ArrayList<FSElement> els = new ArrayList<FSElement>();
		FileSystem fs = FileSystem.getInstance();

		if (paths.size() == 0)
			addContentsOfDir(els, fs.getCurrent());
		else
			for (String path: paths) {
				FSElement element = null;

				try {
					element = fs.resolvePath(path);
				} catch (InvalidPathException e) {
					if (up == null)
						up = new MultipleExceptionsException();
					up.addException(e);
					continue;
				}

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
