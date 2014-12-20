import java.util.ArrayList;

public class FsPermsCommand extends ListCommand {
	public FsPermsCommand(String[] args) {
		super(args, "pperm");
	}

	@Override
	protected void showInfo(ArrayList<FSElement> elements) {
		FileSystem fs = FileSystem.getInstance();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> perms = new ArrayList<String>();

		for (FSElement e : elements) {
			names.add(fs.getName(e));
			perms.add(fs.getUserPathPermission(e).toString());
		}

		ArrayList<ArrayList<String>> columns = new ArrayList<ArrayList<String>>();
		columns.add(names);
		columns.add(perms);

		TUIDisplay.columnDisplayText(columns);
	}
}

