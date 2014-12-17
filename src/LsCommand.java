import java.lang.StringBuilder;
import java.util.ArrayList;

public class LsCommand extends Command {
	ArrayList<String> paths;
	String cmdLine;
	boolean detailed;
	MultipleExceptionsException up;

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
	public void execute() throws MultipleExceptionsException {
		ArrayList<FSElement> elements = getElementsToDisplay();

		if (detailed)
			showDetailedInfo(elements);
		else
			showShortInfo(elements);

		if (up != null)
			throw up;
	}

	private void showDetailedInfo(ArrayList<FSElement> elements) {
		FileSystem fs = FileSystem.getInstance();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> owner = new ArrayList<String>();
		ArrayList<String> sizes = new ArrayList<String>();
		ArrayList<String> targets = new ArrayList<String>();

		for (FSElement e : elements) {
			names.add(fs.getName(e));
			owner.add(fs.getOwner(e));
			sizes.add(""+fs.getSize(e));

			if (fs.isLink(e))
				targets.add(" -> " + fs.getName(fs.getTarget((Link)e)));
			else
				targets.add("");
		}

		ArrayList<ArrayList<String>> columns = new ArrayList<ArrayList<String>>();
		columns.add(names);
		columns.add(owner);
		columns.add(sizes);
		columns.add(targets);

		TUIDisplay.columnDisplayText(columns);
	}

	private void showShortInfo(ArrayList<FSElement> elements) {
		FileSystem fs = FileSystem.getInstance();
		ArrayList<String> names = new ArrayList<String>();

		for (FSElement e : elements)
			names.add(fs.getName(e));

		TUIDisplay.arrayDisplayText(names);
	}

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
