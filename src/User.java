public class User implements Comparable<User> {
	String userName;
	String password;

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
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
