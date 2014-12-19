import java.util.ArrayList;

public class FileSystem {
	private Directory root;
	private Directory current;
	private String user;
	private static FileSystem instance = null;

	private FileSystem() {
		clear();
	}

	public static FileSystem getInstance() {
		if (instance == null)
			instance = new FileSystem();
		return instance;
	}

	public void clear() {
		setUser("root");
		setRoot(new Directory("root", null, getUser()));
		setCurrent(getRoot());
	}

	public String getName(FSElement element) {
		String s = element.getName();
		if (!element.isLeaf())
			s += "/";
		if (element.isLink())
			s = "@" + s;
		return s;
	}

	public Directory getRoot() {
		return root;
	}

	public void setCurrent(Directory dir) {
		current = dir;
	}

	public Directory getCurrent() {
		return current;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setOwner(FSElement element, String user) {
		element.setOwner(user);
	}

	public String getOwner(FSElement element) {
		return element.getOwner();
	}

	public int getSize(FSElement element) {
		return element.getSize();
	}

	public ArrayList<FSElement> getChildren(Directory dir) {
		return dir.getChildren();
	}

	public boolean isLeaf(FSElement element) {
		return element.isLeaf();
	}

	public boolean isLink(FSElement element) {
		return element.isLink();
	}

	public boolean isEmptyDir(Directory dir) {
		return dir.isEmpty();
	}

	public FSElement getTarget(Link element) {
		return element.getTarget();
	}

	public void createDirectory(String name) throws ElementExistsException {
		Directory d = new Directory(name, getCurrent(), getUser());
		getCurrent().addChild(d);
		getCurrent().modify();
	}

	public void createFile(String name) throws ElementExistsException {
		File f = new File(name, getCurrent(), getUser());
		getCurrent().addChild(f);
		getCurrent().modify();
	}

	public void createLink(String name, FSElement target) throws ElementExistsException {
		Link l = new Link(name, getCurrent(), getUser(), target);
		getCurrent().addChild(l);
		getCurrent().modify();
	}

	public void remove(FSElement element) {
		element.getParent().remove(element);
		element.getParent().modify();
	}

	public void showFileContents(File file) {
		TUIDisplay.simpleDisplayText(file.getContents());
	}

	public void editFileContents(File file, String text) {
		file.setContents(text);
		file.modify();
	}

	public void appendFileContents(File file, String text) {
		file.setContents(file.getContents() + text);
		file.modify();
	}

	public FSElement resolvePath(String path) throws InvalidPathException {
		if (path == null)
			return getRoot();

		String delims = "[/]+";
		String[] parts = path.split(delims);
		FSElement now = getCurrent();

		for (int i = 0; i < parts.length; i++) {
			if (now.isLeaf())
				throw new InvalidPathException(path);
			if (parts[i].equals("."))
				continue;
			if (parts[i].equals(".."))
				now = getParent((Directory)now);
			else
				now = getElement((Directory)now, parts[i]);
		}

		return now;
	}

	private Directory getParent(Directory cwd) {
		Directory parent = cwd.getParent();
		if (parent == null)
			parent = cwd;
		return parent;
	}

	private FSElement getElement(Directory cwd, String name) throws InvalidPathException {
		return cwd.getElement(name);
	}

	private void setRoot(Directory root) {
		this.root = root;
	}
}
