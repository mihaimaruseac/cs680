import java.util.ArrayList;

public class MultipleExceptionsException extends Exception {
	ArrayList<InvalidCommandException> exceptions;

	public MultipleExceptionsException() {
		super("Exceptions found: ");
		exceptions = new ArrayList<InvalidCommandException>();
	}

	public MultipleExceptionsException(InvalidCommandException e) {
		this();
		exceptions.add(e);
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		for (InvalidCommandException ice : exceptions)
			msg += "\n\t" + ice.getMessage();
		return msg;
	}

	public void addException(InvalidCommandException e) {
		exceptions.add(e);
	}

	public boolean isException() {
		return exceptions.size() > 0;
	}
}
