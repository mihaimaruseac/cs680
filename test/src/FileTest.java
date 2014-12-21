import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class FileTest {
	@Test
	public void testLeaf() {
		File cut = new File(null, null, null);
		boolean expected = true;
		boolean actual = cut.isLeaf();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLink() {
		File cut = new File(null, null, null);
		boolean expected = false;
		boolean actual = cut.isLink();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSize() {
		File cut = new File(null, null, null);
		int expected = 0;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}

	@Test
	public void testContents() {
		File cut = new File(null, null, null);
		cut.setContents("This is a string");
		String expected = "This is a string";
		String actual = cut.getContents();
		assertThat(actual, is(expected));
	}
}
