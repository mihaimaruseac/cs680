public class MkLinkCommand extends MkCommand {
	FSElement target;
	String targetName;

	public MkLinkCommand(String target, String name) {
		super(new String[]{name}, "ln " + target, "rm");
		targetName = target;
	}

	@Override
	protected void create(String name) throws ElementExistsException {
		FileSystem.getInstance().createLink(name, target);
	}

	@Override
	protected void extraPathSetup() throws InvalidPathException {
		target = FileSystem.getInstance().resolvePath(targetName);
	}
}
