import java.lang.Class;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Serializer {
	public Serializer() {
	}

	public void serialize() {
		serializeUsers();
		serializeFS();
	}

	private void serializeUsers() {
		FileSystem fs = FileSystem.getInstance();
		HashMap<String, User> availableUsers = getField(fs, "availableUsers");
		for (User u : availableUsers.values()) {
			String userName = u.getName();
			String password = getField(u, "password");
			ArrayList<UserPermissionType> perms = getField(u, "perms");
			System.out.println(u + ": " + userName + "|" + password + "|" + perms); // TODO: save
		}
	}

	private void serializeFS() {
		serializeDirectory(FileSystem.getInstance().getRoot());
	}

	private void serializeDirectory(Directory d) {
		serializeFSElement(d);
		for (FSElement e : d.getChildren()) {
			if (!e.isLeaf())
				serializeDirectory((Directory) e);
			else if (!e.isLink())
				serializeFile((File) e);
			else
				serializeLink((Link) e);
		}
	}

	private void serializeFile(File f) {
		serializeFSElement(f);
		String contents = getField(f, "contents");
		System.out.println(f + ": " + contents);
	}

	private void serializeLink(Link l) {
		serializeFSElement(l);
		String target = l.getTarget().getName();
		System.out.println(l + ": " + target);
	}

	private void serializeFSElement(FSElement e) {
		String name = e.getName();
		String owner = e.getOwner().getName();
		Date created = e.getCreationDate();
		Date lastModified = e.getModificationDate();
		HashMap<String, FSPermissionType> perms = e.getAllPerms();
		System.out.println(e + ": " + name + "|" + owner + "|" + created + "|" + lastModified + "|" + perms);
	}

	@SuppressWarnings("unchecked")
	private <K, R> R getField(K o, String fieldName) {
		try {
			Field f = o.getClass().getDeclaredField(fieldName);
			boolean isAccesible = f.isAccessible();

			f.setAccessible(true);
			R fieldVal = (R)f.get(o);
			f.setAccessible(isAccesible);

			return fieldVal;
		} catch (Exception e) {
			TUIDisplay.simpleDisplayText(e.toString());
			return null;
		}
	}
}
