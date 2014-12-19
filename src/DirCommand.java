import java.util.ArrayList;

public class DirCommand extends ListCommand {
	public DirCommand(String[] args) {
		super(args, "dir");
	}

	protected DirCommand(String[] args, String base) {
		super(args, base);
	}

	@Override
	protected void showInfo(ArrayList<FSElement> elements) {
		FileSystem fs = FileSystem.getInstance();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> owner = new ArrayList<String>();
		ArrayList<String> sizes = new ArrayList<String>();
		ArrayList<String> targets = new ArrayList<String>();

		for (FSElement e : elements) {
			names.add(fs.getName(e));
			owner.add(fs.getOwner(e).getName());
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
}
