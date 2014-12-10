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
	public void testGetChildrenOfEmptyDir() {
		Directory cut = new Directory(null, null, null);
		int expected = 0;
		int actual = cut.getChildren().size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSize() throws ElementExistsException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);

		int expected = 42;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}

	@Test
	public void testGetExistingElement() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		FileSystem fs = FileSystem.getInstance();
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
		FileSystem fs = FileSystem.getInstance();
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
		FileSystem fs = FileSystem.getInstance();
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
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeLeaf(cut, "tst");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveExistingLeafAsDirectory() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeLeaf(cut, "test");
	}

	@Test
	public void testRemoveExistingEmptyDir() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		FileSystem fs = FileSystem.getInstance();
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
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.addChild(f, f1);
		fs.removeDirectory(cut, "test");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveNonExistingDir() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		Directory f = new Directory("test", cut, null);
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeDirectory(cut, "tst");
	}

	@Test(expected=InvalidCommandException.class)
	public void testRemoveDirWhichIsLeaf() throws ElementExistsException, InvalidCommandException {
		Directory cut = new Directory(null, null, null);
		File f = new File("test", cut, null, 42);
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(cut, f);
		fs.removeDirectory(cut, "test");
	}
}
