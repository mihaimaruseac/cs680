import java.lang.StringBuilder;
import java.util.ArrayList;

public class RmFileCommand extends Command {
	ArrayList<String> files;
	String cmdLine;

	public RmFileCommand(String[] args) throws InvalidCommandException {
		StringBuilder sb = new StringBuilder("rm");
		files = new ArrayList<String>();

		if (args.length == 1)
			throw new InvalidCommandException("Need at least one file name argument");

		for (int i = 1; i < args.length; i++) {
			files.add(args[i]);
			sb.append(" " + args[i]);
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
		Directory current = fs.getCurrent();

		for (String name : files)
			fs.removeLeaf(current, name);
	}
}
