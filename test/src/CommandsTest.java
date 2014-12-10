import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class CommandsTest {
	@Test
	public void testDefaultUndo() {
		Command cut = new MockCommand();
		boolean expected = false;
		boolean actual = cut.canUndo();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testDefaultExecuteUndo() throws InvalidCommandException {
		Command cut = new MockCommand();
		cut.executeUndo();
	}

	@Test
	public void testUndoCommandGoToHistory() {
		Command cut = new UndoCommand(new MockCommand());
		boolean expected = false;
		boolean actual = cut.goToHistory();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUndoCommandGetCommandLine() {
		Command cut = new UndoCommand(new MockCommand());
		String expected = "undo";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUndoCommandExecute() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "mkdir test".split("[ ]+");
		Command mk = new MkDirCommand(args);
		mk.execute();
		Command cut = new UndoCommand(mk);
		cut.execute();

		int expected = 0;
		int actual = FileSystem.getInstance().getChildren(FileSystem.getInstance().getRoot()).size();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testMkDirNoArg() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "mkdir".split("[ ]+");
		Command cut = new MkDirCommand(args);
	}

	@Test(expected=InvalidCommandException.class)
	public void testMkDirTwice() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "mkdir test test".split("[ ]+");
		Command cut = new MkDirCommand(args);
		cut.execute();
	}

	@Test(expected=InvalidCommandException.class)
	public void testMkFileTwice() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "touch test test".split("[ ]+");
		Command cut = new MkFileCommand(args);
		cut.execute();
	}

	@Test(expected=InvalidCommandException.class)
	public void testMkFileNoArg() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "touch".split("[ ]+");
		Command cut = new MkFileCommand(args);
		cut.execute();
	}

	@Test
	public void testUndoMkFile() throws InvalidCommandException {
		FileSystem.getInstance().clear();
		String []args = "touch test".split("[ ]+");
		Command mk = new MkFileCommand(args);
		mk.execute();
		Command cut = new UndoCommand(mk);
		cut.execute();

		int expected = 0;
		int actual = FileSystem.getInstance().getChildren(FileSystem.getInstance().getRoot()).size();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCanUndoMkFile() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "touch test";
		Command cut = s.parseCommand(cmd);

		boolean expected = true;
		boolean actual = cut.canUndo();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCmdLineMkFile() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "touch test";
		Command cut = s.parseCommand(cmd);

		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCmdLineChUser() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "chuser test";
		Command cut = s.parseCommand(cmd);

		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCanUndoChUser() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "chuser test";
		Command cut = s.parseCommand(cmd);

		boolean expected = true;
		boolean actual = cut.canUndo();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUndoChUser() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "chuser test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		String expected = "test";
		String actual = fs.getUser();
		assertThat(actual, is(expected));

		cut = new UndoCommand(cut);
		cut.execute();

		expected = "root";
		actual = fs.getUser();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testCdNotExist() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "cd test";
		Command cut = s.parseCommand(cmd);
	}

	@Test
	public void testCmdLineCdEmpty() throws InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "cd";
		Command cut = s.parseCommand(cmd);

		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testCdLeaf() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		File f = new File("test", fs.getRoot(), fs.getUser(), 0);
		fs.addChild(fs.getRoot(), f);
		String cmd = "cd test";
		Command cut = s.parseCommand(cmd);
	}

	@Test
	public void testCd() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "cd test";
		Command cut = s.parseCommand(cmd);

		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCanUndoCd() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "cd test";
		Command cut = s.parseCommand(cmd);

		boolean expected = true;
		boolean actual = cut.canUndo();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUndoCd() throws InvalidCommandException, ElementExistsException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "cd test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		String expected = "test";
		String actual = fs.getCurrent().getName();
		assertThat(actual, is(expected));

		cut = new UndoCommand(cut);
		cut.execute();

		expected = "root";
		actual = fs.getCurrent().getName();
		assertThat(actual, is(expected));
	}
	
	@Test
	public void testRmFile() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		File f = new File("test", fs.getRoot(), fs.getUser(), 0);
		fs.addChild(fs.getRoot(), f);
		String cmd = "rm test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		int expected = 0;
		int actual = fs.getChildren(fs.getRoot()).size();
		assertThat(actual, is(expected));

		String expectedCmd = cmd;
		String actualCmd = cut.getCommandLine();
		assertThat(actualCmd, is(expectedCmd));
	}

	@Test(expected=InvalidCommandException.class)
	public void testBadRmFile() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "rm";
		Command cut = s.parseCommand(cmd);
	}

	@Test
	public void testRmDir() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "rmdir test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		int expected = 0;
		int actual = fs.getChildren(fs.getRoot()).size();
		assertThat(actual, is(expected));

		String expectedCmd = cmd;
		String actualCmd = cut.getCommandLine();
		assertThat(actualCmd, is(expectedCmd));
	}

	@Test
	public void testRmDirUndo() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "rmdir test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		boolean expectedUndo = true;
		boolean actualUndo = cut.canUndo();
		assertThat(actualUndo, is(expectedUndo));

		cut = new UndoCommand(cut);
		cut.execute();

		int expected = 1;
		int actual = fs.getChildren(fs.getRoot()).size();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidCommandException.class)
	public void testRmDirFile() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		File f = new File("test", fs.getRoot(), fs.getUser(), 0);
		fs.addChild(fs.getRoot(), f);
		String cmd = "rmdir test";
		Command cut = s.parseCommand(cmd);
		cut.execute();
	}

	@Test(expected=InvalidCommandException.class)
	public void testBadRmDir() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		String cmd = "rmdir";
		Command cut = s.parseCommand(cmd);
	}

	@Test
	public void textExitCmdLine() {
		Command cut = new ExitCommand(null);
		String expected = "exit";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void textHistoryCmdLine() {
		Command cut = new HistoryCommand(null);
		String expected = "history";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void textHelpCmdLine() {
		Command cut = new HelpCommand();
		String expected = "help";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testChOwn() throws ElementExistsException, InvalidCommandException {
		FileSystem fs = FileSystem.getInstance();
		Shell s = new Shell();
		fs.clear();
		Directory f = new Directory("test", fs.getRoot(), fs.getUser());
		fs.addChild(fs.getRoot(), f);
		String cmd = "chown test test";
		Command cut = s.parseCommand(cmd);
		cut.execute();

		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));

		expected = "test";
		actual = fs.resolvePath(fs.getRoot(), "test").getOwner();
		assertThat(actual, is(expected));
	}


	class MockCommand extends Command {
		public MockCommand () {}
		public void execute() throws InvalidCommandException {}

		public String getCommandLine() {
			return null;
		}
	}
}

