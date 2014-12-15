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

	public void showElement(FSElement element, boolean detailed) {
		if (!element.isLeaf()) {
			for (FSElement e : ((Directory)element).getChildren())
				doShowElement(e, detailed);
		} else
			doShowElement(element, detailed);
	}

	private void doShowElement(FSElement element, boolean detailed) {
		if (detailed)
			showDetailedElement(element);
		else
			showSummaryElement(element);
	}

	public void showSummaryElement(FSElement element) {
		System.out.println(getElementCanonicalName(element));
	}

	public void showDetailedElement(FSElement element) {
		String s = getElementCanonicalName(element) + "\t\t\t" + element.getOwner() + "\t\t" + element.getSize();

		if (element.isLink())
			s += " -> " + ((Link)element).getTarget().getName();

		System.out.println(s);
	}

	private String getElementCanonicalName(FSElement element) {
		String s = element.getName();
		if (!element.isLeaf())
			s += "/";
		if (element.isLink())
			s = "@" + s;
		return s;
	}

	private void setRoot(Directory root) {
		this.root = root;
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

	public void addChild(Directory parent, FSElement child) throws ElementExistsException {
		parent.addChild(child);
	}

	public ArrayList<FSElement> getChildren(Directory dir) {
		return dir.getChildren();
	}

	public void removeDirectory(Directory parent, String name) throws InvalidCommandException {
		parent.removeDirectory(name);
	}

	public void removeLeaf(Directory parent, String name) throws InvalidCommandException {
		parent.removeLeaf(name);
	}

	public Directory getParent(Directory cwd) {
		Directory parent = cwd.getParent();
		if (parent == null)
			parent = cwd;
		return parent;
	}

	public FSElement getElement(Directory cwd, String name) throws InvalidCommandException {
		return cwd.getElement(name);
	}

	public FSElement resolvePath(Directory cwd, String path) throws InvalidCommandException {
		String delims = "[/]+";
		String[] parts = path.split(delims);
		FSElement now = cwd;

		for (int i = 0; i < parts.length; i++) {
			if (now.isLeaf())
				throw new InvalidCommandException(path + ": no such path");
			if (parts[i].equals("."))
				continue;
			if (parts[i].equals(".."))
				now = getParent((Directory)now);
			else
				now = getElement((Directory)now, parts[i]);
		}

		return now;
	}

	public void createLink(String name, Directory parent, FSElement target) throws ElementExistsException {
		Link l = new Link(name, parent, getUser(), target);
		addChild(parent, l);
	}
}
