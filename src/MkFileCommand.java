import java.lang.StringBuilder;
import java.util.ArrayList;

public class MkFileCommand extends Command {
	ArrayList<String> files;
	String cmdLine;

	public MkFileCommand(String[] args) {
		StringBuilder sb = new StringBuilder("touch");
		files = new ArrayList<String>();

		for (String s: args) {
			files.add(s);
			sb.append(" " + s);
		}

		cmdLine = sb.toString();
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		String errors = "";

		for (String name : files)
			try {
				fs.createFile(name);
			} catch (ElementExistsException e) {
				errors += name + " ";
			}

		if (errors.length() > 0)
			throw new ElementExistsException(errors);
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws InvalidCommandException {
		/*
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();

		for (String name : files)
			fs.removeLeaf(current, name);
		*/
	}
}
