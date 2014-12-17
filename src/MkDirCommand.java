import java.lang.StringBuilder;
import java.util.ArrayList;

public class MkDirCommand extends Command {
	ArrayList<String> dirs;
	String cmdLine;

	public MkDirCommand(String[] args) {
		StringBuilder sb = new StringBuilder("mkdir");
		dirs = new ArrayList<String>();

		for (String s: args) {
			dirs.add(s);
			sb.append(" " + s);
		}

		cmdLine = sb.toString();
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

		for (String name : dirs) {
			Directory current = fs.getCurrent();

			int sepIx = name.lastIndexOf("/");
			String dirName, newName;
			FSElement parentDir = null;

			if (sepIx == -1) {
				dirName = ".";
				newName = name;
			} else {
				dirName = name.substring(0, sepIx);
				newName = name.substring(sepIx + 1);
			}

			try {
				parentDir = fs.resolvePath(dirName);
			} catch (InvalidPathException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			if (fs.isLeaf(parentDir)) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(new InvalidArgumentsCommandException(parentDir.getName() + ": not a directory"));
				continue;
			}

			fs.setCurrent((Directory)parentDir);

			try {
				fs.createDirectory(newName);
			} catch (ElementExistsException e) {
				if (up == null)
					up = new MultipleExceptionsException();
				up.addException(e);
				continue;
			}

			fs.setCurrent(current);
		}

		if (up != null)
			throw up;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void executeUndo() throws MultipleExceptionsException {
		/*
		FileSystem fs = FileSystem.getInstance();
		Directory current = fs.getCurrent();

		for (String name : dirs)
			fs.removeDirectory(current, name);
		*/
	}
}
