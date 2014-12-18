import java.util.ArrayList;

public class LsCommand extends ListCommand {
	public LsCommand(String[] args) {
		super(args, "ls");
	}

	@Override
	protected void showInfo(ArrayList<FSElement> elements) {
		FileSystem fs = FileSystem.getInstance();
		ArrayList<String> names = new ArrayList<String>();

		for (FSElement e : elements)
			names.add(fs.getName(e));

		TUIDisplay.arrayDisplayText(names);
	}
}
