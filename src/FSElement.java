import java.util.Date;
import java.util.HashMap;

public abstract class FSElement {
	private String name;
	private User owner;
	private Directory parent;
	private Date created;
	private Date lastModified;
	private HashMap<String, FSPermissionType> perms;

	public FSElement(String name, Directory parent, User owner) {
		this.name = name;
		this.parent = parent;
		this.owner = owner;
		created = new Date();
		lastModified = created;
		perms = new HashMap<String, FSPermissionType>();
	}

	public final void addPermission(String user, FSPermissionType perm) {
		perms.put(user, perm);
	}

	public final void removePermission(String user, FSPermissionType perm) {
		FSPermissionType current = perms.get(user);

		if (current == perm)
			perms.remove(user);
	}

	public final FSPermissionType getPermission(User user) {
		if (user.isAllowed(UserPermissionType.PERMISSION_ROOT))
			return FSPermissionType.PERMISSION_READ_WRITE;

		if (user.getName().equals(getOwner().getName()))
			return FSPermissionType.PERMISSION_READ_WRITE;

		FSPermissionType current = perms.get(user.getName());
		if (current == null)
			return FSPermissionType.PERMISSION_NONE;

		return current;
	}

	public final HashMap<String, FSPermissionType> getAllPerms() {
		return perms;
	}

	public final Directory getParent() {
		return parent;
	}

	public final String getName() {
		return name;
	}

	public final User getOwner() {
		return owner;
	}

	public final void setOwner(User user) {
		owner = user;
	}

	public final void modify() {
		lastModified = new Date();
	}

	public final Date getCreationDate() {
		return created;
	}

	public final Date getModificationDate() {
		return lastModified;
	}

	public abstract int getSize();
	public abstract boolean isLeaf();
	public abstract boolean isLink();
}
