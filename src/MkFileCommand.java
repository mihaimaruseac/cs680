public class MkFileCommand extends MkCommand {
	public MkFileCommand(String[] args) {
		super(args, "touch", "rm");
	}

	@Override
	protected void create(String name) throws ElementExistsException {
		FileSystem.getInstance().createFile(name);
	}
}
