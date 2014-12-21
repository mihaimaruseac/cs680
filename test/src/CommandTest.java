import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class CommandTest {
	@Test
	public void testDefaultUndo() {
		Command cut = new MockCommand();
		boolean expected = false;
		boolean actual = cut.canUndo();
		assertThat(actual, is(expected));
	}

	@Test(expected=NotUndoableCommandException.class)
	public void testDefaultExecuteUndo() throws InvalidCommandException {
		Command cut = new MockCommand();
		try {
			cut.executeUndo();
		} catch (MultipleExceptionsException e) {
			throw e.getExceptions().get(0);
		}
	}

	@Test
	public void testDefaultGoToHistory() {
		Command cut = new MockCommand();
		boolean expected = true;
		boolean actual = cut.goesToHistory();
		assertThat(actual, is(expected));
	}

	@Test
	public void testDefaultCommandLine() {
		Command cut = new MockCommand();
		String expected = "mock";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testExecuteSuccessful() throws MultipleExceptionsException {
		Command cut = new MockCommand();
		cut.executeCommand();
		boolean expected = true;
		boolean actual = ((MockCommand)cut).executed;
		assertThat(actual, is(expected));
	}

	class MockCommand extends Command {
		protected boolean executed;

		public MockCommand () {
			cmdLine = "mock";
			executed = false;
		}

		public void execute() throws MultipleExceptionsException {
			executed = true;
		}
	}
}
