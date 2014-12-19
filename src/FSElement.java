import java.util.Date;

public abstract class FSElement {
	private String name;
	private User owner;
	private Directory parent;
	private Date created;
	private Date lastModified;

	public FSElement(String name, Directory parent, User owner) {
		this.name = name;
		this.parent = parent;
		this.owner = owner;
		created = new Date();
		lastModified = created;
	}

	public Directory getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User user) {
		owner = user;
	}

	public void modify() {
		lastModified = new Date();
	}

	public Date getCreationDate() {
		return created;
	}

	public Date getModificationDate() {
		return lastModified;
	}

	public abstract int getSize();
	public abstract boolean isLeaf();
	public abstract boolean isLink();
	public abstract void accept(FSVisitor v);
}
