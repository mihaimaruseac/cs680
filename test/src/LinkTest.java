import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class LinkTest {
	@Test
	public void testLeaf() {
		Directory root = new Directory(null, null, null);
		Link cut = new Link(null, root, null, null);
		boolean expected = true;
		boolean actual = cut.isLeaf();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLink() {
		Directory root = new Directory(null, null, null);
		Link cut = new Link(null, root, null, null);
		boolean expected = true;
		boolean actual = cut.isLink();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSize() {
		Directory root = new Directory(null, null, null);
		Link cut = new Link(null, root, null, null);
		int expected = 0;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}

	@Test
	public void testTarget() {
		Directory root = new Directory(null, null, null);
		File file = new File(null, root, null, 0);
		Link cut = new Link(null, root, null, file);
		FSElement expected = file;
		FSElement actual = cut.getTarget();
		assertThat(actual, is(expected));
	}
}
