import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortCommand extends Command {
	String method;
	String dst;
	String cmdLine;

	public SortCommand(String method, String dst) throws InvalidCommandException {
		this.method = method;
		this.dst = dst;
		cmdLine = "sort " + method + " " + dst;
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		FSElement dst = fs.resolvePath(fs.getCurrent(), this.dst);

		if (dst.isLeaf())
			throw new InvalidCommandException("Cannot display contents of " + this.dst + " : not a directory!");

		ArrayList<FSElement> elems = fs.getChildren((Directory)dst);
		switch(method) {
			case "owner": Collections.sort(elems, new OwnerComparator()); break;
			default: throw new InvalidCommandException("The only sorting methods are: \"owner\"");
		}

		for (FSElement e : elems)
			fs.showDetailedElement(e);
	}
}

class OwnerComparator implements Comparator<FSElement> {
	public OwnerComparator() {}

	public int compare(FSElement o1, FSElement o2) {
		return o1.getOwner().compareTo(o2.getOwner());
	}
}
