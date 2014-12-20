import java.lang.StringBuilder;
import java.util.ArrayList;

public abstract class ListCommand extends Command {
	ArrayList<String> paths;
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
	protected void execute() throws MultipleExceptionsException {
		ArrayList<FSElement> elements = getElementsToDisplay();
		showInfo(elements);

		if (up.isException())
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
				try {
					FSElement element = null;
					element = fs.resolvePath(path);

					if (fs.isLeaf(element))
						if (fs.isAllowed(element.getParent(), FSPermissionType.PERMISSION_READ))
							els.add(element);
						else
							throw new AccessDeniedException("Cannot access " + element.getParent().getName() + " for reading contents");
					else
						addContentsOfDir(els, (Directory)element);
				} catch (InvalidCommandException e) {
					up.addException(e);
				}
			}

		return els;
	}

	private void addContentsOfDir(ArrayList<FSElement> els, Directory dir) {
		if (FileSystem.getInstance().isAllowed(dir, FSPermissionType.PERMISSION_READ))
			els.addAll(FileSystem.getInstance().getChildren(dir));
		else {
			up.addException(new AccessDeniedException("Cannot access " + dir.getName() + " for reading contents"));
		}
	}
}
