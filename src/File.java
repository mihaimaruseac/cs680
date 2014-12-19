public class File extends FSElement {
	private String contents;

	public File(String name, Directory parent, User owner) {
		super(name, parent, owner);
		contents = "";
	}

	@Override
	public int getSize() {
		return contents.length();
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

	public String getContents() {
		return contents;
	}

	public void setContents(String text) {
		contents = text;
	}
}
