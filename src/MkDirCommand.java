public class MkDirCommand extends MkCommand {
	public MkDirCommand(String[] args) {
		super(args, "mkdir", "rmdir");
	}

	@Override
	protected void create(String name) throws ElementExistsException {
		FileSystem.getInstance().createDirectory(name);
	}
}
