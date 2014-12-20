import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class FileSystem {
	private Directory root;
	private Directory current;
	private Stack<User> loggedUsers;
	private HashMap<String, User> availableUsers;
	private static FileSystem instance;

	private FileSystem() {
		availableUsers = new HashMap<String, User>();
		loggedUsers = new Stack<User>();
	}

	public static FileSystem getInstance() {
		if (instance == null)
			instance = new FileSystem();
		return instance;
	}

	public void setUp() {
		setUser(availableUsers.get("root"));
		setRoot(new Directory("root", null, getUser()));
		setCurrent(getRoot());
	}

	public void setUpUsers(ArrayList<User> users) {
		for (User u : users)
			this.availableUsers.put(u.getName(), u);
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

	public void setUser(User user) {
		TUIDisplay.simpleDisplayText("Welcome to MMShell. Type exit or press ^D to exit. Type help [<cmd>] for help");
		loggedUsers.add(user);
	}

	public User getUser() {
		return loggedUsers.peek();
	}

	public boolean hasMoreUsers() {
		return loggedUsers.size() > 0;
	}

	public void logOutUser() {
		loggedUsers.pop();
	}

	public User getUserByName(String userName) throws UserNotFoundException {
		User u = availableUsers.get(userName);

		if (u == null)
			throw new UserNotFoundException(userName);

		return u;
	}

	public void listUsers() {
		ArrayList<String> userNames = new ArrayList<String>(availableUsers.keySet());
		TUIDisplay.arrayDisplayText(userNames);
	}

	public void addUser(String userName, String password) {
		User u = new User(userName, password);
		availableUsers.put(userName, u);
	}

	public void changePassword(User u, String password) {
		u.setPassword(password);
	}

	public boolean checkPassword(User u, String password) {
		return u.isValidPassword(password);
	}

	public void grant(User u, UserPermissionType p) {
		u.addPermission(p);
	}

	public void grant(User u, FSPermissionType p, FSElement e) {
		e.addPermission(u, p);
	}

	public void revoke(User u, UserPermissionType p) {
		u.removePermission(p);
	}

	public void revoke(User u, FSPermissionType p, FSElement e) {
		e.removePermission(u, p);
	}

	public boolean isAllowed(FSElement e, FSPermissionType p) {
		FSPermissionType perm = getUserPathPermission(e);

		return p.compatible(perm);
	}

	public boolean isAllowed(UserPermissionType p) {
		return getUser().isAllowed(p);
	}

	public boolean isAllowedChOwn(User u, FSElement e) {
		return e.getOwner().getName().equals(u.getName()) || u.isAllowed(UserPermissionType.PERMISSION_ROOT);
	}

	public void displayPerms(User u) {
		u.displayPerms();
	}

	public FSPermissionType getUserPathPermission(FSElement e) {
		return e.getPermission(getUser());
	}

	public void setOwner(FSElement element, User user) {
		element.setOwner(user);
	}

	public User getOwner(FSElement element) {
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
		file.setContents(file.getContents() + "\n" + text);
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
