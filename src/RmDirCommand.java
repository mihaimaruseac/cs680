import java.lang.StringBuilder;
import java.util.ArrayList;

public class RmDirCommand extends RmCommand {
	public RmDirCommand(String[] args) {
		super(args, "rmdir");
	}

	@Override
	protected void validateElement(String path, FSElement element) throws InvalidArgumentsCommandException {
		FileSystem fs = FileSystem.getInstance();
		if (fs.isLeaf(element))
			throw new InvalidArgumentsCommandException(path + ": not a file");
		if (!fs.isEmptyDir((Directory)element))
			throw new InvalidArgumentsCommandException(path + ": not an empty directory");
	}
}
