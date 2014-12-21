import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class LinkTest {
	@Test
	public void testLeaf() {
		Link cut = new Link(null, null, null, null);
		boolean expected = true;
		boolean actual = cut.isLeaf();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLink() {
		Link cut = new Link(null, null, null, null);
		boolean expected = true;
		boolean actual = cut.isLink();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSize() {
		Link cut = new Link(null, null, null, null);
		int expected = 0;
		int actual = cut.getSize();
		assertThat(actual, is(expected));
	}

	@Test
	public void testTarget() {
		File file = new File(null, null, null);
		Link cut = new Link(null, null, null, file);
		FSElement expected = file;
		FSElement actual = cut.getTarget();
		assertThat(actual, is(expected));
	}
}
