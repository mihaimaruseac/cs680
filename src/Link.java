public class Link extends FSElement {
	private FSElement target;

	public Link(String name, Directory parent, User owner, FSElement target) {
		super(name, parent, owner);
		this.target = target;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public boolean isLink() {
		return true;
	}

	public FSElement getTarget() {
		return target;
	}

	@Override
	public void accept(FSVisitor v) {
		v.visit(this);
	}
}
