import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class CommandFactoryTest {
	@Test
	public void testAppendCommand() throws InvalidCommandException {
		String cmd = "append file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadAppendCommand() throws InvalidCommandException {
		String cmd = "append";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testCatCommand() throws InvalidCommandException {
		String cmd = " cat file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadCatCommand() throws InvalidCommandException {
		String cmd = "cat";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testCdArgCommand() throws InvalidCommandException {
		String cmd = "cd dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testCdNoArgCommand() throws InvalidCommandException {
		String cmd = "cd";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testChOwnCommand() throws InvalidCommandException {
		String cmd = "chown root file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadChOwnCommand() throws InvalidCommandException {
		String cmd = "chown";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testChUserCommand() throws InvalidCommandException {
		String cmd = "chuser root";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadChUserCommand() throws InvalidCommandException {
		String cmd = "chuser";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testDirCommand() throws InvalidCommandException {
		String cmd = "dir dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testEditCommand() throws InvalidCommandException {
		String cmd = "edit file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadEditCommand() throws InvalidCommandException {
		String cmd = "edit";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testExitCommand() throws InvalidCommandException {
		String cmd = "exit";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testHelpCommand() throws InvalidCommandException {
		String cmd = "help";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testHistoryCommand() throws InvalidCommandException {
		String cmd = "history";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLsCommand() throws InvalidCommandException {
		String cmd = "ls dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testLsUsersCommand() throws InvalidCommandException {
		String cmd = "lsusers";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testSortCommand() throws InvalidCommandException {
		String cmd = "sort owner dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadSortCommand() throws InvalidCommandException {
		String cmd = "sort";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testLnCommand() throws InvalidCommandException {
		String cmd = "ln file target";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadLnCommand() throws InvalidCommandException {
		String cmd = "ln";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testMkDirCommand() throws InvalidCommandException {
		String cmd = "mkdir dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadMkDirCommand() throws InvalidCommandException {
		String cmd = "mkdir";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testMkFileCommand() throws InvalidCommandException {
		String cmd = "touch file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadMkFileCommand() throws InvalidCommandException {
		String cmd = "touch";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testMkUserCommand() throws InvalidCommandException {
		String cmd = "mkuser user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadMkUserCommand() throws InvalidCommandException {
		String cmd = "mkuser";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testRmDirCommand() throws InvalidCommandException {
		String cmd = "rmdir dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadRmDirCommand() throws InvalidCommandException {
		String cmd = "rmdir";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testRmFileCommand() throws InvalidCommandException {
		String cmd = "rm file";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadRmFileCommand() throws InvalidCommandException {
		String cmd = "rm";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testUserPermsCommand() throws InvalidCommandException {
		String cmd = "uperm user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd;
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadUserPermsCommand() throws InvalidCommandException {
		String cmd = "uperm";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testUndoCommand() throws InvalidCommandException {
		String cmd = "undo";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testRedoCommand() throws InvalidCommandException {
		String cmd = "redo";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPWDCommand() throws InvalidCommandException {
		String cmd = "pwd";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testFSPermsCommand() throws InvalidCommandException {
		String cmd = "pperm dir";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPasswdArgCommand() throws InvalidCommandException {
		String cmd = "passwd user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = cmd.trim();
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadPathGrantCommand() throws InvalidCommandException {
		String cmd = "pgrant";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testPathGrantRdCommand() throws InvalidCommandException {
		String cmd = "pgrant rd . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "pgrant PERMISSION_READ . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPathGrantRwCommand() throws InvalidCommandException {
		String cmd = "pgrant rw . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "pgrant PERMISSION_READ_WRITE . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPathGrantWrCommand() throws InvalidCommandException {
		String cmd = "pgrant wr . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "pgrant PERMISSION_WRITE . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testPathGrantBadCommand() throws InvalidCommandException {
		String cmd = "pgrant . . user";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadPathRevokeCommand() throws InvalidCommandException {
		String cmd = "prevoke";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testPathRevokeRdCommand() throws InvalidCommandException {
		String cmd = "prevoke rd . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "prevoke PERMISSION_READ . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPathRevokeRwCommand() throws InvalidCommandException {
		String cmd = "prevoke rw . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "prevoke PERMISSION_READ_WRITE . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testPathRevokeWrCommand() throws InvalidCommandException {
		String cmd = "prevoke wr . user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "prevoke PERMISSION_WRITE . user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testPathRevokeBadCommand() throws InvalidCommandException {
		String cmd = "prevoke . . user";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadUserGrantCommand() throws InvalidCommandException {
		String cmd = "ugrant";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testUserGrantPwCommand() throws InvalidCommandException {
		String cmd = "ugrant pw user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "ugrant PERMISSION_PASSWORD user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserGrantGrCommand() throws InvalidCommandException {
		String cmd = "ugrant gr user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "ugrant PERMISSION_GRANT user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserGrantRvCommand() throws InvalidCommandException {
		String cmd = "ugrant rv user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "ugrant PERMISSION_REVOKE user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserGrantRtCommand() throws InvalidCommandException {
		String cmd = "ugrant rt user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "ugrant PERMISSION_ROOT user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testUserGrantBadCommand() throws InvalidCommandException {
		String cmd = "ugrant . user";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testBadUserRevokeCommand() throws InvalidCommandException {
		String cmd = "urevoke";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test
	public void testUserRevokePwCommand() throws InvalidCommandException {
		String cmd = "urevoke pw user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "urevoke PERMISSION_PASSWORD user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserRevokeGrCommand() throws InvalidCommandException {
		String cmd = "urevoke gr user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "urevoke PERMISSION_GRANT user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserRevokeRvCommand() throws InvalidCommandException {
		String cmd = "urevoke rv user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "urevoke PERMISSION_REVOKE user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test
	public void testUserRevokeRtCommand() throws InvalidCommandException {
		String cmd = "urevoke rt user";
		Command cut = CommandFactory.getCommand(cmd);
		String expected = "urevoke PERMISSION_ROOT user";
		String actual = cut.getCommandLine();
		assertThat(actual, is(expected));
	}

	@Test(expected=InvalidArgumentsCommandException.class)
	public void testUserRevokeBadCommand() throws InvalidCommandException {
		String cmd = "urevoke . user";
		Command cut = CommandFactory.getCommand(cmd);
	}

	@Test(expected=InvalidCommandException.class)
	public void testBadCommand() throws InvalidCommandException {
		String cmd = "asdf";
		Command cut = CommandFactory.getCommand(cmd);
	}
}
