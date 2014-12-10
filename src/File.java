public class File extends FSElement {
	private int size;

	public File(String name, Directory parent, String owner, int size) {
		super(name, parent, owner);
		this.size = size;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public boolean isLink() {
		return false;
	}

	@Override
	public void accept(FSVisitor v) {
		v.visit(this);
	}
}
