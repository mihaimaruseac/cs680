import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class FileSystemTest {
	@Test
	public void testRootAndCurrent() {
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		Directory expected = fs.getRoot();
		Directory actual = fs.getCurrent();
		assertThat(actual, is(expected));
	}

	@Test(expected=ElementExistsException.class)
	public void testCreateSameElementTwice() throws ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		fs.addChild(fs.getRoot(), new File("test", fs.getRoot(), null, 0));
		fs.addChild(fs.getRoot(), new File("test", fs.getRoot(), null, 0));
		Directory expected = fs.getRoot();
		Directory actual = fs.getCurrent();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCreateLink() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		File f = new File("testFile", fs.getRoot(), null, 0);
		fs.addChild(fs.getRoot(), f);
		FSElement file = fs.getElement(fs.getRoot(), "testFile");
		fs.createLink("test", fs.getRoot(), file);
		FSElement link = fs.resolvePath(fs.getRoot(), "../../test");

		String expected = "test";
		String actual = link.getName();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testResolveInvalidDetailedPath() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		fs.clear();
		Directory root = fs.getRoot();
		Directory test = new Directory("test", root, null);
		File cut = new File("file", test, null, 0);

		fs.addChild(root, test);
		fs.addChild(test, cut);

		fs.resolvePath(test, ".././test/file/bad");
	}

	@Test
	public void testVisitorAcceptAcrossFS() throws ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		fs.clear();

		File testFile = new File("test", fs.getRoot(), null, 0);
		Link testLink = new Link("test2", fs.getRoot(), null, testFile);
		fs.addChild(fs.getRoot(), testFile);
		fs.addChild(fs.getRoot(), testLink);

		MockVisitor cut = new MockVisitor();
		fs.getRoot().accept(cut);
		assertThat(cut.filesVisited(), is(1));
		assertThat(cut.linksVisited(), is(1));
		assertThat(cut.dirsVisited(), is(1));
	}

	class MockVisitor implements FSVisitor {
		public int linkVisited;
		public int dirVisited;
		public int fileVisited;

		public MockVisitor() {
			linkVisited = 0;
			dirVisited = 0;
			fileVisited = 0;
		}

		public void visit(Link link) {
			linkVisited++;
		}

		public void visit(Directory dir) {
			dirVisited++;
		}

		public void visit(File file) {
			fileVisited++;
		}

		public int linksVisited() {
			return linkVisited;
		}

		public int dirsVisited() {
			return dirVisited;
		}

		public int filesVisited() {
			return fileVisited;
		}
	}
}
