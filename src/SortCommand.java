import java.util.ArrayList;
import java.util.Collections;

public class SortCommand extends DirCommand {
	String method;

	public SortCommand(String method, String[] args) {
		super(args, "sort " + method);
		this.method = method;
	}

	@Override
	protected void showInfo(ArrayList<FSElement> elements) {
		switch(method) {
			case "owner": Collections.sort(elements, new OwnerComparator()); break;
			default: fail(elements);
		}

		super.showInfo(elements);
	}

	private void fail(ArrayList<FSElement> elements) {
		if (up == null)
			up = new MultipleExceptionsException();

		up.addException(new InvalidArgumentsCommandException("The only sorting methods are: \"owner\""));
		elements.clear();
	}
}
