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
	public void execute() throws MultipleExceptionsException {
		FileSystem fs = FileSystem.getInstance();
		MultipleExceptionsException up = null;

		for (String name : files) {
			Directory current = fs.getCurrent();

			int sepIx = name.lastIndexOf("/");
			String dirName, fileName;
			FSElement parentDir = null;

			if (sepIx == -1) {
				dirName = ".";
				fileName = name;
			} else {
				dirName = name.substring(0, sepIx);
				fileName = name.substring(sepIx + 1);
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
				fs.createFile(fileName);
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

		for (String name : files)
			fs.removeLeaf(current, name);
		*/
	}
}
