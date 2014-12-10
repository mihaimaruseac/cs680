public class LnCommand extends Command {
	String src;
	String dst;
	String cmdLine;

	public LnCommand(String src, String dst) throws InvalidCommandException {
		this.src = src;
		this.dst = dst;
		cmdLine = "ln " + src + " " + dst;
	}

	@Override
	public String getCommandLine() {
		return cmdLine;
	}

	@Override
	public void execute() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		FSElement src = fs.resolvePath(fs.getCurrent(), this.src);

		int ix = dst.lastIndexOf("/");
		String dstPath = this.dst.substring(0, ix);
		String dstName = this.dst.substring(ix + 1);

		FSElement dst = fs.resolvePath(fs.getCurrent(), dstPath);
		if (dst.isLeaf())
			throw new InvalidCommandException("Cannot create link in " + dstPath + " : not a directory!");

		try {
			fs.createLink(dstName, (Directory)dst, src);
		} catch (ElementExistsException e) {
			throw new InvalidCommandException(e.getMessage());
		}
	}
}
