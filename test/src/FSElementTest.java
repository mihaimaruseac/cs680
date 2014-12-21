import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.util.Date;

public class FSElementTest {
	@Test
	public void testNullDirectoryParent() {
		FSElement cut = new Directory(null, null, null);
		Directory expected = null;
		Directory actual = cut.getParent();
		assertThat(actual, is(expected));
	}

	@Test
	public void testFileDirectoryParent() {
		Directory tmp = new Directory(null, null, null);
		FSElement cut = new File(null, tmp, null);
		Directory expected = tmp;
		Directory actual = cut.getParent();
		assertThat(actual, is(expected));
	}

	@Test
	public void testDirectoryName() {
		FSElement cut = new Directory("root", null, null);
		String expected = "root";
		String actual = cut.getName();
		assertThat(actual, is(expected));
	}

	@Test
	public void testDirectoryOwner() {
		User u = new User("root", null);
		FSElement cut = new Directory(null, null, u);
		User expected = u;
		User actual = cut.getOwner();
		assertThat(actual, is(expected));
	}

	@Test
	public void testEqualDatesForNotModified() {
		FSElement cut = new Directory(null, null, null);
		Date expected = cut.getCreationDate();
		Date actual = cut.getModificationDate();
		assertThat(actual, is(expected));
	}

	@Test
	public void testDatesForDirty() {
		FSElement cut = new Directory(null, null, null);
		try { Thread.sleep(1); } catch (InterruptedException e) {};
		cut.modify();
		Date expected = cut.getCreationDate();
		Date actual = cut.getModificationDate();
		assertThat(actual, not(is(expected)));
	}

	@Test
	public void testSetOwner() {
		User u = new User("root", null);
		FSElement cut = new Directory(null, null, null);
		cut.setOwner(u);
		User expected = u;
		User actual = cut.getOwner();
		assertThat(actual, is(expected));
	}

	@Test
	public void testNoInitialPermissions() {
		FSElement cut = new Directory(null, null, null);
		int expected = 0;
		int actual = cut.getAllPerms().size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testAddPermissions() {
		FSElement cut = new Directory(null, null, null);
		cut.addPermission(null, null);
		int expected = 1;
		int actual = cut.getAllPerms().size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testAddRemoveSamePermissions() {
		FSElement cut = new Directory(null, null, null);
		cut.addPermission(null, FSPermissionType.PERMISSION_WRITE);
		cut.removePermission(null, FSPermissionType.PERMISSION_WRITE);
		int expected = 0;
		int actual = cut.getAllPerms().size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testAddRemoveOtherPermissions() {
		FSElement cut = new Directory(null, null, null);
		cut.addPermission(null, FSPermissionType.PERMISSION_WRITE);
		cut.removePermission(null, FSPermissionType.PERMISSION_READ);
		int expected = 1;
		int actual = cut.getAllPerms().size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetRootPermission() {
		FSElement cut = new Directory(null, null, null);
		User u = new User(null, null);
		u.addPermission(UserPermissionType.PERMISSION_ROOT);
		FSPermissionType expected = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType actual = cut.getPermission(u);
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetOwnerPermission() {
		User u = new User("test", null);
		FSElement cut = new Directory(null, null, u);
		FSPermissionType expected = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType actual = cut.getPermission(u);
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetNoPermission() {
		User u1 = new User("test", null);
		User u2 = new User("test2", null);
		FSElement cut = new Directory(null, null, u1);
		FSPermissionType expected = FSPermissionType.PERMISSION_NONE;
		FSPermissionType actual = cut.getPermission(u2);
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetGrantedPermission() {
		User u1 = new User("test", null);
		User u2 = new User("test2", null);
		FSElement cut = new Directory(null, null, u1);
		cut.addPermission(u2.getName(), FSPermissionType.PERMISSION_READ);
		FSPermissionType expected = FSPermissionType.PERMISSION_READ;
		FSPermissionType actual = cut.getPermission(u2);
		assertThat(actual, is(expected));
	}
}
