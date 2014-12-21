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

	/*
	@Test
	public void testGetExistingElement() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);

		FSElement expected = f;
		FSElement actual = cut.getElement("test");
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testGetNonExistingElement() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);

		FSElement expected = f;
		FSElement actual = cut.getElement("tst");
		assertThat(actual, is(expected));
	}

	@Test
	public void testRemoveExistingLeaf() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeLeaf(cut, "test");

		int expected = 0;
		int actual = fs.getChildren(cut).size();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveNonExistingLeaf() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeLeaf(cut, "tst");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveExistingLeafAsDirectory() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeLeaf(cut, "test");
	}

	@Test
	public void testRemoveExistingEmptyDir() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeDirectory(cut, "test");

		int expected = 0;
		int actual = cut.getChildren().size();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveExistingNonEmptyDir() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		Directory f1 = new Directory("test", f, null);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.addChild(f, f1);
		fs.removeDirectory(cut, "test");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveNonExistingDir() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeDirectory(cut, "tst");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveDirWhichIsLeaf() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		// NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeDirectory(cut, "test");
	}
	*/
}
