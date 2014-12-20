import java.util.ArrayList;

public class User implements Comparable<User> {
	String userName;
	String password;
	ArrayList<UserPermissionType> perms;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;

		perms = new ArrayList<UserPermissionType>();
	}

	public void addPermission(UserPermissionType perm) {
		for (UserPermissionType upt : perms)
			if (upt == perm)
				return;

		perms.add(perm);
	}

	public void retractPermission(UserPermissionType perm) {
		perms.remove(perm);
	}

	public boolean isAllowed(UserPermissionType perm) {
		return perms.contains(perm);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidPassword(String password) {
		return this.password.equals(password);
	}

	public String getName() {
		return userName;
	}

	@Override
	public int compareTo(User u) {
		return userName.compareTo(u.userName);
	}
}
