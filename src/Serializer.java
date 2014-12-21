import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Class;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NullCipher;

public class Serializer {
	public Serializer() {
	}

	public void serialize() {
		try {
			Cipher c = new NullCipher();
			DataOutputStream out = new DataOutputStream(new CipherOutputStream(new FileOutputStream("fs"), c));
			serializeUsers(out);
			serializeFS(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			TUIDisplay.simpleDisplayText("Error encountered while serializing :(");
		}
	}

	public void deserialize() throws Exception {
		Cipher c = new NullCipher();
		DataInputStream in = new DataInputStream(new CipherInputStream(new FileInputStream("fs"), c));
		deserializeUsers(in);
		deserializeFS(in);
		in.close();
	}

	private void serializeUsers(DataOutputStream out) throws Exception {
		FileSystem fs = FileSystem.getInstance();
		HashMap<String, User> availableUsers = getField(fs, "availableUsers");
		writeInt(out, availableUsers.size());
		for (User u : availableUsers.values()) {
			String userName = u.getName();
			String password = getField(u, "password");
			ArrayList<UserPermissionType> perms = getField(u, "perms");
			writeString(out, userName);
			writeString(out, password);
			writeInt(out, perms.size());
			for (UserPermissionType p : perms)
				writeString(out, p.toString());
		}
	}

	private void deserializeUsers(DataInputStream in) throws Exception {
		int users = readInt(in);
		ArrayList<User> availableUsers = new ArrayList<User>();

		for (int i = 0; i < users; i++) {
			String userName = readString(in);
			String password = readString(in);
			User u = new User(userName, password);
			int permSize = readInt(in);
			for (int j = 0; j < permSize; j++)
				u.addPermission(readUserPermission(in));

			availableUsers.add(u);
		}

		FileSystem.getInstance().setUpUsers(availableUsers);
		FileSystem.getInstance().setUp();
	}

	private void serializeFS(DataOutputStream out) throws Exception {
		serializeDirectory(FileSystem.getInstance().getRoot(), out);
	}

	private void deserializeFS(DataInputStream in) throws Exception {
		String _d = readString(in);
		if (!_d.equals("D"))
			throw new Exception("Invalid serialized file");

		HashMap<Link, String> linkTargets = new HashMap<Link, String>();
		Directory d = deserializeDirectory(in, linkTargets);
		FileSystem fs = FileSystem.getInstance();
		setField(fs, "root", d);
		setField(fs, "current", d);

		for (Link l : linkTargets.keySet()) {
			setField(l, "target", FileSystem.getInstance().resolvePath("./" + linkTargets.get(l).substring(6)));
		}
	}

	private void serializeDirectory(Directory d, DataOutputStream out) throws Exception {
		writeString(out, "D");
		serializeFSElement(d, out);
		ArrayList<FSElement> children = d.getChildren();
		writeInt(out, children.size());

		for (FSElement e : children) {
			if (!e.isLeaf())
				serializeDirectory((Directory) e, out);
			else if (!e.isLink())
				serializeFile((File) e, out);
			else
				serializeLink((Link) e, out);
		}
	}

	private Directory deserializeDirectory(DataInputStream in, HashMap<Link, String> linkTargets) throws Exception {
		Directory d = new Directory(null, null, null);
		deserializeFSElement(d, in);

		int children = readInt(in);
		for (int i = 0; i < children; i++) {
			FSElement child = null;
			switch(readString(in)) {
				case "D": child = deserializeDirectory(in, linkTargets); break;
				case "F": child = deserializeFile(in); break;
				case "L": child = deserializeLink(in, linkTargets); break;
				default: throw new Exception("Invalid serialized file");
			}

			setField(child, "parent", d);
			d.addChild(child);
		}

		return d;
	}

	private void serializeFile(File f, DataOutputStream out) throws Exception {
		writeString(out, "F");
		serializeFSElement(f, out);
		String contents = getField(f, "contents");
		writeString(out, contents);
	}

	private File deserializeFile(DataInputStream in) throws Exception {
		File f = new File(null, null, null);
		deserializeFSElement(f, in);
		setField(f, "contents", readString(in));
		return f;
	}

	private void serializeLink(Link l, DataOutputStream out) throws Exception {
		writeString(out, "L");
		serializeFSElement(l, out);
		String target = FileSystem.getInstance().getFullPath(l.getTarget());
		writeString(out, target);
	}

	private Link deserializeLink(DataInputStream in, HashMap<Link, String> linkTargets) throws Exception {
		Link l = new Link(null, null, null, null);
		deserializeFSElement(l, in);
		linkTargets.put(l, readString(in));
		return l;
	}

	private void serializeFSElement(FSElement e, DataOutputStream out) throws Exception {
		String name = e.getName();
		String owner = e.getOwner().getName();
		Date created = e.getCreationDate();
		Date lastModified = e.getModificationDate();
		HashMap<String, FSPermissionType> perms = e.getAllPerms();
		writeString(out, name);
		writeString(out, owner);
		writeLong(out, created.getTime());
		writeLong(out, lastModified.getTime());
		writeInt(out, perms.size());
		for (String u : perms.keySet()) {
			writeString(out, u);
			writeString(out, perms.get(u).toString());
		}
	}

	private void deserializeFSElement(FSElement e, DataInputStream in) throws Exception {
		setField(e, "name", readString(in));
		e.setOwner(FileSystem.getInstance().getUserByName(readString(in)));
		setField(e, "created", new Date(readLong(in)));
		setField(e, "lastModified", new Date(readLong(in)));

		int perms = readInt(in);
		for (int i = 0; i < perms; i++)
			e.addPermission(readString(in), readFSPermission(in));
	}

	@SuppressWarnings("unchecked")
	private <K, R> R getField(K o, String fieldName) throws Exception {
		Field f = obtainField(o, fieldName);
		boolean isAccesible = f.isAccessible();

		f.setAccessible(true);
		R fieldVal = (R)f.get(o);
		f.setAccessible(isAccesible);

		return fieldVal;
	}

	@SuppressWarnings("unchecked")
	private <K, R> void setField(K o, String fieldName, R val) throws Exception {
		Field f = obtainField(o, fieldName);
		boolean isAccesible = f.isAccessible();

		f.setAccessible(true);
		f.set(o, val);
		f.setAccessible(isAccesible);
	}

	private <K> Field obtainField(K o, String fieldName) throws Exception {
		Class<?> c = o.getClass();
		Exception _e = null;
		while (c.getSuperclass() != null)
			try {
				Field f = c.getDeclaredField(fieldName);
				return f;
			} catch (Exception e) {
				_e = e;
				c = c.getSuperclass();
			}
		throw _e;
	}

	private void writeInt(DataOutputStream out, int val) throws Exception {
		out.writeInt(val);
	}

	private int readInt(DataInputStream in) throws Exception {
		return in.readInt();
	}

	private void writeLong(DataOutputStream out, long val) throws Exception {
		out.writeLong(val);
	}

	private long readLong(DataInputStream in) throws Exception {
		return in.readLong();
	}

	private void writeString(DataOutputStream out, String val) throws Exception {
		out.writeUTF(val);
	}

	private String readString(DataInputStream in) throws Exception {
		return in.readUTF();
	}

	private UserPermissionType readUserPermission(DataInputStream in) throws Exception {
		switch (readString(in)) {
			case "PERMISSION_PASSWORD": return UserPermissionType.PERMISSION_PASSWORD;
			case "PERMISSION_GRANT": return UserPermissionType.PERMISSION_GRANT;
			case "PERMISSION_REVOKE": return UserPermissionType.PERMISSION_REVOKE;
			case "PERMISSION_ROOT": return UserPermissionType.PERMISSION_ROOT;
			default: throw new Exception("Invalid serialized file");
		}
	}

	private FSPermissionType readFSPermission(DataInputStream in) throws Exception {
		switch (readString(in)) {
			case "PERMISSION_READ": return FSPermissionType.PERMISSION_READ;
			case "PERMISSION_WRITE": return FSPermissionType.PERMISSION_WRITE;
			case "PERMISSION_READ_WRITE": return FSPermissionType.PERMISSION_READ_WRITE;
			default: throw new Exception("Invalid serialized file");
		}
	}
}
