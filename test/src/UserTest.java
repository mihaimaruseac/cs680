import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.util.ArrayList;

public class UserTest {
	@Test
	public void testAddPermTwice() {
		User cut = new User(null, null);
		cut.addPermission(UserPermissionType.PERMISSION_ROOT);
		cut.addPermission(UserPermissionType.PERMISSION_ROOT);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add(UserPermissionType.PERMISSION_ROOT.toString());
		ArrayList<String> actual = cut.getPerms();
		assertThat(actual, is(expected));
	}

	@Test
	public void testAddTwoPerm() {
		User cut = new User(null, null);
		cut.addPermission(UserPermissionType.PERMISSION_ROOT);
		cut.addPermission(UserPermissionType.PERMISSION_GRANT);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add(UserPermissionType.PERMISSION_ROOT.toString());
		expected.add(UserPermissionType.PERMISSION_GRANT.toString());
		ArrayList<String> actual = cut.getPerms();
		assertThat(actual, is(expected));
	}

	@Test
	public void testRemovePerm() {
		User cut = new User(null, null);
		cut.removePermission(UserPermissionType.PERMISSION_ROOT);
		ArrayList<String> expected = new ArrayList<String>();
		ArrayList<String> actual = cut.getPerms();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPassword() {
		User cut = new User(null, null);
		cut.setPassword("test");
		boolean expected = false;
		boolean actual = cut.isValidPassword("false");
		assertThat(actual, is(expected));
	}

	@Test
	public void testCompareUsers() {
		User cut = new User("a", null);
		User u = new User("b", null);
		int expected = "a".compareTo("b");
		int actual = cut.compareTo(u);
		assertThat(actual, is(expected));
	}
}
