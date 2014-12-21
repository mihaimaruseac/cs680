import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

import java.util.ArrayList;

public class DirectoryTest {
	@Test
	public void testLeaf() {
		Directory cut = new Directory(null, null, null);
		boolean expected = false;
		boolean actual = cut.isLeaf();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLink() {
		Directory cut = new Directory(null, null, null);
		boolean expected = false;
		boolean actual = cut.isLink();
		assertThat(actual, is(expected));
	}

	@Test
	public void testEmptySize() {
		Directory cut = new Directory(null, null, null);
		int expected = 0;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}

	@Test
	public void testEmptyIsEmpty() {
		Directory cut = new Directory(null, null, null);
		boolean expected = true;
		boolean actual = cut.isEmpty();
		assertThat(actual, is(expected));
	}

	@Test
	public void testNotEmptyIsEmpty() throws ElementExistsException {
		Directory cut = new Directory(null, null, null);
		File f = new File(null, null, null);
		cut.addChild(f);

		boolean expected = false;
		boolean actual = cut.isEmpty();
		assertThat(actual, is(expected));
	}

	@Test
	public void testAddRemoveChild() throws ElementExistsException {
		Directory cut = new Directory(null, null, null);
		File f = new File(null, null, null);
		cut.addChild(f);
		cut.remove(f);

		int expected = 0;
		int actual = cut.getChildren().size();
		assertThat(actual, is(expected));
	}

	@Test(expected=ElementExistsException.class)
	public void testAddTwice() throws ElementExistsException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", null, null);
		cut.addChild(f);
		cut.addChild(f);
	}

	@Test
	public void testAddAndGet() throws ElementExistsException, InvalidPathException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", null, null);
		cut.addChild(f);

		FSElement expected = f;
		FSElement actual = cut.getElement(f.getName());
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidPathException.class)
	public void testGetiMissing() throws InvalidPathException {
		Directory cut = new Directory(null, null, null);
		cut.getElement("test");
	}
	@Test
	public void testSize() throws ElementExistsException {
		Directory cut = new Directory(null, null, null);
		File f = new File(null, null, null);
		f.setContents("1234567890");
		cut.addChild(f);

		int expected = 10;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}
}
