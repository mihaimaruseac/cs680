import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class FileTest {
	@Test
	public void testLeaf() {
		Directory root = new Directory(null, null, null);
		File cut = new File(null, root, null, 0);
		boolean expected = true;
		boolean actual = cut.isLeaf();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLink() {
		Directory root = new Directory(null, null, null);
		File cut = new File(null, root, null, 0);
		boolean expected = false;
		boolean actual = cut.isLink();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSize() {
		Directory root = new Directory(null, null, null);
		File cut = new File(null, root, null, 42);
		int expected = 42;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}
}
