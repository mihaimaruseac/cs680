import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.util.ArrayList;

public class PermsTest {
	@Test
	public void testFSPermissionTypeValues() {
		int expected = 4;
		int actual = FSPermissionType.values().length;
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserPermissionTypeValues() {
		int expected = 4;
		int actual = UserPermissionType.values().length;
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeValueOfString() {
		FSPermissionType expected = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType actual = FSPermissionType.valueOf("PERMISSION_READ_WRITE");
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserPermissionTypeValueOfString() {
		UserPermissionType expected = UserPermissionType.PERMISSION_ROOT;
		UserPermissionType actual = UserPermissionType.valueOf("PERMISSION_ROOT");
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleRW_W() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType other = FSPermissionType.PERMISSION_WRITE;
		boolean expected = true;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleRW_R() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType other = FSPermissionType.PERMISSION_READ;
		boolean expected = true;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleRW_N() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ_WRITE;
		FSPermissionType other = FSPermissionType.PERMISSION_NONE;
		boolean expected = false;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleR_W() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ;
		FSPermissionType other = FSPermissionType.PERMISSION_WRITE;
		boolean expected = false;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleR_R() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ;
		FSPermissionType other = FSPermissionType.PERMISSION_READ;
		boolean expected = true;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleR_N() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ;
		FSPermissionType other = FSPermissionType.PERMISSION_NONE;
		boolean expected = false;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleN_RW() {
		FSPermissionType cut = FSPermissionType.PERMISSION_NONE;
		FSPermissionType other = FSPermissionType.PERMISSION_READ_WRITE;
		boolean expected = false;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermissionTypeCompatibleR_RW() {
		FSPermissionType cut = FSPermissionType.PERMISSION_READ;
		FSPermissionType other = FSPermissionType.PERMISSION_READ_WRITE;
		boolean expected = true;
		boolean actual = cut.compatible(other);
		assertThat(actual, is(expected));
	}
}
