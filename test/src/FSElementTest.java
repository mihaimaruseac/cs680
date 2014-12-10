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
		FSElement cut = new File(null, tmp, null, 0);
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
		FSElement cut = new Directory(null, null, "root");
		String expected = "root";
		String actual = cut.getOwner();
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
		FSElement cut = new Directory(null, null, null);
		String owner = "test";
		FileSystem.getInstance().setOwner(cut, owner);
		String expected = owner;
		String actual = cut.getOwner();
		assertThat(actual, is(expected));
	}
}
